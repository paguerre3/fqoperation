package org.paguerre.fqoperation.services;

import org.paguerre.fqoperation.models.Satellite;
import org.paguerre.fqoperation.models.SatellitesComposition;
import org.paguerre.fqoperation.models.Transporter;

public interface SpacecraftResolver {

	Transporter find(SatellitesComposition satellitesComposition);
	
	boolean store(Satellite satellite);
	
	Transporter find();
}
