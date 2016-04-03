package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.util.Panel;
import org.bautista.cybersafe.util.account.util.AccountType;

public class AccountFilterScreen extends Panel implements ActionListener {
	private final JLabel nameFilterLabel, typeFilterLabel, filter;
	private final JComboBox typeFilter;
	private JTextField nameFilter;
	private final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 30);

	public AccountFilterScreen() {
		setLayout(new GridLayout(5, 1));

		filter = new JLabel("Search Filter");
		filter.setFont(TITLE_FONT);
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
		typeFilter = new JComboBox();
		typeFilter.addItem("All");
		for (int index = 0; index < AccountType.values().length; index++) {
			typeFilter.addItem(AccountType.values()[index].getName());
		}

		positionComponents();
		setListeners();
		setPreferredSize(new Dimension(450, 350));
	}

	private void setListeners() {
		typeFilter.addActionListener(this);
		nameFilter.addActionListener(this);
	}

	private void positionComponents() {
		add(filter, new GridLayout(1, 1));
		add(nameFilterLabel, new GridLayout(2, 1));
		add(nameFilter, new GridLayout(3, 1));
		add(typeFilterLabel, new GridLayout(4, 1));
		add(typeFilter, new GridLayout(5, 1));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		Engine.getInstance().getAccountManager().filterAccounts(nameFilter.getText(),
				typeFilter.getSelectedItem().toString());
		Engine.getInstance().updateAccountPreviews();
	}

}
