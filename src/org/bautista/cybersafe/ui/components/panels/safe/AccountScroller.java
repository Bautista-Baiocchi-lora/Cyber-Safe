package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
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
			add(ap, new GridLayout(nextRow, 1));
			nextRow++;
		}
	}

	private void setListeners() {
		for (AccountPreview ap : accountPreviews) {
			ap.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
			System.out.println("fire preview");
		}
	}

}
