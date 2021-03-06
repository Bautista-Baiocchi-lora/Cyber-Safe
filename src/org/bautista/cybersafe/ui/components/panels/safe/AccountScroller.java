package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.AccountPreview;
import org.bautista.cybersafe.ui.util.Panel;
import org.bautista.cybersafe.util.account.Account;

public class AccountScroller extends Panel implements ActionListener {
	private ArrayList<AccountPreview> accountPreviews;
	private int nextRow = 1;

	public AccountScroller() {
		loadAccountPreviews();
		setLayout(new GridLayout(accountPreviews.size(), 1));
		positionComponents();
		setListeners();
	}

	private void removeAllComponents() {
		for (AccountPreview ap : accountPreviews) {
			remove(ap);
		}
		accountPreviews.clear();
		nextRow = 1;
	}

	public void updatePreviews() {
		removeAllComponents();
		loadAccountPreviews();
		setLayout(new GridLayout(accountPreviews.size(), 1));
		positionComponents();
		setListeners();
		refresh();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String command = e.getActionCommand();
		for (final AccountPreview preview : accountPreviews) {
			if (command.equalsIgnoreCase(preview.getActionCommand())) {
				Engine.getInstance().openAccountViewer(preview.getAccount());
				break;
			}
		}
	}

	private void loadAccountPreviews() {
		if (accountPreviews == null) {
			accountPreviews = new ArrayList<AccountPreview>();
		}
		for (final Account account : Engine.getInstance().getAccountManager()
				.getFilteredAccounts()) {
			accountPreviews.add(new AccountPreview(account));
		}
	}

	private void positionComponents() {
		for (final AccountPreview ap : accountPreviews) {
			add(ap, new GridLayout(nextRow, 1));
			nextRow++;
		}
	}

	private void setListeners() {
		for (final AccountPreview ap : accountPreviews) {
			ap.addActionListener(this);
		}
	}

}
