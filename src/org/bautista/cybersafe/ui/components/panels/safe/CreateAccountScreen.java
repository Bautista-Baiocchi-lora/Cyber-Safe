package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.InformationField;
import org.bautista.cybersafe.ui.util.Scroller;
import org.bautista.cybersafe.util.account.Account;
import org.bautista.cybersafe.util.account.util.AccountField;
import org.bautista.cybersafe.util.account.util.AccountType;

public class CreateAccountScreen extends JPanel implements ActionListener {
	private static CreateAccountScreen instance;
	private final char[] INVALID_CHARACTERS = { '"', '*', '/', ':', '<', '>', '?', '|', '\\' };
	private final GridBagConstraints constraints;
	private final JTextField titleTextField;
	private final JTextArea descriptionTextArea;
	private final InformationField title, type, description;
	private final JButton createAccount, back;
	private final InformationFieldCreator infoFieldCreator;
	private final ArrayList<InformationField> fields;
	private final JOptionPane popUp;

	private int nextRow = 0;

	public CreateAccountScreen() {
		instance = this;
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(0, 5, 7, 5);

		popUp = new JOptionPane();
		titleTextField = new JTextField();
		titleTextField.setPreferredSize(new Dimension(367, 25));
		title = new InformationField("Account Name", titleTextField);
		type = new InformationField("Account Type", new JComboBox(AccountType.values()));
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		description = new InformationField("Account Description",
				new Scroller(descriptionTextArea, new Dimension(367, 25)));

		fields = new ArrayList<InformationField>();
		fields.add(title);
		fields.add(type);
		fields.add(description);

		infoFieldCreator = new InformationFieldCreator();
		createAccount = new JButton("Create Account");
		back = new JButton("Back");

		positionComponents();
		setListeners();
	}

	public static CreateAccountScreen getInstance() {
		return instance == null ? instance = new CreateAccountScreen() : instance;
	}

	private boolean containsInvalidCharacters(final String username) {
		for (final char c : username.toCharArray()) {
			for (final char element : INVALID_CHARACTERS) {
				if (c == element) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String command = e.getActionCommand();
		if (command.equalsIgnoreCase("back")) {
			final int reply = JOptionPane.showConfirmDialog(this,
					"Do you want to save this account before exiting the account creation screen? All data will be lost otherwise.",
					"Warning!",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.NO_OPTION) {
				Engine.getInstance().openSafeScreen();
				return;
			}
		}
		final ArrayList<AccountField> fields = new ArrayList<AccountField>();
		for (final InformationField field : this.fields) {
			fields.add(
					new AccountField(field.getFieldTitle(), field.getFieldData(),
							field.getFieldType()));
		}
		if (!title.getFieldData().isEmpty() && !containsInvalidCharacters(title.getFieldData())) {
			if (Engine.getInstance().getAccountManager()
					.isNameAvaliable(title.getFieldData())) {
				final Account account = new Account(title.getFieldData(),
						description.getFieldData(),
						AccountType.getTypeByName(type.getFieldData()), fields);
				Engine.getInstance().getAccountManager().createAccount(account);
				Engine.getInstance().openSafeScreen();
			} else {
				JOptionPane.showMessageDialog(this,
						"This account name is already taken.",
						"Warning!", JOptionPane.OK_OPTION);
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Your \"Name\" field is empty. Please name the account.",
					"Warning!", JOptionPane.OK_OPTION);
		}
	}

	public void addComponent(final InformationField field) {
		fields.add(field);
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
		addComponent(0, nextRow, 1, 1, 1, createAccount);
		addComponent(0, nextRow, 1, 1, 1, title);
		addComponent(0, nextRow, 1, 1, 1, type);
		addComponent(0, nextRow, 1, 1, 1, description);
		addComponent(0, nextRow, 1, 1, 1, infoFieldCreator);
	}

	public void removeComponent(final JComponent component) {
		remove(component);
		nextRow--;
		if (fields.contains(component)) {
			fields.remove(component);
		}
		Engine.getInstance().refreshUI();
	}

	private void setListeners() {
		createAccount.addActionListener(this);
		back.addActionListener(this);
	}

}
