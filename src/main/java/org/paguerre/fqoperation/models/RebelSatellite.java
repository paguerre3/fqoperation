package org.paguerre.fqoperation.models;

public class RebelSatellite extends Satellite {

	String[] messageFromSource;

	public RebelSatellite(String name, double[] coordinates, double sourceDistance) {
		super(name, coordinates, sourceDistance);
	}
	
	public RebelSatellite(String name, double[] coordinates) {
		super(name, coordinates);
	}

	public RebelSatellite(String name, double[] coordinates, double sourceDistance, String[] messageFromSource) {
		super(name, coordinates, sourceDistance);
		this.messageFromSource = messageFromSource;
	}

	public RebelSatellite(String name, double sourceDistance, String[] messageFromSource) {
		super(name, sourceDistance);
		this.messageFromSource = messageFromSource;
	}
	
	public String[] getMessageFromSource() {
		return messageFromSource;
	}

	public void setMessageFromSource(String[] messageFromSource) {
		this.messageFromSource = messageFromSource;
	}

}
