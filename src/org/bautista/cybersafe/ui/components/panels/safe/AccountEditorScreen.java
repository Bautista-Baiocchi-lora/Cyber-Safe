package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.InformationField;
import org.bautista.cybersafe.ui.util.Panel;
import org.bautista.cybersafe.util.account.Account;
import org.bautista.cybersafe.util.account.util.AccountType;

public class AccountEditorScreen extends Panel implements ActionListener {
	private JButton back, save, delete;
	private final GridBagConstraints constraints;
	private Account account;
	private final InformationFieldCreator infoFieldCreator;
	private final ArrayList<InformationField> fields;
	private int nextRow = 1;
	private static AccountEditorScreen instance;
	private final JOptionPane popUp;

	public AccountEditorScreen(final Account account) {
		// make more space for buttons
		instance = this;
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(0, 5, 7, 5);
		this.account = account;
		fields = account.getFields();
		popUp = new JOptionPane();

		infoFieldCreator = new InformationFieldCreator();
		back = new JButton("Back");
		save = new JButton("Save Changes");
		delete = new JButton("Delete Account");

		positionComponents();
		setListeners();
	}

	public static AccountEditorScreen getInstance() {
		return instance == null ? instance = new AccountEditorScreen(null) : instance;
	}

	public void addComponent(final InformationField field) {
		if (!fields.contains(field)) {
			fields.add(field);
		}
		removeComponent(infoFieldCreator);
		addComponent(0, nextRow, 1, 1, 1, field);
		addComponent(0, nextRow, 1, 1, 1, infoFieldCreator);
	}

	private void addComponent(final int x, final int y, final int height, final double xweight,
			final double yweight, final JComponent comp) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.weightx = xweight;
		constraints.weighty = yweight;
		constraints.gridheight = height;
		nextRow += height;
		add(comp, constraints);
	}

	private void positionComponents() {
		addComponent(0, nextRow, 1, 1, 1, back);
		addComponent(0, nextRow, 1, 1, 1, delete);
		addComponent(0, nextRow, 1, 1, 1, save);
		addComponent(0, nextRow, 1, 1, 1, infoFieldCreator);
		for (InformationField field : fields) {
			addComponent(field);
		}
	}

	public void removeComponent(final JComponent component) {
		remove(component);
		nextRow--;
		if (fields.contains(component)) {
			fields.remove(component);
		}
		refresh();
	}

	private void setListeners() {
		back.addActionListener(this);
		save.addActionListener(this);
		delete.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command.toLowerCase()) {
			case "delete account":
				final int reply = JOptionPane.showConfirmDialog(this,
						"Are you sure you want to delete this account?",
						"Warning!",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					Engine.getInstance().getAccountManager().deleteAccount(account);
					Engine.getInstance().openSafeScreen();
				}
				break;
			case "back":
				final int r = JOptionPane.showConfirmDialog(this,
						"Do you want to save this account before exiting the account creation screen? All data will be lost otherwise.",
						"Warning!",
						JOptionPane.YES_NO_OPTION);
				if (r == JOptionPane.NO_OPTION) {
					Engine.getInstance().openSafeScreen();
					break;
				}
			case "save changes":
				final int re = JOptionPane.showConfirmDialog(this,
						"Are you sure you wish to make these changes to your account? These changes are permanent!",
						"Warning!",
						JOptionPane.YES_NO_OPTION);
				if (re == JOptionPane.YES_OPTION) {
					if (!fields.get(0).getFieldData().isEmpty()) {
						final Account newAccount = new Account(fields.get(0).getFieldData(),
								fields.get(2).getFieldData(),
								AccountType.getTypeByName(fields.get(1).getFieldData()), fields);
						if (Engine.getInstance().getAccountManager().deleteAccount(account)) {
							if (Engine.getInstance().getAccountManager()
									.createAccount(newAccount)) {
								Engine.getInstance().openSafeScreen();
							}
						}
					} else {
						JOptionPane.showMessageDialog(this,
								"Your \"Name\" field is empty. Please name the account.",
								"Warning!", JOptionPane.OK_OPTION);
					}
				}
				break;
		}
	}

}
