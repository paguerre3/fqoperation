package org.paguerre.fqoperation.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Satellite extends Spacecraft {

	private String name = "";
	private double distance;

	public Satellite() {
	}

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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Satellite == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final Satellite objToCompare = (Satellite) obj;
		return new EqualsBuilder().append(getName(), objToCompare.getName())
				.append(getPosition(), objToCompare.getPosition()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getName()).append(getPosition()).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", this.getName()).append("pos", this.getPosition())
				.append("distance", this.getDistance()).append("mssg", this.getMessage()).toString();
	}
}
