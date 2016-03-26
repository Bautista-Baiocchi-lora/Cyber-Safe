package org.bautista.cybersafe.util.account;

public class Account {

	private String name;
	private String password;
	private AccountType type;

	public Account(String name, String password, AccountType type) {
		this.name = name;
		this.password = password;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public AccountType getType() {
		return type;
	}

}
