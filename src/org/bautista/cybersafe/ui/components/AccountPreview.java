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
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.bautista.cybersafe.util.account.Account;

public class AccountPreview extends JComponent implements MouseListener {
	private final Account account;
	private final JLabel name, type;
	private final JTextArea description;
	private final GridBagConstraints constraints;
	private final ArrayList<ActionListener> listeners;
	private final String actionCommand;
	private final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 18);
	private final Font TYPE_FONT = new Font("Dialog", Font.ITALIC, 13);
	private final Font DESCRIPTION_FONT = new Font("Dialog", Font.PLAIN, 11);
	private final Border DESCRIPTION_BORDER = BorderFactory
			.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Description");
	private final Border COMPONENT_BORDER = BorderFactory.createLineBorder(Color.BLACK);

	public AccountPreview(final Account account) {
		super();
		this.account = account;
		actionCommand = account.getName();
		setBorder(COMPONENT_BORDER);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 5, 7, 5);

		listeners = new ArrayList<ActionListener>();

		name = new JLabel(account.getName());
		name.setFont(TITLE_FONT);
		type = new JLabel(account.getType().getName());
		type.setFont(TYPE_FONT);
		type.setHorizontalAlignment(SwingConstants.RIGHT);
		description = new JTextArea();
		description.setFont(DESCRIPTION_FONT);
		description.setLineWrap(true);
		description.setEditable(false);
		description.setOpaque(false);
		description.setFocusable(false);
		description.setWrapStyleWord(true);
		description.setBorder(DESCRIPTION_BORDER);
		description.setText(account.getDescription().length() > 0 ? account.getDescription()
				: "No description avaliable.");
		description.setPreferredSize(new Dimension(250, 45));

		setToolTipText(account.getName());
		enableInputMethods(true);
		addMouseListener(this);
		setLayout(new GridBagLayout());
		positionComponents();
	}

	public void addActionListener(final ActionListener listener) {
		listeners.add(listener);
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

	public Account getAccount() {
		return account;
	}

	public String getActionCommand() {
		return actionCommand;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getParent().getWidth(),
				(name.getHeight() + type.getHeight() + description.getHeight()));
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(final MouseEvent e) {
		notifyListeners(e);
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	private void notifyListeners(final MouseEvent e) {
		final ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand,
				e.getWhen(), e.getModifiers());
		synchronized (listeners) {
			for (int i = 0; i < listeners.size(); i++) {
				final ActionListener tmp = listeners.get(i);
				tmp.actionPerformed(evt);
			}
		}
	}

	private void positionComponents() {
		addComponent(0, 0, 0, 0, 1, name);
		constraints.anchor = GridBagConstraints.NORTHEAST;
		addComponent(1, 0, 0, 0, 1, type);
		addComponent(0, 1, 0, 0, 2, description);
	}

}
