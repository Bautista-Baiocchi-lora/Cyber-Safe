package org.bautista.cybersafe.util.account;

public class Account {

	private String username;
	private String password;
	private AccountType[] type;

	public Account(String username, String password, AccountType... type) {
		this.username = username;
		this.password = password;
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public AccountType[] getType() {
		return type;
	}

}
