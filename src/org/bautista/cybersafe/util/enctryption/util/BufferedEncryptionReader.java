package org.bautista.cybersafe.util.enctryption.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.bautista.cybersafe.util.enctryption.Encryptor;

public class BufferedEncryptionReader extends BufferedReader {
	private String key;
	private String vector;

	public BufferedEncryptionReader(Reader reader, String key, String vector) {
		super(reader);
		this.key = key;
		this.vector = vector;
	}

	public String readLine() throws IOException {
		String encryptedLine = super.readLine();
		if (encryptedLine != null) {
			String decryptedLine = Encryptor.decrypt(key, vector, encryptedLine);
			return decryptedLine;
		}
		return null;
	}

}
