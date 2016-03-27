package org.bautista.cybersafe.util.enctryption.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import org.bautista.cybersafe.util.enctryption.Encryptor;

public class BufferedEncryptionWriter extends BufferedWriter {
	private String key;
	private String vector;

	public BufferedEncryptionWriter(Writer writer, String key, String vector) {
		super(writer);
		this.key = key;
		this.vector = vector;
	}

	public void write(String str) throws IOException {
		if (str == null) {
			return;
		}
		super.write(Encryptor.encrypt(key, vector, str));
	}

	public void newLine() throws IOException {
		super.write(System.getProperty("line.separator"));
	}

}
