package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.util.Scroller;
import org.bautista.cybersafe.util.account.Account;
import org.bautista.cybersafe.util.account.util.AccountField;

public class AccountViewerScreen extends JPanel implements ActionListener {
	private final JButton back, edit;
	private final Account account;
	private final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 16);
	private int nextRow = 1;

	public AccountViewerScreen(final Account account) {
		setLayout(new GridLayout(((account.getFields().size() * 2) + 2), 1, 10, 10));
		this.account = account;

		back = new JButton("Back");
		edit = new JButton("Edit");

		positionComponents();
		setListeners();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String command = e.getActionCommand();
		if (command.equalsIgnoreCase("edit")) {

		} else {
			Engine.getInstance().openSafeScreen();
		}
	}

	private void addComponent(final JComponent component) {
		add(component, new GridLayout(nextRow, 1));
		nextRow++;
	}

	private void positionComponents() {
		for (final AccountField field : account.getFields()) {
			final JLabel title = new JLabel(field.getTitle());
			title.setFont(TITLE_FONT);
			addComponent(title);
			if (field.getType().getType().equalsIgnoreCase("TextArea")) {
				final JTextArea dataArea = new JTextArea(field.getData());
				dataArea.setLineWrap(true);
				dataArea.setWrapStyleWord(true);
				dataArea.setOpaque(true);
				dataArea.setEditable(false);
				dataArea.setFocusable(false);
				addComponent(new Scroller(dataArea, new Dimension(280, 20)));
			} else {
				final JTextField dataField = new JTextField(field.getData());
				dataField.setEditable(false);
				dataField.setOpaque(true);
				dataField.setFocusable(false);
				addComponent(dataField);
			}
		}
		addComponent(edit);
		addComponent(back);
	}

	private void setListeners() {
		back.addActionListener(this);
		edit.addActionListener(this);
	}

}
