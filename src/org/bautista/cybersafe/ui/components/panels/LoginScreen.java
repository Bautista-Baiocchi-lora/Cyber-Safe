package org.bautista.cybersafe.ui.components.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

	public LoginScreen() {
		setLayout(new GridLayout(4, 1));

		username = new JTextField();
		password = new JPasswordField();
		key = new JPasswordField();
		usernameLabel = new JLabel("Username: ", JLabel.TRAILING);
		keyLabel = new JLabel("Encryption Key: ", JLabel.TRAILING);
		passwordLabel = new JLabel("Password: ", JLabel.TRAILING);
		login = new JButton("Login");
		login.addActionListener(this);
		createAccount = new JButton("Create Account");
		createAccount.addActionListener(this);

		popUp = new JOptionPane();

		setPreferredSize(new Dimension(300, 120));
		positionComponents();
	}

	private void positionComponents() {
		add(usernameLabel);
		add(username);
		add(passwordLabel);
		add(password);
		add(keyLabel);
		add(key);
		add(createAccount);
		add(login);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equalsIgnoreCase("login")) {
			final User user = new User(username.getText(), String.valueOf(password.getPassword()),
					String.valueOf(key.getPassword()));
			if (loginAttempts < 5) {
				if (!Engine.getInstance().getUserManager().login(user)) {
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
