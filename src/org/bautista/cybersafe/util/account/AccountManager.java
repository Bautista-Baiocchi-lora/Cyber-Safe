package org.bautista.cybersafe.util.account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.data.Variables;
import org.bautista.cybersafe.util.Cache;
import org.bautista.cybersafe.util.Config;
import org.bautista.cybersafe.util.enctryption.util.EncryptedObjectInputStream;
import org.bautista.cybersafe.util.enctryption.util.EncryptedObjectOutputStream;
import org.bautista.cybersafe.util.user.User;

public class AccountManager {
	private ArrayList<Account> accounts;
	private User user;

	public AccountManager(User user) {
		this.user = user;
		accounts = loadAccounts(user);
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void createAccount(Account account) {
		accounts.add(account);
		saveAccount(account);
	}

	public boolean isNameAvaliable(String name) {
		for (Account account : accounts) {
			if (account.getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	private void deleteAccountFile(Account account) {
		final File accountFile = new File(Cache.USERS_PATH + File.separator
				+ Variables.getCurrentUser().getUsername() + File.separator + "accounts"
				+ File.separator + account.getName() + ".acsafe");
		if (accountFile.exists()) {
			accountFile.delete();
		}
	}

	public void deleteAccount(Account account) {
		if (account != null) {
			if (accounts.contains(account)) {
				deleteAccountFile(account);
				accounts.remove(account);
			}
		}
	}

	public void deleteAccount(String name) {
		Account acc = null;
		for (Account account : accounts) {
			if (account.getName().equalsIgnoreCase(name)) {
				acc = account;
				break;
			}
		}
		if (acc != null) {
			deleteAccountFile(acc);
			accounts.remove(acc);
		}
	}

	private void saveAccount(Account account) {
		final File userDirectory = new File(Cache.USERS_PATH + File.separator
				+ Variables.getCurrentUser().getUsername() + File.separator + "accounts");
		if (userDirectory.exists()) {
			final File accountFile = new File(Cache.USERS_PATH + File.separator
					+ Variables.getCurrentUser().getUsername() + File.separator + "accounts"
					+ File.separator + account.getName() + ".acsafe");
			try {
				final FileOutputStream outputStream = new FileOutputStream(accountFile);
				final EncryptedObjectOutputStream encryptedStream = new EncryptedObjectOutputStream(
						outputStream, Variables.getCurrentUser().getEncryptionKey(),
						Engine.getInstance().getConfig().getPropertyValue(Config.INIT_VECTOR));
				encryptedStream.writeEncryptedObject(account);
				encryptedStream.close();
			} catch (IOException | InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<Account> loadAccounts(User user) {
		final ArrayList<Account> list = new ArrayList<Account>();
		final File userDirectory = new File(Cache.USERS_PATH + File.separator
				+ Variables.getCurrentUser().getUsername() + File.separator + "accounts");
		if (userDirectory.exists()) {
			for (File file : userDirectory.listFiles()) {
				try {
					final FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());
					final EncryptedObjectInputStream encryptedStream = new EncryptedObjectInputStream(
							inputStream, Variables.getCurrentUser().getEncryptionKey(),
							Engine.getInstance().getConfig().getPropertyValue(Config.INIT_VECTOR));
					final Account account = (Account) encryptedStream.readEncryptedObject();
					if (account != null) {
						list.add(account);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}
