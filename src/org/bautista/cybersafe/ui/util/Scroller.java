package org.bautista.cybersafe.ui.util;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Scroller extends JScrollPane {
	private JComponent component;

	public Scroller(final JComponent component) {
		super(component, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.component = component;
		setAutoscrolls(true);
	}

	public Scroller(final JComponent component, Dimension size) {
		super(component, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.component = component;
		setPreferredSize(size);
		setAutoscrolls(true);
	}

	public JComponent getComponent() {
		return component;
	}

}
