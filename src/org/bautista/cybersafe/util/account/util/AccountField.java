package org.bautista.cybersafe.util.account.util;

public class AccountField {
	private final String title;
	private final FieldType type;
	private final String data;

	public AccountField(String title, FieldType type, String data) {
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
