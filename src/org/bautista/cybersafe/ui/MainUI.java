package org.bautista.cybersafe.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.data.Variables;
import org.bautista.cybersafe.ui.components.panels.CreateUser;
import org.bautista.cybersafe.ui.components.panels.LoginScreen;
import org.bautista.cybersafe.ui.components.panels.safe.AccountFilterScreen;
import org.bautista.cybersafe.ui.components.panels.safe.AccountScroller;
import org.bautista.cybersafe.ui.components.panels.safe.AccountViewerScreen;
import org.bautista.cybersafe.ui.components.panels.safe.CreateAccountScreen;
import org.bautista.cybersafe.ui.util.Scroller;
import org.bautista.cybersafe.util.account.Account;

public class MainUI extends JFrame implements WindowListener, ActionListener {

	private LoginScreen loginScreen;
	private AccountScroller accountScroller;
	private CreateUser createUserScreen;
	private CreateAccountScreen createAccountScreen;
	private JMenuBar menu;
	private JMenu account, file, user;
	private JMenuItem info, logout, quit, createNewAccount;
	private AccountFilterScreen filterScreen;
	private AccountViewerScreen viewAccountScreen;
	private final ArrayList<JComponent> currentView;

	public MainUI() {
		super("Cyber Safe");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setLocationRelativeTo(null);
		currentView = new ArrayList<JComponent>();
		confirmOnClose();
		addMenuBar();
		showLogin();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String command = e.getActionCommand().toLowerCase();
		final JOptionPane pane = new JOptionPane();
		switch (command) {
			case "quit":
				final int result = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to close Cyber Safe?", "Warning!",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				break;
			case "new account":
				Engine.getInstance().openCreateAccountScreen();
				break;
			case "user info":
				if (Variables.getCurrentUser() != null) {
					JOptionPane.showMessageDialog(null,
							"Username: " + Variables.getCurrentUser().getUsername() + "\nPassword: "
									+ Variables.getCurrentUser().getPassword()
									+ "\nRecovery Question: "
									+ Variables.getCurrentUser().getRecoveryQuestion()
									+ "\nRecovery Question Answer: "
									+ Variables.getCurrentUser().getRecoveryAnswer()
									+ "\nEncryption Key: "
									+ Variables.getCurrentUser().getEncryptionKey(),
							"Information", JOptionPane.OK_OPTION);
				}
				break;
			case "log out":
				if (Variables.getCurrentUser() != null) {
					final int reply = JOptionPane.showConfirmDialog(this,
							"Are you sure you want to log out?", "Warning!",
							JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						Engine.getInstance().logOut();
					}
				}
				break;
		}

	}

	private void addMenuBar() {
		menu = new JMenuBar();

		account = new JMenu("Account");
		file = new JMenu("File");
		user = new JMenu("User");

		file.add(account);
		file.add(user);

		info = new JMenuItem("Information", 'i');
		info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK));
		info.setActionCommand("user info");
		info.addActionListener(this);
		user.add(info);
		logout = new JMenuItem("Log out", 'o');
		logout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		logout.setActionCommand("log out");
		logout.addActionListener(this);
		user.add(logout);
		file.addSeparator();
		quit = new JMenuItem("Quit", 'e');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		file.add(quit);

		createNewAccount = new JMenuItem("Create new...", 'n');
		createNewAccount.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		createNewAccount.setActionCommand("new account");
		createNewAccount.addActionListener(this);
		account.add(createNewAccount);

		menu.add(file);

		setJMenuBar(menu);
	}

	private void clearFrame() {
		if (currentView != null) {
			for (final JComponent component : currentView) {
				remove(component);
			}
			currentView.clear();
		}
	}

	public void confirmOnClose() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				final int result = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to close Cyber Safe?", "Warning!",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	private void enableUserMenu(final boolean i) {
		info.setEnabled(i);
		logout.setEnabled(i);
		createNewAccount.setEnabled(i);
	}

	public void refresh() {
		revalidate();
		pack();
		repaint();
	}

	public void setFrameTitle(final String title) {
		setTitle(title);
	}

	public void showAccount(final Account account) {
		clearFrame();
		viewAccountScreen = new AccountViewerScreen(account);
		final Scroller scroller = new Scroller(viewAccountScreen, new Dimension(400, 300));
		currentView.add(scroller);
		add(scroller, BorderLayout.CENTER);
		refresh();
	}

	public void showCreateAccount() {
		clearFrame();
		createAccountScreen = new CreateAccountScreen();
		final Scroller scroller = new Scroller(createAccountScreen, new Dimension(400, 500));
		currentView.add(scroller);
		add(scroller, BorderLayout.CENTER);
		refresh();
	}

	public void showCreateUser() {
		clearFrame();
		enableUserMenu(false);
		createUserScreen = new CreateUser();
		currentView.add(createUserScreen);
		add(createUserScreen);
		refresh();
	}

	public void showLogin() {
		clearFrame();
		enableUserMenu(false);
		loginScreen = new LoginScreen();
		currentView.add(loginScreen);
		add(loginScreen);
		refresh();
	}

	public void showSafe() {
		clearFrame();
		enableUserMenu(true);
		accountScroller = new AccountScroller();
		final Scroller scroller = new Scroller(accountScroller, new Dimension(250, 100));
		add(scroller, BorderLayout.LINE_END);
		currentView.add(scroller);
		filterScreen = new AccountFilterScreen();
		add(filterScreen, BorderLayout.CENTER);
		currentView.add(filterScreen);
		refresh();
	}

	@Override
	public void windowActivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
