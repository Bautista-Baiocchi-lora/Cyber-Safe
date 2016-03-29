package org.bautista.cybersafe.util.user;

public class User {

	private final String username;
	private final String password;
	private final String encryptionKey;

	public User(String username, String password, String encryptionKey) {
		this.username = username;
		this.password = password;
		this.encryptionKey = encryptionKey;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public String[][] getData() {
		String[][] data = { { "name", username }, { "password", password },
				{ "key", encryptionKey } };
		return data;
	}

	public boolean equals(User user) {
		return user.getUsername().equalsIgnoreCase(username)
				&& user.getEncryptionKey().equalsIgnoreCase(encryptionKey)
				&& user.getPassword().equalsIgnoreCase(password);
	}

}
