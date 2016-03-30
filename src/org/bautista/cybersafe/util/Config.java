package org.bautista.cybersafe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.bautista.cybersafe.util.enctryption.util.KeyGenerator;

public class Config {

	public static final String KEY = "key";
	public static final String INIT_VECTOR = "vector";
	private static final String CONFIG_FILE_PATH = Cache.CACHE_PATH + File.separator
			+ "config.properties";
	private static final String[][] DEFAULT_CONFIG_PROPERTIES = {
			{ KEY, KeyGenerator.getNewKey() }, { INIT_VECTOR, KeyGenerator.getNewVector() } };
	private Properties properties;

	public Config() {
		try {
			if (createConfig()) {
				properties = loadProperties();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private boolean configExists() {
		final File config = new File(CONFIG_FILE_PATH);
		return config.exists();
	}

	private boolean createConfig() throws IOException {
		final Properties properties = new Properties();
		final InputStream input = new FileInputStream(CONFIG_FILE_PATH);
		properties.load(input);
		final OutputStream output = new FileOutputStream(CONFIG_FILE_PATH);
		for (int index = 0; index < DEFAULT_CONFIG_PROPERTIES.length; index++) {
			if (!properties.containsKey(DEFAULT_CONFIG_PROPERTIES[index][0])) {
				properties.setProperty(DEFAULT_CONFIG_PROPERTIES[index][0],
						DEFAULT_CONFIG_PROPERTIES[index][1]);
			}
		}
		properties.store(output, null);
		output.close();
		input.close();
		return configExists();
	}

	public Properties getProperties() {
		return properties;
	}

	public String getPropertyValue(final String key) {
		return properties.getProperty(key);
	}

	private Properties loadProperties() throws IOException {
		final InputStream input = new FileInputStream(CONFIG_FILE_PATH);
		final Properties properties = new Properties();
		properties.load(input);
		input.close();
		return properties;
	}

	public void setProperty(final String key, final String value) throws IOException {
		if (configExists()) {
			final InputStream input = new FileInputStream(CONFIG_FILE_PATH);
			final OutputStream output = new FileOutputStream(CONFIG_FILE_PATH);
			final Properties properties = new Properties();
			properties.load(input);
			properties.setProperty(key, value);
			properties.store(output, null);
			output.close();
			input.close();
		}
	}

}
