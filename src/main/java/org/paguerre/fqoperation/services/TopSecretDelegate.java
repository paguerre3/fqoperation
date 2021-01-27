package org.paguerre.fqoperation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
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

@Service("TopSecretSvc")
public class TopSecretDelegate implements SpacecraftResolver {
	private final static Logger LOG = LoggerFactory.getLogger(TopSecretDelegate.class);

	@Autowired
	protected Environment env;

	// saved position info. as written in .pdf
	@Autowired
	@Qualifier("rebelSatellites")
	protected LoadingCache<String, Set<Satellite>> rebelSatellitesCache;

	@Autowired
	protected Discovery discovery;

	@Autowired
	protected Messenger messenger;

	@Override
	public Transporter find(SatellitesComposition satellitesNewInformation) {
		Transporter transporter = null;
		try {
			Set<Satellite> rebelSatellites = this.rebelSatellitesCache
					.getUnchecked(env.getProperty("org.paguerre.fqoperation.resource"));
			satellitesNewInformation.getSatellites().stream().filter(sni0 -> StringUtils.isNotBlank(sni0.getName()))
					.forEach(sni -> {
						Optional<Satellite> ersOpt = rebelSatellites.stream()
								.filter(pes -> sni.getName().equals(pes.getName())).findFirst();
						if (ersOpt.isPresent()) {
							// refresh reference:
							Satellite found = ersOpt.get();
							found.setDistance(sni.getDistance());
							found.setMessage(sni.getMessage());
							satellitesNewInformation.setVerified(true);
						} else {
							LOG.warn("Unable to find satelite in cache: {}" + sni);
						}
					});
			if (satellitesNewInformation.isVerified())
				transporter = find(rebelSatellites);
		} catch (Exception e) {
			LOG.error("Unable to find spacecraft/transporter", e);
		}
		return transporter;
	}

	@SuppressWarnings("unchecked")
	protected final Transporter find(Set<Satellite> calculationSatellites) {
		List<List<Double>> positions = new ArrayList<>();
		List<Double> distances = new ArrayList<>();
		List<List<String>> messages = new ArrayList<>();
		calculationSatellites.stream().filter(c0 -> c0.getMessage() != null).forEach(cs -> {
			positions.add((List<Double>) (List<?>) Arrays.asList(cs.getPosition()));
			distances.add(cs.getDistance());
			messages.add((List<String>) (List<?>) Arrays.asList(cs.getMessage()));
		});
		double[][] crds = to2DDoublePrimitive(positions);
		double[] rads = distances.stream().mapToDouble(i -> i).toArray();
		Transporter transporter = new Transporter();
		// parallel work here:
		CompletableFuture<Void> posFut = CompletableFuture
				.runAsync(() -> transporter.setPosition(new Position(discovery.getLocation(crds, rads))));
		CompletableFuture<Void> mssgFut = CompletableFuture.runAsync(() -> {
			String[][] msgs = to2DStringPrimitive(messages);
			transporter.setMessage(messenger.getMessage(msgs));
		});
		Stream.of(posFut, mssgFut).forEach(CompletableFuture::join);
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

	@Override
	public boolean store(Satellite satellite) {
		// implemented under split delegate
		throw new NotImplementedException();
	}

	@Override
	public Transporter find() {
		// implemented under split delegate
		throw new NotImplementedException();
	}
}
