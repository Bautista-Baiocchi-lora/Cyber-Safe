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

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.util.user.User;

public class LoginScreen extends JPanel implements ActionListener {
	private final JTextField username;
	private final JPasswordField password, key;
	private final JButton login, createAccount;
	private final JLabel usernameLabel, passwordLabel, keyLabel;
	private int loginAttempts = 0;
	private final JOptionPane popUp;
	private final GridBagConstraints constraints;

	public LoginScreen() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = constraints.NORTHWEST;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(10, 5, 5, 3);

		username = new JTextField();
		password = new JPasswordField();
		key = new JPasswordField();
		usernameLabel = new JLabel("Username: ", JLabel.TRAILING);
		keyLabel = new JLabel("Encryption Key: ", JLabel.TRAILING);
		passwordLabel = new JLabel("Password: ", JLabel.TRAILING);
		login = new JButton("Login");
		login.addActionListener(this);
		createAccount = new JButton("Create New Account");
		createAccount.addActionListener(this);

		popUp = new JOptionPane();

		positionComponents();
		setPreferredSize(new Dimension(300, 175));
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
		addComponent(0, 3, 4, 1, .7, login);
		addComponent(0, 4, 4, 1, .7, createAccount);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equalsIgnoreCase("login")) {
			final User user = new User(username.getText(), String.valueOf(password.getPassword()),
					String.valueOf(key.getPassword()));
			if (loginAttempts < 5) {
				if (!Engine.getInstance().getUserManager().logIn(user)) {
					loginAttempts++;
					popUp.showMessageDialog(this,
							"The username, password, or encryption key you have submitted are invalid. Please try again.\nYou have "
									+ (5 - loginAttempts) + " login attempts left.");
				}
			} else {
				loginAttempts = 3;
				popUp.showMessageDialog(this,
						"You have exceded the amount of login attempts. Please wait 30 seconds before trying again.");
			}
		} else {
			Engine.getInstance().openCreateUserScreen();
		}
	}

}
