package org.paguerre.fqoperation.models;

public class Spacecraft {

	private double[] position;
	private String[] message;

	public Spacecraft(double[] position, String[] message) {
		this.position = position;
		this.message = message;
	}

	public Spacecraft() {
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}
}
