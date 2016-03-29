package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AccountFilterScreen extends JPanel implements ActionListener {

	public AccountFilterScreen() {
		setLayout(new BorderLayout());
		add(new JLabel("filter screen"), BorderLayout.CENTER);
		setPreferredSize(new Dimension(450, 350));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
