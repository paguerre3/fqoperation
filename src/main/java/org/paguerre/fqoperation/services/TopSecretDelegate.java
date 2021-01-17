package org.paguerre.fqoperation.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.paguerre.fqoperation.models.Position;
import org.paguerre.fqoperation.models.Satellite;
import org.paguerre.fqoperation.models.SatellitesComposition;
import org.paguerre.fqoperation.models.Transporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.common.cache.LoadingCache;
import com.rits.cloning.Cloner;

@Service
public class TopSecretDelegate implements SpacecraftResolver {
	private final static Logger LOG = LoggerFactory.getLogger(TopSecretDelegate.class);

	@Autowired
	private Environment env;

	// saved position info. as written in .pdf
	@Autowired
	@Qualifier("rebelSatellites")
	private LoadingCache<String, Set<Satellite>> rebelSatellitesCache;

	@Autowired
	private Discovery discovery;

	@Autowired
	private Messenger messenger;

	@SuppressWarnings("unchecked")
	@Override
	public Transporter find(SatellitesComposition satellitesNewInformation) {
		Transporter transporter = null;
		try {
			final Set<Satellite> preExistentRebelSatelites = this.rebelSatellitesCache
					.getUnchecked(env.getProperty("org.paguerre.fqoperation.resource"));
			Set<Satellite> calculationSatellites = new HashSet<>();
			satellitesNewInformation.getSatellites().stream().filter(sni0 -> StringUtils.isNotBlank(sni0.getName()))
					.forEach(sni -> {
						Optional<Satellite> pesOpt = preExistentRebelSatelites.stream()
								.filter(pes -> sni.getName().equals(pes.getName())).findFirst();
						Satellite calcSatellite = null;
						if (pesOpt.isPresent()) {
							// obtain copy from existent populated info
							// containing satellite/positions:
							calcSatellite = new Cloner().shallowClone(pesOpt.get());
							// name and positions are already present in this
							// case!
						} else {
							calcSatellite = new Satellite();
							calcSatellite.setName(sni.getName());
							// no positions nor name? are present in this case
							// by it relies on Trilateration when only distances
							// are present! e.g:
							// if at least 2 coordinates exist then multiple
							// distances can be added, this means that it's
							// possible to calculate satellites without all
							// coordinates mapping to an equal quantity of
							// positions
						}
						calcSatellite.setDistance(sni.getDistance());
						calcSatellite.setMessage(sni.getMessage());
						calculationSatellites.add(calcSatellite);
					});
			List<List<Double>> positions = new ArrayList<>();
			List<Double> distances = new ArrayList<>();
			List<List<String>> messages = new ArrayList<>();
			calculationSatellites.forEach(cs -> {
				positions.add((List<Double>) (List<?>) Arrays.asList(cs.getPosition()));
				distances.add(cs.getDistance());
				messages.add((List<String>) (List<?>) Arrays.asList(cs.getMessage()));
			});
			double[][] crds = to2DDoublePrimitive(positions);
			double[] rads = distances.stream().mapToDouble(i -> i).toArray();
			transporter = new Transporter();
			transporter.setPosition(new Position(discovery.getLocation(crds, rads)));
			String[][] msgs = to2DStringPrimitive(messages);
			transporter.setMessage(messenger.getMessage(msgs));
		} catch (Exception e) {
			LOG.error("Unable to find spacecraft/transporter", e);
		}
		return transporter;
	}

	private String[][] to2DStringPrimitive(List<List<String>> list) {
		String[][] array = new String[list.size()][];
		int i = 0;
		for (List<String> nestedList : list) {
			array[i++] = nestedList.toArray(new String[nestedList.size()]);
		}
		return array;
	}

	private double[][] to2DDoublePrimitive(List<List<Double>> list) {
		return list.stream().map(l -> l.stream().mapToDouble(Double::doubleValue).toArray()).toArray(double[][]::new);
	}
}
