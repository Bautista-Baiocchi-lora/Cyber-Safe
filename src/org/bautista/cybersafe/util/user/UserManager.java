package org.bautista.cybersafe.util.user;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.logger.LogType;
import org.bautista.cybersafe.ui.components.logger.Logger;
import org.bautista.cybersafe.util.Cache;
import org.bautista.cybersafe.util.Config;
import org.bautista.cybersafe.util.enctryption.util.BufferedEncryptionReader;
import org.bautista.cybersafe.util.enctryption.util.BufferedEncryptionWriter;

public class UserManager {
	private final ArrayList<User> users;
	private final char[] INVALID_CHARACTERS = { '"', '*', '/', ':', '<', '>', '?', '|', '\\' };

	public UserManager() {
		users = loadUsers();
		Logger.write("Users loaded.", LogType.CLIENT);
	}

	public boolean createUser(final User user) {
		if (keyIsValid(user.getEncryptionKey()) && passwordIsValid(user.getPassword())
				&& usernameIsValid(user.getUsername())
				&& recoveryAnswerIsValid(user.getRecoveryAnswer())
				&& recoveryQuestionIsValid(user.getRecoveryQuestion())) {
			try {
				saveUser(user);
				users.add(user);
				Logger.write("Created user: " + user.getUsername(), LogType.CLIENT);
			} catch (final IOException e) {
				e.printStackTrace();
				Logger.writeException("Error creating user.", LogType.CLIENT);
			}
			return true;
		}
		return false;
	}

	private void createUserDirectory(final User user) {
		final File directory = new File(
				Cache.USER_FOLDER.getAbsolutePath() + File.separator + user.getUsername()
						+ File.separator
						+ "accounts");
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	public User getUserByName(final String username) {
		for (final User user : users) {
			if (user.getUsername().equalsIgnoreCase(username)) {
				return user;
			}
		}
		return null;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public boolean isNameAvaliable(final String name) {
		for (final User user : users) {
			if (user.getUsername().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	private ArrayList<User> loadUsers() {
		final ArrayList<User> list = new ArrayList<User>();
		final File userDirectory = Cache.USER_FOLDER;
		if (userDirectory.exists() && userDirectory.isDirectory()) {
			for (final File file : userDirectory.listFiles()) {
				try (final BufferedEncryptionReader reader = new BufferedEncryptionReader(
						new FileReader(
								file.getAbsolutePath() + File.separator + file.getName()
										+ ".ucsafe"),
						Engine.getInstance().getConfig().getPropertyValue(Config.KEY),
						Engine.getInstance().getConfig()
								.getPropertyValue(Config.INIT_VECTOR));) {
					final HashMap<String, String> accountInfo = new HashMap<String, String>();
					String sCurrentLine;
					while ((sCurrentLine = reader.readLine()) != null) {
						if (sCurrentLine.contains(":")) {
							accountInfo.put(sCurrentLine.split(":")[0], sCurrentLine.split(":")[1]);
						}
					}
					if (accountInfo.size() >= 5) {
						list.add(new User(accountInfo.get("name"), accountInfo.get("password"),
								accountInfo.get("recovery question"),
								accountInfo.get("recovery answer"),
								accountInfo.get("key")));
					}
				} catch (final IOException e) {
					e.printStackTrace();
					Logger.writeException("Error loading users.", LogType.CLIENT);
				}
			}
		}
		return list;
	}

	public boolean logIn(final User user) {
		for (final User u : users) {
			if (u.equals(user)) {
				Engine.getInstance().setCurrentUser(u);
				Engine.getInstance().openSafeScreen();
				return true;
			}
		}
		return false;
	}

	private void saveUser(final User user) throws IOException {
		final File userFile = new File(
				Cache.USER_FOLDER.getAbsolutePath() + File.separator + user.getUsername()
						+ File.separator
						+ user.getUsername() + ".ucsafe");
		if (!userFile.exists()) {
			createUserDirectory(user);
			userFile.createNewFile();
		}
		final BufferedEncryptionWriter writer = new BufferedEncryptionWriter(
				new FileWriter(userFile),
				Engine.getInstance().getConfig().getPropertyValue(Config.KEY),
				Engine.getInstance().getConfig().getPropertyValue(Config.INIT_VECTOR));
		for (final String[] data : user.getData()) {
			writer.write(data[0] + ":" + data[1]);
			writer.newLine();
		}
		writer.close();
	}

	private boolean recoveryAnswerIsValid(String answer) {
		if (answer.length() >= 1) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
				"You left your recovery answer blank! Make sure to make a password you will remember the answer to. It is the only way to recover your password.",
				"Warning!", JOptionPane.OK_OPTION);
		return false;
	}

	private boolean recoveryQuestionIsValid(String question) {
		if (question.length() >= 1) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
				"You left your recovery question blank! Make sure to make a question you will remember the answer to. It is the only way to recover your password.",
				"Warning!", JOptionPane.OK_OPTION);
		return false;
	}

	private boolean usernameIsValid(String username) {
		boolean tooShort = true;
		boolean invalidChar = true;
		if (username.length() >= 4) {
			tooShort = false;
			if (!containsInvalidCharacters(username)) {
				invalidChar = false;
				if (isNameAvaliable(username)) {
					return true;
				}
				JOptionPane.showMessageDialog(null, "That username is already taken.", "Warning!",
						JOptionPane.OK_OPTION);
			}
		}
		if (tooShort) {
			JOptionPane.showMessageDialog(null,
					"Your username must be at least 4 characters long.", "Warning!",
					JOptionPane.OK_OPTION);
		} else if (invalidChar) {
			JOptionPane.showMessageDialog(null,
					"Your username contains invalid characters. Please refrain from using the following characters in your username: ' \" ', ' \\ ', ' / ', ' * ', ' | ', ' ? ', ' : ', ' < ', ' > '",
					"Warning!", JOptionPane.OK_OPTION);
		}
		return false;
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

	private boolean keyIsValid(String key) {
		if (key.length() == 16) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
				"Your Encryption Key is too short. Make sure it is exactly 16 characters long. Remember, you can always have one automatically generated for you.",
				"Warning!", JOptionPane.OK_OPTION);
		return false;
	}

	private boolean passwordIsValid(String password) {
		if (password.length() >= 6) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
				"Your password is too short. Make sure it is at least 6 characters long.",
				"Warning!", JOptionPane.OK_OPTION);
		return false;
	}

}
