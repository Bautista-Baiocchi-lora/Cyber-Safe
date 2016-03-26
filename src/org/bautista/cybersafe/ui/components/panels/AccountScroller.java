package org.bautista.cybersafe.ui.components.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.bautista.cybersafe.ui.components.AccountPreview;
import org.bautista.cybersafe.util.account.Account;
import org.bautista.cybersafe.util.account.AccountType;

public class AccountScroller extends JPanel implements ActionListener {
	private JButton refresh;
	private AccountPreview ap;

	public AccountScroller() {
		setLayout(new GridLayout(0, 1));
		refresh = new JButton("Refresh");
		positionComponents();
		setListeners();
		setPreferredSize(new Dimension(450, 300));
	}

	private void positionComponents() {
		add(ap = new AccountPreview(new Account("My Email", "pass", AccountType.EMAIL)));
		add(refresh);
	}

	private void setListeners() {
		refresh.addActionListener(this);
		ap.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}

}
