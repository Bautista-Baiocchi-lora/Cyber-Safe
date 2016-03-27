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
	private ArrayList<User> users;

	public UserManager() {
		users = loadUsers();
	}

	public boolean logIn(User user) {
		for (User u : users) {
			if (u.equals(user)) {
				Variables.setCurrentUser(u);
				Engine.getInstance().openSafeScreen();
				return true;
			}
		}
		return false;
	}

	public void logOut(User user) {
		Variables.setCurrentUser(null);
		Engine.getInstance().openLoginScreen();
	}

	public boolean isNameAvaliable(String name) {
		for (User user : users) {
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
			for (File file : userDirectory.listFiles()) {
				try {
					final HashMap<String, String> accountInfo = new HashMap<String, String>();
					final BufferedEncryptionReader reader = new BufferedEncryptionReader(
							new FileReader(
									file.getAbsolutePath() + File.separator + file.getName()
											+ ".ucsafe"),
							Engine.getInstance().getConfig().getPropertyValue(Config.KEY),
							Engine.getInstance().getConfig()
									.getPropertyValue(Config.INIT_VECTOR));
					String sCurrentLine;
					while ((sCurrentLine = reader.readLine()) != null) {
						if (sCurrentLine.contains(":")) {
							accountInfo.put(sCurrentLine.split(":")[0], sCurrentLine.split(":")[1]);
						}
					}
					if (accountInfo.size() >= 3) {
						list.add(new User(accountInfo.get("name"), accountInfo.get("password"),
								accountInfo.get("key")));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public void createUser(User user) {
		users.add(user);
		try {
			saveUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createUserDirectory(User user) {
		final File directory = new File(
				Cache.USERS_PATH + File.separator + user.getUsername() + File.separator
						+ "accounts");
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	private void saveUser(User user) throws IOException {
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
		for (String[] data : user.getData()) {
			writer.write(data[0] + ":" + data[1]);
			writer.newLine();
		}
		writer.close();
	}

	public ArrayList<User> getUsers() {
		return users;
	}

}
