package org.bautista.cybersafe.ui.components.logger;

public enum LogType {

	CLIENT("Client"), USER("User");

	private String type;

	LogType(final String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
