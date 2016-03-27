package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.Dimension;
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

	public AccountScroller() {
		setLayout(new GridLayout(0, 1));
		accountPreviews = getAccountPreviews();
		positionComponents();
		setListeners();
		setPreferredSize(new Dimension(200, 100));
	}

	public void refresh() {
		accountPreviews = getAccountPreviews();
		positionComponents();
		setListeners();
	}

	private ArrayList<AccountPreview> getAccountPreviews() {
		final ArrayList<AccountPreview> list = new ArrayList<AccountPreview>();
		for (Account account : Engine.getInstance().getAccountManager().getAccounts()) {
			list.add(new AccountPreview(account));
		}
		return list;
	}

	private void positionComponents() {
		for (AccountPreview ap : accountPreviews) {
			add(ap);
		}
	}

	private void setListeners() {
		for (AccountPreview ap : accountPreviews) {
			ap.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}

}
