package org.paguerre.fqoperation.models;

import java.util.Set;

public class SatellitesComposition {

	Set<Satellite> satellites;
	boolean verified;

	public Set<Satellite> getSatellites() {
		return satellites;
	}

	public void setSatellites(Set<Satellite> satellites) {
		this.satellites = satellites;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
}
