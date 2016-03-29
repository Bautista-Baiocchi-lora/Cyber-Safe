package org.bautista.cybersafe.util.account.util;

import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public enum FieldType implements Serializable {

	TEXT_AREA(new JTextArea(), "TextArea"), TEXT_FIELD(new JTextField(),
			"TextField"), PASSWORD_FIELD(new JPasswordField(),
					"PasswordField"), COMBO_BOX(new JComboBox(), "ComboBox");

	private JComponent component;
	private String type;

	FieldType(JComponent component, String type) {
		this.component = component;
		this.type = type;
	}

	public JComponent getComponent() {
		return component;
	}

	public String getType() {
		return type;
	}

}
