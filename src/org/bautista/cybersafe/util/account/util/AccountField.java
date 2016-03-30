package org.bautista.cybersafe.util.account.util;

import java.io.Serializable;

public class AccountField implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String title;
	private final FieldType type;
	private final String data;

	public AccountField(final String title, final String data, final FieldType type) {
		this.title = title;
		this.type = type;
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public String getTitle() {
		return title;
	}

	public FieldType getType() {
		return type;
	}

}
