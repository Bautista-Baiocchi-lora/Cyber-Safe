package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.AccountPreview;
import org.bautista.cybersafe.util.account.Account;

public class AccountScroller extends JPanel implements ActionListener {
	private ArrayList<AccountPreview> accountPreviews;
	private int nextRow = 1;

	public AccountScroller() {
		accountPreviews = getAccountPreviews();
		setLayout(new GridLayout(accountPreviews.size(), 1));
		positionComponents();
		setListeners();
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

	private ArrayList<AccountPreview> getAccountPreviews() {
		final ArrayList<AccountPreview> list = new ArrayList<AccountPreview>();
		for (final Account account : Engine.getInstance().getAccountManager().getAccounts()) {
			list.add(new AccountPreview(account));
		}
		return list;
	}

	private void positionComponents() {
		for (final AccountPreview ap : accountPreviews) {
			add(ap, new GridLayout(nextRow, 1));
			nextRow++;
		}
	}

	public void refresh() {
		accountPreviews = getAccountPreviews();
		positionComponents();
		setListeners();
	}

	private void setListeners() {
		for (final AccountPreview ap : accountPreviews) {
			ap.addActionListener(this);
		}
	}

}
