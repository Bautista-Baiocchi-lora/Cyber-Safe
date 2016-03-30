package org.bautista.cybersafe.util.enctryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM = "AES";

	public static Object decrypt(final String key, final String initVector,
			final InputStream istream)
			throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		final IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		final SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), ALGORITHM);

		final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

		final CipherInputStream cipherInputStream = new CipherInputStream(istream, cipher);
		final ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
		SealedObject sealedObject;
		try {
			sealedObject = (SealedObject) inputStream.readObject();
			return sealedObject.getObject(cipher);
		} catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(final String key, final String initVector,
			final String encrypted) {
		try {
			final IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			final SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), ALGORITHM);

			final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			final byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static void encrypt(final String key, final String initVector,
			final OutputStream ostream,
			final Serializable object)
			throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException {
		try {
			final IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			final SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), ALGORITHM);

			final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			final SealedObject sealedObject = new SealedObject(object, cipher);

			final CipherOutputStream cos = new CipherOutputStream(ostream, cipher);
			final ObjectOutputStream outputStream = new ObjectOutputStream(cos);
			outputStream.writeObject(sealedObject);
			outputStream.close();
		} catch (final IllegalBlockSizeException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(final String key, final String initVector, final String value) {
		try {
			final IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			final SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);

			final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			final byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.encodeBase64String(encrypted);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}