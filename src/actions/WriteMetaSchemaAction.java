package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.MyController;
import model.Database;
import model.Entity;
import model.InfResources;
import model.NodeMeta;
import view.MainFrame;

public class WriteMetaSchemaAction extends AbstractAction {

	public WriteMetaSchemaAction() {
		putValue(SMALL_ICON, MyController.getInstance().ikonica("save"));
		putValue(NAME, "Write selected scheme");
		putValue(SHORT_DESCRIPTION, "Write selected scheme");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object object = MainFrame.getInstance().getNodeTree().getSelectedObject();
		if (object instanceof NodeMeta) {
			NodeMeta node = (NodeMeta) object;
			if (node.getData() instanceof InfResources) {
				InfResources ir = (InfResources) node.getData();
				File file = new File("res/writeFile.json");
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				PrintWriter pw;
				try {
					pw = new PrintWriter(file);
					JSONObject jobj = new JSONObject();
					JSONObject obj = ir.writeMetaFile(ir);
					jobj.put("ws", obj);
					jobj.write(pw);
					pw.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "You have to select an information resoursce to write.", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
