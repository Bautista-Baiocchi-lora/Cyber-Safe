package org.bautista.cybersafe.util.account;

import java.util.ArrayList;

public class AccountManager {
	private AccountManager instance;
	private ArrayList<Account> accounts;

	public AccountManager() {
		instance = this;
		accounts = loadAccounts();
	}

	private ArrayList<Account> loadAccounts() {
		return new ArrayList<Account>();
	}

	public AccountManager getInstance() {
		return instance == null ? instance = new AccountManager() : instance;
	}

}
