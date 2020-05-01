package controller;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import actions.ActionManager;
import model.MainFrameModel;
import view.MainFrame;

public class MyController {

	private static MyController instance = null;

	private MyController() {
	}

	// private myModel model;
	private MainFrame mainFrame;

	public static MyController getInstance() {
		if (instance == null) {
			instance = new MyController();
		}
		return instance;
	}

	public ImageIcon ikonica(String name) {
		String path = MainFrameModel.getImagePath(name);
		ImageIcon org = new ImageIcon(path);
		Image img = org.getImage().getScaledInstance(-1, 25, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
	
	public ImageIcon malaIkonica(String name) {
		String path = MainFrameModel.getImagePath(name);
		ImageIcon org = new ImageIcon(path);
		Image img = org.getImage().getScaledInstance(-1, 20, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	public File nadjiFajl() {
		JFileChooser chooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON fajlovi", "json");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(workingDirectory);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			return chooser.getSelectedFile();
		}
		
		return null;

	}

}
