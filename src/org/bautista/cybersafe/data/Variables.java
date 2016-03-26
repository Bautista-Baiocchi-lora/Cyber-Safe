package org.bautista.cybersafe.data;

import org.bautista.cybersafe.util.user.User;

public class Variables {

	private static User currentUser;

	public static void setCurrentUser(User i) {
		currentUser = i;
	}

	public static User getCurrentUser() {
		return currentUser;
	}
}
