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
import javax.swing.JOptionPane;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.logger.LogType;
import org.bautista.cybersafe.ui.components.logger.Logger;
import org.bautista.cybersafe.util.Cache;
import org.bautista.cybersafe.util.Config;
import org.bautista.cybersafe.util.enctryption.util.EncryptedObjectInputStream;
import org.bautista.cybersafe.util.enctryption.util.EncryptedObjectOutputStream;

public class AccountManager {
	private final ArrayList<Account> accounts;
	private ArrayList<Account> filteredAccounts;
	private final char[] INVALID_CHARACTERS = { '"', '*', '/', ':', '<', '>', '?', '|', '\\' };

	public AccountManager() {
		accounts = loadAccounts();
		Logger.write("Accounts loaded.", LogType.CLIENT);
		filteredAccounts = accounts;
	}

	private boolean containsInvalidCharacters(final String username) {
		for (final char c : username.toCharArray()) {
			for (final char element : INVALID_CHARACTERS) {
				if (c == element) {
					JOptionPane.showMessageDialog(null,
							"This account name contains invalid characters. Please refrain from using the following characters in your account name: ' \" ', ' \\ ', ' / ', ' * ', ' | ', ' ? ', ' : ', ' < ', ' > '",
							"Warning!", JOptionPane.OK_OPTION);
					return true;
				}
			}
		}
		return false;
	}

	public void filterAccounts(String name, String type) {
		final ArrayList<Account> list = new ArrayList<Account>();
		for (Account account : accounts) {
			if ((type.equalsIgnoreCase("all") ? true
					: account.getType().getName().equalsIgnoreCase(type))
					&& account.getName().toLowerCase().contains(name.toLowerCase())) {
				list.add(account);
			}
		}
		filteredAccounts = list;
	}

	public ArrayList<Account> getFilteredAccounts() {
		return filteredAccounts;
	}

	public boolean createAccount(final Account account) {
		if (!containsInvalidCharacters(account.getName()) && isNameAvaliable(account.getName())) {
			accounts.add(account);
			filteredAccounts = accounts;
			saveAccount(account);
			Logger.write("Created account: " + account.getName(), LogType.CLIENT);
			return true;
		}
		return false;
	}

	public boolean deleteAccount(final Account account) {
		if (account != null) {
			if (accounts.contains(account)) {
				deleteAccountFile(account);
				accounts.remove(account);
				filteredAccounts = accounts;
				Logger.write("Deleted Account: " + account.getName(), LogType.CLIENT);
				return true;
			}
		}
		return false;
	}

	private void deleteAccountFile(final Account account) {
		final File accountFile = new File(Cache.USER_FOLDER.getAbsoluteFile() + File.separator
				+ Engine.getInstance().getCurrentUser().getUsername() + File.separator + "accounts"
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
				JOptionPane.showMessageDialog(null,
						"This account name is already taken.",
						"Warning!", JOptionPane.OK_OPTION);
				return false;
			}
		}
		return true;
	}

	private ArrayList<Account> loadAccounts() {
		final ArrayList<Account> list = new ArrayList<Account>();
		final File userDirectory = new File(Cache.USER_FOLDER.getAbsoluteFile() + File.separator
				+ Engine.getInstance().getCurrentUser().getUsername() + File.separator
				+ "accounts");
		if (userDirectory.exists()) {
			for (final File file : userDirectory.listFiles()) {
				try (final FileInputStream inputStream = new FileInputStream(
						file.getAbsolutePath());
						final EncryptedObjectInputStream encryptedStream = new EncryptedObjectInputStream(
								inputStream,
								Engine.getInstance().getCurrentUser().getEncryptionKey(),
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
				+ Engine.getInstance().getCurrentUser().getUsername() + File.separator
				+ "accounts");
		if (userDirectory.exists()) {
			final File accountFile = new File(Cache.USER_FOLDER.getAbsoluteFile() + File.separator
					+ Engine.getInstance().getCurrentUser().getUsername() + File.separator
					+ "accounts"
					+ File.separator + account.getName() + ".acsafe");
			try (final FileOutputStream outputStream = new FileOutputStream(accountFile);
					final EncryptedObjectOutputStream encryptedStream = new EncryptedObjectOutputStream(
							outputStream, Engine.getInstance().getCurrentUser().getEncryptionKey(),
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
