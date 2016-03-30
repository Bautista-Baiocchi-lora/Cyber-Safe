package org.bautista.cybersafe.util.user;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.data.Variables;
import org.bautista.cybersafe.util.Cache;
import org.bautista.cybersafe.util.Config;
import org.bautista.cybersafe.util.enctryption.util.BufferedEncryptionReader;
import org.bautista.cybersafe.util.enctryption.util.BufferedEncryptionWriter;

public class UserManager {
	private final ArrayList<User> users;

	public UserManager() {
		users = loadUsers();
	}

	public void createUser(final User user) {
		users.add(user);
		try {
			saveUser(user);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void createUserDirectory(final User user) {
		final File directory = new File(
				Cache.USERS_PATH + File.separator + user.getUsername() + File.separator
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
		final File userDirectory = new File(Cache.USERS_PATH);
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
				}
			}
		}
		return list;
	}

	public boolean logIn(final User user) {
		for (final User u : users) {
			if (u.equals(user)) {
				Variables.setCurrentUser(u);
				Engine.getInstance().openSafeScreen();
				return true;
			}
		}
		return false;
	}

	public void logOut(final User user) {
		Variables.setCurrentUser(null);
		Engine.getInstance().openLoginScreen();
	}

	private void saveUser(final User user) throws IOException {
		final File userFile = new File(
				Cache.USERS_PATH + File.separator + user.getUsername() + File.separator
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

}
