package org.bautista.cybersafe.util.account;

import java.io.Serializable;
import java.util.ArrayList;

import org.bautista.cybersafe.ui.components.InformationField;
import org.bautista.cybersafe.util.account.util.AccountType;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String name;
	private final String description;
	private final AccountType type;
	private final ArrayList<InformationField> fields;

	public Account(final String name, final String description, final AccountType type,
			final ArrayList<InformationField> fields) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.fields = fields;
	}

	public void addField(final InformationField field) {
		fields.add(field);
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<InformationField> getFields() {
		return fields;
	}

	public String getName() {
		return name;
	}

	public AccountType getType() {
		return type;
	}

}
