package org.bautista.cybersafe.util.enctryption.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import org.bautista.cybersafe.util.enctryption.Encryptor;

public class BufferedEncryptionWriter extends BufferedWriter {
	private final String key;
	private final String vector;

	public BufferedEncryptionWriter(final Writer writer, final String key, final String vector) {
		super(writer);
		this.key = key;
		this.vector = vector;
	}

	@Override
	public void newLine() throws IOException {
		super.write(System.getProperty("line.separator"));
	}

	@Override
	public void write(final String str) throws IOException {
		if (str == null) {
			return;
		}
		super.write(Encryptor.encrypt(key, vector, str));
	}

	public void write(final String str, final boolean newLine) throws IOException {
		if (str == null) {
			return;
		}
		super.write(Encryptor.encrypt(key, vector, str));
		if (newLine) {
			newLine();
		}
	}

}
