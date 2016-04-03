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
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.bautista.cybersafe.ui.components.panels.safe.AccountCreatorScreen;
import org.bautista.cybersafe.ui.components.panels.safe.AccountEditorScreen;
import org.bautista.cybersafe.ui.util.Scroller;
import org.bautista.cybersafe.util.account.util.FieldType;

public class InformationField extends JComponent implements ActionListener {
	private final JButton delete;
	private final JComponent field;
	private final JLabel titleLabel, fieldLabel;
	private final JTextField title;
	private final Border BORDER = BorderFactory
			.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Information Field");

	// blank field
	public InformationField(final JComponent field) {
		setLayout(new GridLayout(5, 1, 10, 5));
		setBorder(BORDER);

		titleLabel = new JLabel("Field Title", SwingConstants.LEADING);
		title = new JTextField();
		title.setPreferredSize(new Dimension(367, 20));
		fieldLabel = new JLabel("Field", SwingConstants.LEADING);
		this.field = field;
		delete = new JButton("Delete");
		delete.addActionListener(this);

		positionComponents();
	}

	// none deletable fields
	public InformationField(final String name, final JComponent field) {
		this(field);
		title.setText(name);
		title.setEditable(false);
		delete.setEnabled(false);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (getParent() instanceof AccountCreatorScreen) {
			AccountCreatorScreen.getInstance().removeComponent(this);
		} else {
			AccountEditorScreen.getInstance().removeComponent(this);
		}
	}

	public String getFieldData() {
		if (field instanceof JComboBox) {
			final JComboBox f = (JComboBox) field;
			return f.getSelectedItem().toString();
		} else if (field instanceof JTextField) {
			final JTextField f = (JTextField) field;
			return f.getText();
		} else if (field instanceof Scroller) {
			final Scroller scroller = (Scroller) field;
			final JTextArea f = (JTextArea) scroller.getComponent();
			return f.getText();
		}
		return "";
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

	private void positionComponents() {
		add(titleLabel, new GridLayout(1, 1));
		add(title, new GridLayout(2, 1));
		add(fieldLabel, new GridLayout(3, 1));
		add(field, new GridLayout(4, 1));
		add(delete, new GridLayout(5, 1));
	}

}
