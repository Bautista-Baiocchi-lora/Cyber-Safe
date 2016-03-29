package org.bautista.cybersafe.util.account;

import java.io.Serializable;
import java.util.ArrayList;

import org.bautista.cybersafe.util.account.util.AccountField;
import org.bautista.cybersafe.util.account.util.AccountType;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String name;
	private final String description;
	private final AccountType type;
	private final ArrayList<AccountField> fields;

	public Account(String name, String description, AccountType type,
			ArrayList<AccountField> fields) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.fields = fields;
	}

	public ArrayList<AccountField> getFields() {
		return fields;
	}

	public void addField(AccountField field) {
		fields.add(field);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public AccountType getType() {
		return type;
	}

}
