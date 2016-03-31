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

	@Override
	public int hashCode() {
		final int prime = 13;
		int result = 0;
		result = result * prime + username.hashCode();
		result = result * prime + password.hashCode();
		result = result * prime + encryptionKey.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else if (!(object instanceof User)) {
			return false;
		}
		final User user = (User) object;
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
