package org.paguerre.fqoperation.models;

public class Satellite extends Spaceship {

	private double sourceDistance;

	public Satellite(String name, double[] coordinates, double sourceDistance) {
		super(name, coordinates);
		this.sourceDistance = sourceDistance;
	}

	public Satellite(String name, double[] coordinates) {
		super(name, coordinates);
	}

	public Satellite(String name, double sourceDistance) {
		super(name);
		this.sourceDistance = sourceDistance;
	}

	public double getSourceDistance() {
		return sourceDistance;
	}

	public void setSourceDistance(double sourceDistance) {
		this.sourceDistance = sourceDistance;
	}
}
