package org.bautista.cybersafe.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.bautista.cybersafe.ui.components.panels.AccountScroller;
import org.bautista.cybersafe.ui.components.panels.CreateAccount;
import org.bautista.cybersafe.ui.components.panels.LoginScreen;
import org.bautista.cybersafe.ui.util.Scroller;

public class MainUI extends JFrame implements WindowListener {

	private LoginScreen loginScreen;
	private AccountScroller accountScroller;
	private CreateAccount createAccountScreen;

	public MainUI() {
		super("Cyber Safe");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setLocationRelativeTo(null);
		confirmOnClose();
		initiateLogin();
		pack();
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

	public void initiateLogin() {
		loginScreen = new LoginScreen();
		add(loginScreen);
	}

	public void openVault() {
		remove(loginScreen);
		accountScroller = new AccountScroller();
		add(new Scroller(accountScroller), BorderLayout.NORTH);
		refresh();
	}

	public void createUser() {
		remove(loginScreen);
		createAccountScreen = new CreateAccount();
		add(createAccountScreen);
		refresh();
	}

	private void clearFrame() {
		removeAll();
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

}
