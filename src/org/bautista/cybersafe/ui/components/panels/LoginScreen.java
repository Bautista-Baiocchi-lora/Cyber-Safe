package org.bautista.cybersafe.ui.components.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.util.user.User;

public class LoginScreen extends JPanel implements ActionListener {
	private final JTextField username;
	private final JPasswordField password, key;
	private final JButton login, createAccount, recoverAccount;
	private final JLabel usernameLabel, passwordLabel, keyLabel;
	private int loginAttempts = 0;
	private final JOptionPane popUp;
	private final GridBagConstraints constraints;

	public LoginScreen() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(8, 5, 3, 3);

		username = new JTextField("panda");
		password = new JPasswordField("juan123");
		key = new JPasswordField("1111111111111111");
		usernameLabel = new JLabel("Username: ", SwingConstants.TRAILING);
		keyLabel = new JLabel("Encryption Key: ", SwingConstants.TRAILING);
		passwordLabel = new JLabel("Password: ", SwingConstants.TRAILING);
		login = new JButton("Login");
		createAccount = new JButton("Create New Account");
		recoverAccount = new JButton("Recover Account");

		popUp = new JOptionPane();

		positionComponents();
		setListeners();
		setPreferredSize(new Dimension(300, 220));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String command = e.getActionCommand();
		if (command.equalsIgnoreCase("login")) {
			final User user = new User(username.getText(), String.valueOf(password.getPassword()),
					String.valueOf(key.getPassword()));
			if (loginAttempts < 5) {
				if (!Engine.getInstance().getUserManager().logIn(user)) {
					loginAttempts++;
					JOptionPane.showMessageDialog(this,
							"The username, password, or encryption key you have submitted are invalid. Please try again.\nYou have "
									+ (5 - loginAttempts) + " login attempts left.");
				}
			} else {
				loginAttempts = 3;
				JOptionPane.showMessageDialog(this,
						"You have exceded the amount of login attempts. Please wait 30 seconds before trying again.");
			}
		} else if (command.equalsIgnoreCase("Create New Account")) {
			Engine.getInstance().openCreateUserScreen();
		} else {
			if (username.getText().length() > 0) {
				final User user = Engine.getInstance().getUserManager()
						.getUserByName(username.getText());
				if (user != null) {
					final String answer = JOptionPane.showInputDialog(this,
							user.getRecoveryQuestion());
					if ((answer != null) && answer.equalsIgnoreCase(user.getRecoveryAnswer())) {
						JOptionPane.showMessageDialog(null,
								"Username: " + user.getUsername()
										+ "\nPassword: "
										+ user.getPassword()
										+ "\nRecovery Question: "
										+ user.getRecoveryQuestion()
										+ "\nRecovery Question Answer: "
										+ user.getRecoveryAnswer()
										+ "\nEncryption Key: "
										+ user.getEncryptionKey(),
								"Information", JOptionPane.OK_OPTION);
					} else {
						JOptionPane.showMessageDialog(this,
								"Incorrect answer.");
					}
				} else {
					JOptionPane.showMessageDialog(this,
							"Account doesn't exist.");
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Please enter a username before attempting to recover an account.");
			}
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
		addComponent(1, 0, 3, 1, 1, username);
		addComponent(0, 1, 1, .1, 1, passwordLabel);
		addComponent(1, 1, 3, 1, 1, password);
		addComponent(0, 2, 1, .1, 1, keyLabel);
		addComponent(1, 2, 3, 1, 1, key);
		addComponent(0, 3, 4, 1, .5, login);
		addComponent(0, 4, 4, 1, .5, recoverAccount);
		addComponent(0, 5, 4, 1, .5, createAccount);
	}

	private void setListeners() {
		recoverAccount.addActionListener(this);
		createAccount.addActionListener(this);
		login.addActionListener(this);
	}

}
