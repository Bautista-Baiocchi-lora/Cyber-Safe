package org.bautista.cybersafe.ui.components.panels.safe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.InformationField;
import org.bautista.cybersafe.ui.util.Scroller;

public class InformationFieldCreator extends JPanel implements ActionListener {
	private final JComboBox fieldType;
	private final JLabel fieldTypeLabel;
	private final JButton add;
	private final JTextArea textArea;
	private final JTextField textField;
	private final String[] FIELD_TYPES = { "Normal Text Field",
			"Large Text Field" };
	private final Border BORDER = BorderFactory
			.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Field Creator");

	public InformationFieldCreator() {
		super();
		setLayout(new GridLayout(3, 1, 10, 5));

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(367, 20));
		fieldTypeLabel = new JLabel("Field Type", SwingConstants.LEADING);
		fieldType = new JComboBox(FIELD_TYPES);
		add = new JButton("Add Blank Field");
		add.setActionCommand("add");
		add.addActionListener(this);

		setBorder(BORDER);
		positionComponents();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		e.getActionCommand().toLowerCase();
		switch (fieldType.getSelectedItem().toString().toLowerCase()) {
			case "normal text field":
				CreateAccountScreen.getInstance().addComponent(
						new InformationField(textField));
				break;
			case "large text field":
				CreateAccountScreen.getInstance()
						.addComponent(new InformationField(
								new Scroller(textArea, new Dimension(367, 20))));
				break;
		}
		revalidate();
		repaint();
		updateUI();
		Engine.getInstance().refreshUI();
	}

	private void positionComponents() {
		add(fieldTypeLabel, new GridLayout(1, 1));
		add(fieldType, new GridLayout(2, 1));
		add(add, new GridLayout(3, 1));
	}

}
