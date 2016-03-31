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
	private final ArrayList<Account> accounts;
	private ArrayList<Account> filteredAccounts;
	private final User user;

	public AccountManager(final User user) {
		this.user = user;
		accounts = loadAccounts(user);
		filteredAccounts = accounts;
	}

	public void filterAccounts(String name, String type) {
		final ArrayList<Account> list = new ArrayList<Account>();
		for (Account account : accounts) {
			if (account.getType().getName().equalsIgnoreCase(type)
					&& account.getName().contains(name)) {
				list.add(account);
			}
		}
		filteredAccounts = list;
	}

	public ArrayList<Account> getFilteredAccounts() {
		return filteredAccounts;
	}

	public void createAccount(final Account account) {
		accounts.add(account);
		filteredAccounts = accounts;
		saveAccount(account);
	}

	public void deleteAccount(final Account account) {
		if (account != null) {
			if (accounts.contains(account)) {
				deleteAccountFile(account);
				accounts.remove(account);
			}
		}
	}

	public void deleteAccount(final String name) {
		Account acc = null;
		for (final Account account : accounts) {
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

	private void deleteAccountFile(final Account account) {
		final File accountFile = new File(Cache.USER_FOLDER.getAbsoluteFile() + File.separator
				+ Variables.getCurrentUser().getUsername() + File.separator + "accounts"
				+ File.separator + account.getName() + ".acsafe");
		if (accountFile.exists()) {
			accountFile.delete();
		}
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public boolean isNameAvaliable(final String name) {
		for (final Account account : accounts) {
			if (account.getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	private ArrayList<Account> loadAccounts(final User user) {
		final ArrayList<Account> list = new ArrayList<Account>();
		final File userDirectory = new File(Cache.USER_FOLDER.getAbsoluteFile() + File.separator
				+ Variables.getCurrentUser().getUsername() + File.separator + "accounts");
		if (userDirectory.exists()) {
			for (final File file : userDirectory.listFiles()) {
				try (final FileInputStream inputStream = new FileInputStream(
						file.getAbsolutePath());
						final EncryptedObjectInputStream encryptedStream = new EncryptedObjectInputStream(
								inputStream, Variables.getCurrentUser().getEncryptionKey(),
								Engine.getInstance().getConfig()
										.getPropertyValue(Config.INIT_VECTOR));) {
					final Account account = (Account) encryptedStream.readEncryptedObject();
					if (account != null) {
						list.add(account);
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private void saveAccount(final Account account) {
		final File userDirectory = new File(Cache.USER_FOLDER.getAbsoluteFile() + File.separator
				+ Variables.getCurrentUser().getUsername() + File.separator + "accounts");
		if (userDirectory.exists()) {
			final File accountFile = new File(Cache.USER_FOLDER.getAbsoluteFile() + File.separator
					+ Variables.getCurrentUser().getUsername() + File.separator + "accounts"
					+ File.separator + account.getName() + ".acsafe");
			try (final FileOutputStream outputStream = new FileOutputStream(accountFile);
					final EncryptedObjectOutputStream encryptedStream = new EncryptedObjectOutputStream(
							outputStream, Variables.getCurrentUser().getEncryptionKey(),
							Engine.getInstance().getConfig()
									.getPropertyValue(Config.INIT_VECTOR));) {
				encryptedStream.writeEncryptedObject(account);
			} catch (IOException | InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
		}
	}

}
