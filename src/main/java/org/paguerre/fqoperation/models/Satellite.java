package org.paguerre.fqoperation.models;

public class Satellite extends Spacecraft {

	private String name = "";
	private double distance;

	public Satellite(String name, double[] position, double distance) {
		super(position, null);
		this.name = name;
		this.distance = distance;
	}

	public Satellite(String name, double[] position, double distance, String[] message) {
		super(position, message);
		this.name = name;
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
