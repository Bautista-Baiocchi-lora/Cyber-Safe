package org.bautista.cybersafe.util.user;

public class User {

	private final String username;
	private final String password;
	private final String encryptionKey;
	private String recoveryQuestion;
	private String recoveryAnswer;

	public User(String username, String password, String recoveryQuestion, String recoveryAnswer,
			String encryptionKey) {
		this.username = username;
		this.password = password;
		this.encryptionKey = encryptionKey;
		this.recoveryAnswer = recoveryAnswer;
		this.recoveryQuestion = recoveryQuestion;
	}

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
				{ "key", encryptionKey }, { "recovery question", recoveryQuestion },
				{ "recovery answer", recoveryAnswer } };
		return data;
	}

	public String getRecoveryQuestion() {
		return recoveryQuestion;
	}

	public String getRecoveryAnswer() {
		return recoveryAnswer;
	}

	public boolean equals(User user) {
		return user.getUsername().equalsIgnoreCase(username)
				&& user.getEncryptionKey().equalsIgnoreCase(encryptionKey)
				&& user.getPassword().equalsIgnoreCase(password);
	}

}
