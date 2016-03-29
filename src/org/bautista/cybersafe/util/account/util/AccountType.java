package org.bautista.cybersafe.util.account.util;

import java.io.Serializable;

public enum AccountType implements Serializable {

	BANKING("Banking", 1), WEBSITE("Website", 2), GAME("Game", 3), EMAIL("Email",
			4), OTHER("Other", 5);

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

	public static AccountType getTypeByName(String name) {
		for (AccountType i : values()) {
			if (i.getName().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return null;
	}

}
