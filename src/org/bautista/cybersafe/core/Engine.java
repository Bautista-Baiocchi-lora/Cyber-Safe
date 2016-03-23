package org.bautista.cybersafe.core;

import javax.swing.SwingUtilities;

import org.bautista.cybersafe.ui.MainUI;
import org.bautista.cybersafe.util.account.AccountManager;

public class Engine {

	private Engine instance;
	private AccountManager accountManager;
	private MainUI ui;

	public Engine() {
		instance = this;
		ui = new MainUI();
		accountManager = new AccountManager();
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

	public Engine getInstance() {
		return instance == null ? instance = new Engine() : instance;
	}

}
