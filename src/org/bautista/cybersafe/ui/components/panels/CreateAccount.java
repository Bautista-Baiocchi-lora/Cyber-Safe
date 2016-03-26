package org.bautista.cybersafe.ui.components.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TrayIcon.MessageType;
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
import javax.swing.text.DefaultStyledDocument;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.util.enctryption.KeyGenerator;
import org.bautista.cybersafe.util.user.User;

public class CreateAccount extends JPanel implements ActionListener {
	private final JTextField username;
	private final JPasswordField password, confirmPassword, confirmKey, key;
	private final JButton create, generateKey;
	private final JLabel usernameLabel, passwordLabel, keyLabel, confirmPasswordLabel,
			confirmKeyLabel;
	private final GridBagConstraints constraints;
	private final JOptionPane popUp;
	private final char[] INVALID_CHARACTERS = { '"', '*', '/', ':', '<', '>', '?', '|', '\\' };

	public CreateAccount() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = constraints.NORTHWEST;
		constraints.weightx = 1;
		constraints.weighty = 1;

		popUp = new JOptionPane();
		username = new JTextField();
		confirmPassword = new JPasswordField();
		password = new JPasswordField();
		key = new JPasswordField();
		confirmKey = new JPasswordField();
		usernameLabel = new JLabel("Username: ", JLabel.TRAILING);
		keyLabel = new JLabel("Encryption Key: ", JLabel.TRAILING);
		passwordLabel = new JLabel("Password: ", JLabel.TRAILING);
		confirmKeyLabel = new JLabel("Cofirm Key: ", JLabel.TRAILING);
		confirmPasswordLabel = new JLabel("Confirm Password: ", JLabel.TRAILING);
		create = new JButton("Create Account");
		create.addActionListener(this);
		generateKey = new JButton("Generate Encryption Key");
		generateKey.addActionListener(this);

		positionComponents();
		setPreferredSize(new Dimension(300, 120));
	}

	private void addComponent(final int x, final int y, final int yPad,
			final int xPad, final int width,
			final JComponent comp) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.ipadx = xPad;
		constraints.ipady = yPad;
		constraints.gridwidth = width;
		add(comp, constraints);
	}

	private void positionComponents() {
		addComponent(0, 0, 0, 0, 1, usernameLabel);
		addComponent(1, 0, 0, 0, 5, username);

		addComponent(0, 1, 0, 0, 1, passwordLabel);
		addComponent(1, 1, 0, 0, 3, password);

		addComponent(0, 2, 0, 0, 1, confirmPasswordLabel);
		addComponent(1, 2, 0, 0, 3, confirmPassword);

		addComponent(0, 3, 0, 0, 1, keyLabel);
		addComponent(1, 3, 0, 0, 3, key);

		addComponent(0, 4, 0, 0, 1, confirmKeyLabel);
		addComponent(1, 4, 0, 0, 3, confirmKey);

		addComponent(0, 5, 0, 0, 1, generateKey);
		addComponent(1, 5, 0, 0, 2, create);
	}

	private boolean keyIsValid() {
		boolean tooShort = true;
		char[] eKey = key.getPassword();
		char[] confirmEKey = confirmKey.getPassword();
		if (eKey.length == 16) {
			tooShort = false;
			if (Arrays.equals(eKey, confirmEKey)) {
				return true;
			}
			popUp.showMessageDialog(this,
					"Your Encryption Keys don't match. Remember, you can always have one automatically generated for you.");
		}
		if (tooShort) {
			popUp.showMessageDialog(this,
					"Your Encryption Key is too short. Make sure it is exactly 16 characters long. Remember, you can always have one automatically generated for you.");

		}
		return false;
	}

	private boolean passwordIsValid() {
		boolean tooShort = true;
		char[] pass = password.getPassword();
		char[] confirmPass = confirmPassword.getPassword();
		if (pass.length > 6) {
			tooShort = false;
			if (Arrays.equals(pass, confirmPass)) {
				return true;
			}
			popUp.showMessageDialog(this,
					"Your password's dont match.");
		}
		if (tooShort) {
			popUp.showMessageDialog(this,
					"Your password is too short. Make sure it is at least 6 characters long.");
		}
		return false;
	}

	private boolean containsInvalidCharacters(String username) {
		for (char c : username.toCharArray()) {
			for (int i = 0; i < INVALID_CHARACTERS.length; i++) {
				if (c == INVALID_CHARACTERS[i]) {
					return true;
				}
			}
		}
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
				popUp.showMessageDialog(this, "That username is already taken.");
			}
		}
		if (tooShort) {
			popUp.showMessageDialog(this,
					"Your username must be at least 4 characters long.");
		} else if (invalidChar) {
			popUp.showMessageDialog(this,
					"Your username contains invalid characters. Please refrain from using the following characters in your username: ' \" ', ' \\ ', ' / ', ' * ', ' | ', ' ? ', ' : ', ' < ', ' > '");
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equalsIgnoreCase("Create Account")) {
			if (usernameIsValid() && passwordIsValid() && keyIsValid()) {
				final User user = new User(username.getText(),
						String.valueOf(password.getPassword()), String.valueOf(key.getPassword()));
				Engine.getInstance().getUserManager().createUser(user);
				Engine.getInstance().getUserManager().login(user);
			}
		} else {
			String newKey = KeyGenerator.getNewKey();
			key.setText(newKey);
			confirmKey.setText(newKey);
			popUp.showMessageDialog(null, "Your new key is: " + newKey
					+ "\nPlease write it down somewhere you wont forget. You can not access any of your information without it.");
		}
	}

}
