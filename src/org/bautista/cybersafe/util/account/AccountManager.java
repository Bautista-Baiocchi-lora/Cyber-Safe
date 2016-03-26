package org.bautista.cybersafe.util.account;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bautista.cybersafe.util.Cache;
import org.bautista.cybersafe.util.enctryption.Encryptor;
import org.bautista.cybersafe.util.user.User;

public class AccountManager {
	private ArrayList<Account> accounts;
	private User user;

	public AccountManager(User user) {
		this.user = user;
		accounts = loadAccounts(user);
	}

	private ArrayList<Account> loadAccounts(User user) {
		final ArrayList<Account> list = new ArrayList<Account>();
		final File userDirectory = new File(Cache.USERS_PATH + File.separator + "accounts");
		if (userDirectory.exists()) {
			for (File file : userDirectory.listFiles()) {
				try {
					final File f = Encryptor.decrypt(user.getEncryptionKey(), file);
				} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
						| NoSuchAlgorithmException | NoSuchPaddingException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}
