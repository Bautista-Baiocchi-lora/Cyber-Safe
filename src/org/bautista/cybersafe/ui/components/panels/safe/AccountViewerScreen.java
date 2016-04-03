package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.InformationField;
import org.bautista.cybersafe.ui.util.Panel;
import org.bautista.cybersafe.ui.util.Scroller;
import org.bautista.cybersafe.util.account.Account;

public class AccountViewerScreen extends Panel implements ActionListener {
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
			Engine.getInstance().openAccountEditor(account);
		} else {
			Engine.getInstance().openSafeScreen();
		}
	}

	private void addComponent(final JComponent component) {
		add(component, new GridLayout(nextRow, 1));
		nextRow++;
	}

	private void positionComponents() {
		addComponent(edit);
		addComponent(back);
		for (final InformationField field : account.getFields()) {
			final JLabel title = new JLabel(field.getFieldTitle());
			title.setFont(TITLE_FONT);
			addComponent(title);
			if (field.getFieldType().getType().equalsIgnoreCase("TextArea")) {
				final JTextArea dataArea = new JTextArea(field.getFieldData());
				dataArea.setLineWrap(true);
				dataArea.setWrapStyleWord(true);
				dataArea.setOpaque(true);
				dataArea.setEditable(false);
				dataArea.setFocusable(false);
				addComponent(new Scroller(dataArea, new Dimension(280, 40)));
			} else {
				final JTextField dataField = new JTextField(field.getFieldData());
				dataField.setEditable(false);
				dataField.setOpaque(true);
				dataField.setFocusable(false);
				addComponent(dataField);
			}
		}
	}

	private void setListeners() {
		back.addActionListener(this);
		edit.addActionListener(this);
	}

}
