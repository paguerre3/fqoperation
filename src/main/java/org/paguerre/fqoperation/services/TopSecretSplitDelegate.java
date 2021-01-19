package org.paguerre.fqoperation.services;

import java.util.Optional;
import java.util.Set;

import org.paguerre.fqoperation.models.Satellite;
import org.paguerre.fqoperation.models.Transporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("SplitSvc")
public class TopSecretSplitDelegate extends TopSecretDelegate {
	private final static Logger LOG = LoggerFactory.getLogger(TopSecretSplitDelegate.class);

	@Override
	public boolean store(Satellite satellite) {
		boolean success = false;
		if (satellite != null) {
			try {
				Set<Satellite> rebelSatellites = rebelSatellitesCache
						.getUnchecked(env.getProperty("org.paguerre.fqoperation.resource"));
				Optional<Satellite> optFound = rebelSatellites.stream()
						.filter(s -> s.getName().equals(satellite.getName())).findFirst();
				if (optFound.isPresent()) {
					Satellite found = optFound.get();
					// refresh saved reference:
					found.setDistance(satellite.getDistance());
					found.setMessage(satellite.getMessage());
					success = true;
				} else {
					LOG.warn("Unable to find satelite for split: {}" + satellite);
				}
			} catch (Exception e) {
				LOG.error("Unable to split/store satelite", e);
			}
		}
		return success;
	}

	@Override
	public Transporter find() {
		Transporter transporter = null;
		try {
			Set<Satellite> rebelSatellites = rebelSatellitesCache
					.getUnchecked(env.getProperty("org.paguerre.fqoperation.resource"));
			transporter = find(rebelSatellites);
		} catch (Exception e) {
			LOG.error("Unable to find spacecraft from split", e);
		}
		return transporter;
	}
}
