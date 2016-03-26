package org.bautista.cybersafe.util;

import java.io.File;

public class Cache {

	public static final String CACHE_PATH = System.getProperty("user.home") + File.separator
			+ "Cyber Safe";
	public static final String USERS_PATH = System.getProperty("user.home") + File.separator
			+ "Cyber Safe" + File.separator + "Users";

	public static boolean cacheExists() {
		final File cache = new File(CACHE_PATH);
		return cache.exists();
	}

	public static boolean createCache() {
		final File cache = new File(CACHE_PATH);
		if (cache.mkdirs()) {
			final File users = new File(USERS_PATH);
			return users.mkdirs();
		}
		return false;
	}
}
