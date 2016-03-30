package org.bautista.cybersafe.util.enctryption.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.bautista.cybersafe.util.enctryption.Encryptor;

public class EncryptedObjectOutputStream extends BufferedOutputStream {
	private final String key;
	private final String vector;

	public EncryptedObjectOutputStream(final FileOutputStream out, final String key,
			final String vector)
			throws IOException {
		super(out);
		this.key = key;
		this.vector = vector;
	}

	public void writeEncryptedObject(final Serializable object)
			throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		if (object != null) {
			Encryptor.encrypt(key, vector, this, object);
		}
	}

}
