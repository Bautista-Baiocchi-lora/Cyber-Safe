package org.bautista.cybersafe.util.account;

import java.util.ArrayList;

import org.bautista.cybersafe.util.account.util.AccountField;
import org.bautista.cybersafe.util.account.util.AccountType;

public class Account {

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

	public Account(String name, String description, AccountType type) {
		this(name, description, type, new ArrayList<AccountField>());
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
