package org.bautista.cybersafe.util.account.util;

public enum AccountType {

	BANKING("Banking", 1), WEBSITE("Website", 2), GAME("Game", 3), OTHER("Other", 4), EMAIL("Email",
			5);

	private String name;
	private int id;

	AccountType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
