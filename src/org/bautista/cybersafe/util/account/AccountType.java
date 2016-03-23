package org.bautista.cybersafe.util.account;

public enum AccountType {

	BANKING("Banking", 1), WEBSITE("Website", 2), GAME("Game", 3), OTHER("Other", 4);

	private String name;
	private int id;

	AccountType(String name, int id) {

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
