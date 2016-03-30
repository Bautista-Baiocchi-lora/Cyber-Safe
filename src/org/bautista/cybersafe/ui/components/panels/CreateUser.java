package org.bautista.cybersafe.ui.components.panels;

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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.util.enctryption.util.KeyGenerator;
import org.bautista.cybersafe.util.user.User;

public class CreateUser extends JPanel implements ActionListener {
	private final JTextField username, recoveryQuestion, recoveryAnswer;
	private final JPasswordField password, confirmPassword, confirmKey, key;
	private final JButton create, generateKey, back;
	private final JLabel usernameLabel, passwordLabel, keyLabel, confirmPasswordLabel,
			confirmKeyLabel, recoveryQuestionLabel, recoveryAnswerLabel;
	private final GridBagConstraints constraints;
	private final JOptionPane popUp;
	private final char[] INVALID_CHARACTERS = { '"', '*', '/', ':', '<', '>', '?', '|', '\\' };

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
		create.addActionListener(this);
		generateKey = new JButton("Generate Encryption Key");
		generateKey.setActionCommand("generate key");
		generateKey.addActionListener(this);
		back = new JButton("Back");
		back.addActionListener(this);

		positionComponents();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String command = e.getActionCommand().toLowerCase();
		switch (command) {
			case "create account":
				if (usernameIsValid() && passwordIsValid() && keyIsValid()
						&& recoveryQuestionIsValid() && recoveryAnsswerIsValid()) {
					final User user = new User(username.getText(),
							String.valueOf(password.getPassword()), recoveryQuestion.getText(),
							recoveryAnswer.getText(),
							String.valueOf(key.getPassword()));
					Engine.getInstance().getUserManager().createUser(user);
					Engine.getInstance().getUserManager().logIn(user);
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

	private boolean keyIsValid() {
		boolean tooShort = true;
		final char[] eKey = key.getPassword();
		final char[] confirmEKey = confirmKey.getPassword();
		if (eKey.length == 16) {
			tooShort = false;
			if (Arrays.equals(eKey, confirmEKey)) {
				return true;
			}
			JOptionPane.showMessageDialog(this,
					"Your Encryption Keys don't match. Remember, you can always have one automatically generated for you.",
					"Warning!", JOptionPane.OK_OPTION);
		}
		if (tooShort) {
			JOptionPane.showMessageDialog(this,
					"Your Encryption Key is too short. Make sure it is exactly 16 characters long. Remember, you can always have one automatically generated for you.",
					"Warning!", JOptionPane.OK_OPTION);

		}
		return false;
	}

	private boolean passwordIsValid() {
		boolean tooShort = true;
		final char[] pass = password.getPassword();
		final char[] confirmPass = confirmPassword.getPassword();
		if (pass.length > 6) {
			tooShort = false;
			if (Arrays.equals(pass, confirmPass)) {
				return true;
			}
			JOptionPane.showMessageDialog(this,
					"Your password's dont match.", "Warning!", JOptionPane.OK_OPTION);
		}
		if (tooShort) {
			JOptionPane.showMessageDialog(this,
					"Your password is too short. Make sure it is at least 6 characters long.",
					"Warning!", JOptionPane.OK_OPTION);
		}
		return false;
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

	private boolean recoveryAnsswerIsValid() {
		if (recoveryAnswer.getText().length() >= 1) {
			return true;
		}
		JOptionPane.showMessageDialog(this,
				"You left your recovery answer blank! Make sure to make a password you will remember the answer to. It is the only way to recover your password.",
				"Warning!", JOptionPane.OK_OPTION);
		return false;
	}

	private boolean recoveryQuestionIsValid() {
		if (recoveryQuestion.getText().length() >= 1) {
			return true;
		}
		JOptionPane.showMessageDialog(this,
				"You left your recovery question blank! Make sure to make a question you will remember the answer to. It is the only way to recover your password.",
				"Warning!", JOptionPane.OK_OPTION);
		return false;
	}

	private boolean usernameIsValid() {
		boolean tooShort = true;
		boolean invalidChar = true;
		if (username.getText().length() >= 4) {
			tooShort = false;
			if (!containsInvalidCharacters(username.getText())) {
				invalidChar = false;
				if (Engine.getInstance().getUserManager().isNameAvaliable(username.getText())) {
					return true;
				}
				JOptionPane.showMessageDialog(this, "That username is already taken.", "Warning!",
						JOptionPane.OK_OPTION);
			}
		}
		if (tooShort) {
			JOptionPane.showMessageDialog(this,
					"Your username must be at least 4 characters long.", "Warning!",
					JOptionPane.OK_OPTION);
		} else if (invalidChar) {
			JOptionPane.showMessageDialog(this,
					"Your username contains invalid characters. Please refrain from using the following characters in your username: ' \" ', ' \\ ', ' / ', ' * ', ' | ', ' ? ', ' : ', ' < ', ' > '",
					"Warning!", JOptionPane.OK_OPTION);
		}
		return false;
	}

}