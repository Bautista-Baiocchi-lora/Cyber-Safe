package org.bautista.cybersafe.core;

import javax.swing.SwingUtilities;

import org.bautista.cybersafe.data.Variables;
import org.bautista.cybersafe.ui.MainUI;
import org.bautista.cybersafe.util.Cache;
import org.bautista.cybersafe.util.Config;
import org.bautista.cybersafe.util.account.Account;
import org.bautista.cybersafe.util.account.AccountManager;
import org.bautista.cybersafe.util.user.UserManager;

public class Engine {

	private static Engine instance;
	private AccountManager accountManager;
	private final MainUI ui;
	private final UserManager userManager;
	private final Config config;

	private Engine() {
		instance = this;
		if (!Cache.cacheExists()) {
			if (!Cache.createCache()) {
				System.out.println("Error creating cache.");
			}
		}
		config = new Config();
		userManager = new UserManager();
		ui = new MainUI();
	}

	public void run() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					ui.setVisible(true);
				}
			});
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public Config getConfig() {
		return config;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void openSafeScreen() {
		if (accountManager == null) {
			accountManager = new AccountManager(Variables.getCurrentUser());
		}
		ui.showSafe();
		ui.setTitle("Cyber Safe - [" + Variables.getCurrentUser().getUsername() + "]");
	}

	public void openAccountViewer(Account account) {
		ui.showAccount(account);
		ui.setTitle("Cyber Safe - [" + Variables.getCurrentUser().getUsername() + "] -"
				+ account.getName());
	}

	public void openLoginScreen() {
		ui.showLogin();
		ui.setTitle("Cyber Safe");
	}

	public void openCreateUserScreen() {
		ui.showCreateUser();
	}

	public void openCreateAccountScreen() {
		ui.showCreateAccount();
	}

	public void refreshUI() {
		ui.refresh();
	}

	public void logOut() {
		userManager.logOut(Variables.getCurrentUser());
		accountManager = null;
		ui.showLogin();
	}

	public static Engine getInstance() {
		return instance == null ? instance = new Engine() : instance;
	}

}
