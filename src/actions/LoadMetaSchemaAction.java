package actions;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import controller.MyController;
import model.Database;
import model.InfResources;
import model.NodeMeta;
import model.Workspace;
import view.MainFrame;

public class LoadMetaSchemaAction extends AbstractAction {

	public LoadMetaSchemaAction() {
		putValue(SMALL_ICON, MyController.getInstance().ikonica("open"));
		putValue(NAME, "Load the scheme");
		putValue(SHORT_DESCRIPTION, "Load the scheme");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		File f = MyController.getInstance().nadjiFajl();
		if (f == null) {
			return;
		}

		try {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			JSONTokener tokener = new JSONTokener(bReader);
			JSONObject obj = new JSONObject(tokener);
			bReader.close();

			JSONObject jScheme = new JSONObject(new JSONTokener(new FileInputStream(new File("res/meta_meta.json"))));
			Schema schema = SchemaLoader.load(jScheme);
			try {
				schema.validate(obj);
			} catch (ValidationException e1) {
				JOptionPane.showMessageDialog(null, "Cant validate scheme!", "Error", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Everit validaciju nije prosao");
				return;
			}

			Workspace root = (Workspace) MainFrame.getInstance().getNodeTree().getModel().getRoot();
			JSONArray objar = obj.getJSONObject("ws").getJSONArray("inf_resources");
			List<InfResources> resources = InfResources.loadBase(objar);
			// MainFrame.getInstance().getModel().setActiveDatabase(database);
			boolean check = MySchemeValidator.CheckMyScheme(resources);
			if (!check) {
				return;
			}
			for (int i = 0; i < objar.length(); i++) {
				NodeMeta node = new NodeMeta(resources.get(i));
				root.addChildren(node);
			}

			SwingUtilities.updateComponentTreeUI(MainFrame.getInstance());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Load file not found!", "Error", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("FNF");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Load IO error!", "Error", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("IO");
			e.printStackTrace();
		}
	}

}
