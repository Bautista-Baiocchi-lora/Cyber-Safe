package org.bautista.cybersafe.util.user;

public class User {

	private final String username;
	private final String password;
	private final String encryptionKey;
	private String recoveryQuestion;
	private String recoveryAnswer;

	public User(final String username, final String password, final String encryptionKey) {
		this.username = username;
		this.password = password;
		this.encryptionKey = encryptionKey;
	}

	public User(final String username, final String password, final String recoveryQuestion,
			final String recoveryAnswer,
			final String encryptionKey) {
		this.username = username;
		this.password = password;
		this.encryptionKey = encryptionKey;
		this.recoveryAnswer = recoveryAnswer;
		this.recoveryQuestion = recoveryQuestion;
	}

	public boolean equals(final User user) {
		return user.getUsername().equalsIgnoreCase(username)
				&& user.getEncryptionKey().equalsIgnoreCase(encryptionKey)
				&& user.getPassword().equalsIgnoreCase(password);
	}

	public String[][] getData() {
		final String[][] data = { { "name", username }, { "password", password },
				{ "key", encryptionKey }, { "recovery question", recoveryQuestion },
				{ "recovery answer", recoveryAnswer } };
		return data;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public String getPassword() {
		return password;
	}

	public String getRecoveryAnswer() {
		return recoveryAnswer;
	}

	public String getRecoveryQuestion() {
		return recoveryQuestion;
	}

	public String getUsername() {
		return username;
	}

}
