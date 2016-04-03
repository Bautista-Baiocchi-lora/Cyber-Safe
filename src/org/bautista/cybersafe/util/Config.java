package org.bautista.cybersafe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.bautista.cybersafe.ui.components.logger.LogType;
import org.bautista.cybersafe.ui.components.logger.Logger;
import org.bautista.cybersafe.util.enctryption.util.KeyGenerator;

public class Config {

	public static final String KEY = "key";
	public static final String INIT_VECTOR = "vector";
	private static final File CONFIG_FILE = new File(Cache.CACHE.getAbsolutePath() + File.separator
			+ "config.properties");
	private static final String[][] DEFAULT_CONFIG_PROPERTIES = {
			{ KEY, KeyGenerator.getNewKey() }, { INIT_VECTOR, KeyGenerator.getNewVector() } };
	private Properties properties;

	public Config() {
		try {
			if (!configExists()) {
				Logger.writeException("Config not detected.", LogType.CLIENT);
				createConfig();
				Logger.write("Config created.", LogType.CLIENT);
				return;
			}
			loadProperties();
			Logger.write("Config loaded.", LogType.CLIENT);
		} catch (final IOException e) {
			Logger.writeException("Error loading config.", LogType.CLIENT);
			e.printStackTrace();
		}
	}

	private boolean configExists() {
		return CONFIG_FILE.exists();
	}

	private void createConfig() throws IOException {
		if (!configExists()) {
			CONFIG_FILE.createNewFile();
		}
		properties = new Properties();
		final InputStream input = new FileInputStream(CONFIG_FILE);
		properties.load(input);
		final OutputStream output = new FileOutputStream(CONFIG_FILE);
		for (int index = 0; index < DEFAULT_CONFIG_PROPERTIES.length; index++) {
			if (!properties.containsKey(DEFAULT_CONFIG_PROPERTIES[index][0])) {
				properties.setProperty(DEFAULT_CONFIG_PROPERTIES[index][0],
						DEFAULT_CONFIG_PROPERTIES[index][1]);
			}
		}
		properties.store(output, null);
		output.close();
		input.close();
	}

	public Properties getProperties() {
		return properties;
	}

	public String getPropertyValue(final String key) {
		return properties.getProperty(key);
	}

	private void loadProperties() throws IOException {
		final InputStream input = new FileInputStream(CONFIG_FILE);
		properties = new Properties();
		properties.load(input);
		input.close();
	}

	public void setProperty(final String key, final String value) throws IOException {
		final OutputStream output = new FileOutputStream(CONFIG_FILE);
		properties.setProperty(key, value);
		properties.store(output, null);
		output.close();
	}

}
