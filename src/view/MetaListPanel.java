package view;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class MetaListPanel extends JPanel {
	
	private JScrollPane scrollPanel;

	public JScrollPane getScrollPanel() {
		return scrollPanel;
	}

	public void setScrollPanel(JScrollPane scrollPanel) {
		this.scrollPanel = scrollPanel;
		this.add(scrollPanel, BorderLayout.CENTER);
	}

	public MetaListPanel(JButton btDel, JButton btAdd) {
		setLayout(new BorderLayout());
		
		scrollPanel = new JScrollPane();
		
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.X_AXIS));
		panelBottom.setBorder(new EmptyBorder(5,5,5,5));
		panelBottom.add(btDel);
		panelBottom.add(Box.createHorizontalStrut(5));
		panelBottom.add(btAdd);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(panelBottom, BorderLayout.SOUTH);
	}
	
	

}
