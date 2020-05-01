package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MyAbout extends JFrame {

	public MyAbout() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		setTitle("About");
		setSize(600, 400);
		setLocationRelativeTo(null);

		this.setLayout(new BorderLayout());

		JLabel slikaT = new JLabel(new ImageIcon("images/aleksa.png"));
		JLabel slikaZ = new JLabel(new ImageIcon("images/djordjez.jpg"));
		JLabel slikaV = new JLabel(new ImageIcon("images/djordjev.jpg"));
		JPanel jPanleImages = new JPanel();
		jPanleImages.setLayout(new GridLayout(0, 3));
		jPanleImages.add(slikaT);
		jPanleImages.add(slikaZ);
		jPanleImages.add(slikaV);
		
		JLabel imeAT = new JLabel("Aleksa Trajkovic",SwingConstants.CENTER);
		JLabel imeDZ = new JLabel("Djordje Zivanovic",SwingConstants.CENTER);
		JLabel imeDV = new JLabel("Djordje Vasiljevic",SwingConstants.CENTER);
		JLabel indeksAT = new JLabel("RN 72/2018",SwingConstants.CENTER);
		JLabel indeksDZ = new JLabel("RN 98/2018",SwingConstants.CENTER);
		JLabel indeksDV = new JLabel("RN 91/2018",SwingConstants.CENTER);
		JPanel jPanelImena = new JPanel();
		jPanelImena.setLayout(new GridLayout(2, 3));
		jPanelImena.add(imeAT);
		jPanelImena.add(imeDZ);
		jPanelImena.add(imeDV);
		jPanelImena.add(indeksAT);
		jPanelImena.add(indeksDZ);
		jPanelImena.add(indeksDV);

		JPanel jPanelLink = new JPanel();
		JLabel labelLink = new JLabel("Our Redmine Wiki.");
		labelLink.setForeground(Color.BLUE.darker());
		labelLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		labelLink.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop()
							.browse(new URI("https://student.ftn.uns.ac.rs/redmine/projects/tim_201_5_ui2018/wiki"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});


		jPanelLink.add(labelLink);

		this.add(jPanleImages, BorderLayout.NORTH);
		this.add(jPanelImena, BorderLayout.CENTER);
		this.add(jPanelLink, BorderLayout.SOUTH);
		pack();
	}
}
