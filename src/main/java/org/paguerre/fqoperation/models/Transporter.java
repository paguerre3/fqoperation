package org.paguerre.fqoperation.models;

public class Transporter {

	private Position position;
	private String message;

	public Transporter() {
	}

	public Transporter(Position position, String message) {
		this.position = position;
		this.message = message;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
