package org.bautista.cybersafe.util.account.util;

import java.io.Serializable;

public class AccountField implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String title;
	private final FieldType type;
	private final String data;

	public AccountField(String title, String data, FieldType type) {
		this.title = title;
		this.type = type;
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public String getData() {
		return data;
	}

	public FieldType getType() {
		return type;
	}

}
