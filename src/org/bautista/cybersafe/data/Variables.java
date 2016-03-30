package org.bautista.cybersafe.data;

import org.bautista.cybersafe.util.user.User;

public class Variables {

	private static User currentUser;

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(final User i) {
		currentUser = i;
	}
}
