package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.util.account.util.AccountType;

public class AccountFilterScreen extends JPanel implements ActionListener {
	private final JLabel nameFilterLabel, typeFilterLabel;
	private final JComboBox typeFilter;
	private JTextField nameFilter;

	public AccountFilterScreen() {
		setLayout(new GridLayout(4, 1));

		nameFilterLabel = new JLabel("Account Name");
		nameFilter = new JTextField();
		nameFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				Engine.getInstance().getAccountManager().filterAccounts(nameFilter.getText(),
						typeFilter.getSelectedItem().toString());
				Engine.getInstance().updateAccountPreviews();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				Engine.getInstance().getAccountManager().filterAccounts(nameFilter.getText(),
						typeFilter.getSelectedItem().toString());
				Engine.getInstance().updateAccountPreviews();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

		});
		typeFilterLabel = new JLabel("Account Type");
		typeFilter = new JComboBox(AccountType.values());

		positionComponents();
		setListeners();
		setPreferredSize(new Dimension(450, 350));
	}

	private void setListeners() {
		typeFilter.addActionListener(this);
		nameFilter.addActionListener(this);
	}

	private void positionComponents() {
		add(nameFilterLabel, new GridLayout(1, 1));
		add(nameFilter, new GridLayout(2, 1));
		add(typeFilterLabel, new GridLayout(3, 1));
		add(typeFilter, new GridLayout(4, 1));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		Engine.getInstance().getAccountManager().filterAccounts(nameFilter.getText(),
				typeFilter.getSelectedItem().toString());
		Engine.getInstance().updateAccountPreviews();
	}

}
