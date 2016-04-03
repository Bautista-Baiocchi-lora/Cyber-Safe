package org.bautista.cybersafe.ui.util;

import javax.swing.JPanel;

import org.bautista.cybersafe.core.Engine;

public class Panel extends JPanel {

	public void refresh() {
		revalidate();
		repaint();
		updateUI();
		Engine.getInstance().refreshUI();
	}

}
