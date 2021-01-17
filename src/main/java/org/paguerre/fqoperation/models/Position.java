package org.paguerre.fqoperation.models;

import org.apache.commons.lang3.ArrayUtils;

public class Position {

	double x;
	double y;

	public Position(double[] coordinates) {
		if (ArrayUtils.isNotEmpty(coordinates) && coordinates.length > 1) {
			this.x = coordinates[0];
			this.y = coordinates[1];
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
