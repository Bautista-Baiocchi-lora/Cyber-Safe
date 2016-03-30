package org.bautista.cybersafe.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.bautista.cybersafe.core.Engine;
import org.bautista.cybersafe.ui.components.panels.safe.CreateAccountScreen;
import org.bautista.cybersafe.ui.util.Scroller;
import org.bautista.cybersafe.util.account.util.FieldType;

public class InformationField extends JComponent implements ActionListener {
	private final JButton delete;
	private final JComponent field;
	private final JLabel titleLabel, fieldLabel;
	private final JTextField title;
	private final Border BORDER = BorderFactory
			.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Information Field");

	public InformationField(JComponent field) {
		setLayout(new GridLayout(5, 1, 10, 5));
		setBorder(BORDER);

		titleLabel = new JLabel("Field Title", JLabel.LEADING);
		title = new JTextField();
		title.setPreferredSize(new Dimension(367, 20));
		fieldLabel = new JLabel("Field", JLabel.LEADING);
		this.field = field;
		delete = new JButton("Delete");
		delete.addActionListener(this);

		positionComponents();
	}

	public InformationField(String name, JComponent field) {
		this(field);
		this.title.setText(name);
		this.title.setEditable(false);
		this.delete.setEnabled(false);
	}

	public String getFieldTitle() {
		return title.getText();
	}

	public FieldType getFieldType() {
		if (field instanceof JComboBox) {
			return FieldType.COMBO_BOX;
		} else if (field instanceof JTextField) {
			return FieldType.TEXT_FIELD;
		} else if (field instanceof Scroller) {
			return FieldType.TEXT_AREA;
		}
		return null;
	}

	public String getFieldData() {
		if (field instanceof JComboBox) {
			JComboBox f = (JComboBox) field;
			return f.getSelectedItem().toString();
		} else if (field instanceof JTextField) {
			JTextField f = (JTextField) field;
			return f.getText();
		} else if (field instanceof Scroller) {
			Scroller scroller = (Scroller) field;
			JTextArea f = (JTextArea) scroller.getComponent();
			return f.getText();
		}
		return "";
	}

	private void positionComponents() {
		add(titleLabel, new GridLayout(1, 1));
		add(title, new GridLayout(2, 1));
		add(fieldLabel, new GridLayout(3, 1));
		add(field, new GridLayout(4, 1));
		add(delete, new GridLayout(5, 1));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CreateAccountScreen.getInstance().removeComponent(this);
	}

}
