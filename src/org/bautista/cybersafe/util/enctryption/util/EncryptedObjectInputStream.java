package org.bautista.cybersafe.util.enctryption.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.bautista.cybersafe.util.enctryption.Encryptor;

public class EncryptedObjectInputStream extends BufferedInputStream {
	private final String key;
	private final String vector;

	public EncryptedObjectInputStream(final FileInputStream stream, final String key,
			final String vector)
			throws IOException {
		super(stream);
		this.key = key;
		this.vector = vector;
	}

	public Object readEncryptedObject()
			throws ClassNotFoundException, IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		return Encryptor.decrypt(key, vector, this);
	}

}
