package org.bautista.cybersafe.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import org.bautista.cybersafe.util.account.Account;

public class AccountPreview extends JComponent implements MouseListener {
	private final Account account;
	private final JLabel title, type;
	private final JTextArea description;
	private final GridBagConstraints constraints;
	private final ArrayList<ActionListener> listeners;
	private final String actionCommand;
	private final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 20);
	private final Font TYPE_FONT = new Font("Dialog", Font.ITALIC, 14);
	private final Font DESCRIPTION_FONT = new Font("Dialog", Font.PLAIN, 12);
	private final Border DESCRIPTION_BORDER = BorderFactory
			.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Description");

	public AccountPreview(Account account) {
		super();
		this.account = account;
		this.actionCommand = account.getName();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = constraints.NORTHEAST;
		constraints.weightx = 1;
		constraints.weighty = 1;
		listeners = new ArrayList<ActionListener>();

		title = new JLabel(account.getName());
		title.setFont(TITLE_FONT);
		type = new JLabel(account.getType().getName());
		type.setFont(TYPE_FONT);
		type.setHorizontalAlignment(JLabel.RIGHT);
		description = new JTextArea();
		description.setFont(DESCRIPTION_FONT);
		description.setLineWrap(true);
		description.setEditable(false);
		description.setOpaque(false);
		description.setFocusable(false);
		description.setWrapStyleWord(true);
		description.setBorder(DESCRIPTION_BORDER);
		description.setText(
				"This is a description.");

		setToolTipText(account.getName());
		enableInputMethods(true);
		addMouseListener(this);
		setLayout(new GridBagLayout());
		positionComponents();
	}

	private void addComponent(final int x, final int y, final int yPad,
			final int xPad, final int width,
			final JComponent comp) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.ipadx = xPad;
		constraints.ipady = yPad;
		constraints.gridwidth = width;
		add(comp, constraints);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getParent().getWidth(), 50);
	}

	private void positionComponents() {
		addComponent(0, 0, 0, 0, 1, title);
		constraints.anchor = constraints.NORTHEAST;
		addComponent(1, 0, 0, 0, 1, type);
		addComponent(0, 1, 0, 0, 2, description);
	}

	public void mousePressed(MouseEvent e) {
		notifyListeners(e);
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners(MouseEvent e) {
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand,
				e.getWhen(), e.getModifiers());
		synchronized (listeners) {
			for (int i = 0; i < listeners.size(); i++) {
				ActionListener tmp = listeners.get(i);
				tmp.actionPerformed(evt);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
