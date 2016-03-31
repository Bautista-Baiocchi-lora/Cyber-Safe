package org.bautista.cybersafe.util;

import java.io.File;

public class Cache {

	public static final File CACHE = new File(System.getProperty("user.home") + File.separator
			+ "Cyber Safe");
	public static final File USER_FOLDER = new File(System.getProperty("user.home") + File.separator
			+ "Cyber Safe" + File.separator + "Users");

	public static boolean cacheExists() {
		return CACHE.exists();
	}

	public static boolean createCache() {
		if (CACHE.mkdirs()) {
			return USER_FOLDER.mkdirs();
		}
		return false;
	}
}
