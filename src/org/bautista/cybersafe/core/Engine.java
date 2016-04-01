package org.bautista.cybersafe.core;

import javax.swing.SwingUtilities;

import org.bautista.cybersafe.ui.MainUI;
import org.bautista.cybersafe.util.Cache;
import org.bautista.cybersafe.util.Config;
import org.bautista.cybersafe.util.account.Account;
import org.bautista.cybersafe.util.account.AccountManager;
import org.bautista.cybersafe.util.user.User;
import org.bautista.cybersafe.util.user.UserManager;

public class Engine {
	private static Engine instance;
	private AccountManager accountManager;
	private MainUI ui;
	private UserManager userManager;
	private final Config config;
	private User currentUser;

	private Engine() {
		if (!Cache.cacheExists()) {
			if (!Cache.createCache()) {
				System.out.println("Error creating cache.");
			}
		}
		config = new Config();
	}

	public static Engine getInstance() {
		return instance == null ? instance = new Engine() : instance;
	}

	public void setCurrentUser(User user) {
		currentUser = user;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public Config getConfig() {
		return config;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void logOut() {
		currentUser = null;
		accountManager = null;
		ui.showLogin();
	}

	public void openAccountViewer(final Account account) {
		ui.showAccount(account);
		ui.setTitle("Cyber Safe - [" + currentUser.getUsername() + "] -"
				+ account.getName());
	}

	public void openCreateAccountScreen() {
		ui.showCreateAccount();
	}

	public void openCreateUserScreen() {
		ui.showCreateUser();
	}

	public void openLoginScreen() {
		ui.showLogin();
		ui.setTitle("Cyber Safe");
	}

	public void openSafeScreen() {
		if (accountManager == null) {
			accountManager = new AccountManager(currentUser);
		}
		ui.showSafe();
		ui.setTitle("Cyber Safe - [" + currentUser.getUsername() + "]");
	}

	public void refreshUI() {
		ui.refresh();
	}

	public void updateAccountPreviews() {
		ui.updateAccountScroller();
	}

	public void run() {
		userManager = new UserManager();
		ui = new MainUI();
		try {
			SwingUtilities.invokeAndWait(() -> ui.setVisible(true));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
