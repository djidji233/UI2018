package view;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.google.common.collect.Table;

import model.Attribute;
import model.Entity;
import model.Entry;
import model.TableModel;
import model.file.IUDBFile;
import model.file.SEKFile;

public class RelationTab extends JPanel {

	public RelationTab(Entity entity, List<Attribute> keys, List<Attribute> slogKeys, Entry slog) {

		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		int j = 0;

		setLayout(new BorderLayout());

		List<Attribute> atributi = entity.getAttributes();
		for (int i = 0; i < atributi.size(); i++) {
			if (j < keys.size() && atributi.get(i) == keys.get(j)) {
				a.add(slog.getFields().get(slogKeys.get(j)));
				j++;
			} else
				a.add("");
		}

		System.out.println("slogkeys.size " + slogKeys.size());
		System.out.println(a);

		if (entity.getType().equals("sek")) {
			String[][] data = ((SEKFile) entity).findAll(a);

			TableModel tm = new TableModel(entity, data);
			tm.setRowCount(data.length);

			JTable tabela = new JTable(tm);

			setBorder(new EmptyBorder(5, 5, 5, 5));
			add(new JScrollPane(tabela), BorderLayout.CENTER);
		}
		if (entity.getType().equals("sql")) {
			String[][] data;
			try {
				data = ((IUDBFile) entity).findAll(a,keys);

			TableModel tm = new TableModel(entity, data);
			tm.setRowCount(data.length);

			JTable tabela = new JTable(tm);

			setBorder(new EmptyBorder(5, 5, 5, 5));
			add(new JScrollPane(tabela), BorderLayout.CENTER);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

}
