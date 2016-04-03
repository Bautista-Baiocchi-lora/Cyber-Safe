package org.bautista.cybersafe.ui.components.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.util.Panel;
import org.bautista.cybersafe.util.enctryption.util.KeyGenerator;
import org.bautista.cybersafe.util.user.User;

public class CreateUser extends Panel implements ActionListener {
	private final JTextField username, recoveryQuestion, recoveryAnswer;
	private final JPasswordField password, confirmPassword, confirmKey, key;
	private final JButton create, generateKey, back;
	private final JLabel usernameLabel, passwordLabel, keyLabel, confirmPasswordLabel,
			confirmKeyLabel, recoveryQuestionLabel, recoveryAnswerLabel;
	private final GridBagConstraints constraints;
	private final JOptionPane popUp;

	public CreateUser() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 4, 4, 5);

		popUp = new JOptionPane();
		username = new JTextField();
		confirmPassword = new JPasswordField();
		password = new JPasswordField();
		key = new JPasswordField();
		confirmKey = new JPasswordField();
		usernameLabel = new JLabel("Username: ", SwingConstants.TRAILING);
		keyLabel = new JLabel("Encryption Key: ", SwingConstants.TRAILING);
		passwordLabel = new JLabel("Password: ", SwingConstants.TRAILING);
		confirmKeyLabel = new JLabel("Cofirm Key: ", SwingConstants.TRAILING);
		confirmPasswordLabel = new JLabel("Confirm Password: ", SwingConstants.TRAILING);
		recoveryQuestionLabel = new JLabel("Recovery Question: ", SwingConstants.TRAILING);
		recoveryQuestion = new JTextField();
		recoveryAnswerLabel = new JLabel("Recovery Answer: ", SwingConstants.TRAILING);
		recoveryAnswer = new JTextField();
		create = new JButton("Create Account");
		generateKey = new JButton("Generate Encryption Key");
		generateKey.setActionCommand("generate key");
		back = new JButton("Back");

		positionComponents();
		setListeners();
		setPreferredSize(new Dimension(450, 320));
	}

	private void setListeners() {
		back.addActionListener(this);
		generateKey.addActionListener(this);
		create.addActionListener(this);
	}

	private boolean keysMatch() {
		final char[] eKey = key.getPassword();
		final char[] confirmEKey = confirmKey.getPassword();
		if (Arrays.equals(eKey, confirmEKey)) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
				"Your encryption keys do not match.",
				"Warning!", JOptionPane.OK_OPTION);
		return false;
	}

	private boolean passwordsMatch() {
		final char[] pass = password.getPassword();
		final char[] confirmPass = confirmPassword.getPassword();
		if (Arrays.equals(pass, confirmPass)) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
				"Your passwords do not match.",
				"Warning!", JOptionPane.OK_OPTION);
		return false;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String command = e.getActionCommand().toLowerCase();
		switch (command) {
			case "create account":
				if (keysMatch() && passwordsMatch()) {
					final User user = new User(username.getText(),
							String.valueOf(password.getPassword()), recoveryQuestion.getText(),
							recoveryAnswer.getText(),
							String.valueOf(key.getPassword()));
					if (Engine.getInstance().getUserManager().createUser(user)) {
						Engine.getInstance().getUserManager().logIn(user);
					}
				}
				break;
			case "generate key":
				final String newKey = KeyGenerator.getNewKey();
				key.setText(newKey);
				confirmKey.setText(newKey);
				JOptionPane.showMessageDialog(this, "Your new key is: " + newKey
						+ "\nPlease write it down somewhere you wont forget. You can not access any of your information without it.",
						"Warning!", JOptionPane.OK_OPTION);
				break;
			case "back":
				Engine.getInstance().openLoginScreen();
				break;
		}
	}

	private void addComponent(final int x, final int y, final int width, final double xweight,
			final double yweight,
			final JComponent comp) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.weightx = xweight;
		constraints.weighty = yweight;
		constraints.gridwidth = width;
		add(comp, constraints);
	}

	private void positionComponents() {
		addComponent(0, 0, 1, .1, 1, usernameLabel);
		addComponent(1, 0, 4, 1, 1, username);

		addComponent(0, 1, 1, .1, 1, passwordLabel);
		addComponent(1, 1, 4, 1, 1, password);

		addComponent(0, 2, 1, .1, 1, confirmPasswordLabel);
		addComponent(1, 2, 4, 1, 1, confirmPassword);

		addComponent(0, 3, 1, .1, 1, keyLabel);
		addComponent(1, 3, 4, 1, 1, key);

		addComponent(0, 4, 1, .1, 1, confirmKeyLabel);
		addComponent(1, 4, 4, 1, 1, confirmKey);

		addComponent(0, 5, 1, .1, 1, recoveryQuestionLabel);
		addComponent(1, 5, 1, 1, 1, recoveryQuestion);

		addComponent(0, 6, 1, .1, 1, recoveryAnswerLabel);
		addComponent(1, 6, 1, 1, 1, recoveryAnswer);

		addComponent(0, 7, 1, 1, .7, generateKey);
		addComponent(1, 7, 3, 1, .7, create);

		addComponent(0, 8, 4, 1, .7, back);
	}

}