package org.paguerre.fqoperation.models;

public class Spaceship {

	private String name;

	private double[] coordinates;

	public Spaceship(double[] coordinates) {
		super();
		this.coordinates = coordinates;
	}

	public Spaceship(String name, double[] coordinates) {
		super();
		this.name = name;
		this.coordinates = coordinates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}
}
