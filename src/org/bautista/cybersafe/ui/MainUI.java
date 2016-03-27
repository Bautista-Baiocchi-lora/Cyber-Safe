package org.bautista.cybersafe.ui;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.data.Variables;
import org.bautista.cybersafe.ui.components.panels.CreateUser;
import org.bautista.cybersafe.ui.components.panels.LoginScreen;
import org.bautista.cybersafe.ui.components.panels.safe.AccountFilterScreen;
import org.bautista.cybersafe.ui.components.panels.safe.AccountScroller;
import org.bautista.cybersafe.ui.components.panels.safe.CreateAccount;
import org.bautista.cybersafe.ui.util.Scroller;

public class MainUI extends JFrame implements WindowListener, ActionListener {

	private LoginScreen loginScreen;
	private AccountScroller accountScroller;
	private CreateUser createUserScreen;
	private CreateAccount createAccountScreen;
	private JMenuBar menu;
	private JMenu account, file, user;
	private JMenuItem info, logout, quit, createNewAccount;
	private AccountFilterScreen filterScreen;
	private final ArrayList<JComponent> currentView;

	public MainUI() {
		super("Cyber Safe");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setLocationRelativeTo(null);
		currentView = new ArrayList<JComponent>();
		confirmOnClose();
		addMenuBar();
		showLogin();
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

	private void enableUserMenu(boolean i) {
		info.setEnabled(i);
		logout.setEnabled(i);
		createNewAccount.setEnabled(i);
	}

	public void confirmOnClose() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(new JLabel("", JLabel.CENTER),
						"Are you sure you want to close Cyber Safe?");
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
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
		final Scroller scroller = new Scroller(accountScroller);
		add(scroller, BorderLayout.LINE_END);
		currentView.add(scroller);
		filterScreen = new AccountFilterScreen();
		add(filterScreen, BorderLayout.CENTER);
		currentView.add(filterScreen);
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

	public void showCreateAccount() {
		clearFrame();
		createAccountScreen = new CreateAccount();
		final Scroller scroller = new Scroller(createAccountScreen);
		currentView.add(scroller);
		add(scroller, BorderLayout.CENTER);
		refresh();
	}

	private void clearFrame() {
		if (currentView != null) {
			for (JComponent component : currentView) {
				remove(component);
			}
			currentView.clear();
		}
	}

	private void refresh() {
		revalidate();
		pack();
		repaint();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand().toLowerCase();
		JOptionPane pane = new JOptionPane();
		switch (command) {
			case "quit":
				System.exit(0);
				break;
			case "new account":
				Engine.getInstance().openCreateAccountScreen();
				break;
			case "user info":
				if (Variables.getCurrentUser() != null) {
					pane.showMessageDialog(null,
							"Username: " + Variables.getCurrentUser().getUsername() + "\nPassword: "
									+ Variables.getCurrentUser().getPassword()
									+ "\nEncryption Key: "
									+ Variables.getCurrentUser().getEncryptionKey(),
							"Information", JOptionPane.OK_OPTION);
				}
				break;
			case "log out":
				if (Variables.getCurrentUser() != null) {
					int reply = JOptionPane.showConfirmDialog(this,
							"Are you sure you want to log out?", "Warning!",
							JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						Engine.getInstance().logOut();
					}
				}
				break;
		}

	}

}