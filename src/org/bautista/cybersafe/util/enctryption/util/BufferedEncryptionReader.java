package org.bautista.cybersafe.util.enctryption.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.bautista.cybersafe.util.enctryption.Encryptor;

public class BufferedEncryptionReader extends BufferedReader {
	private final String key;
	private final String vector;

	public BufferedEncryptionReader(final Reader reader, final String key, final String vector) {
		super(reader);
		this.key = key;
		this.vector = vector;
	}

	@Override
	public String readLine() throws IOException {
		final String encryptedLine = super.readLine();
		if (encryptedLine != null) {
			final String decryptedLine = Encryptor.decrypt(key, vector, encryptedLine);
			return decryptedLine;
		}
		return null;
	}

}
