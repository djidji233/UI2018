package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

	List<Attribute> atributi;

	private int rowCount = 7;

	private String[] columns;
	private String[][] data;
	
	public TableModel(Entity entity, String[][] data) {
		
		this(entity.getAttributes(),data);
	}
	
	public TableModel(List<Attribute> attributes, String[][] data) {
		super();
		// rowCount=block_size;
		atributi=attributes;
		columns = new String[atributi.size()];
		this.data = data;
		
		for (int i = 0; i < atributi.size(); i++) {

			columns[i] = atributi.get(i).getName();
		}
		rowCount = data.length;
		setDataVector(data, atributi.toArray());
		fireTableDataChanged();
	
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowCount;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return data[arg0][arg1];
	}

	@Override
	public String getColumnName(int columnIndex) {
		return (String) columns[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO
		return true;
	}
//	public void setRowCount(int x) {
//		rowCount = x;
//	} // ne postoji u abstract table model

	public void setValueAt(String aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = aValue;
	}

	public String[] getRowValue(int i) {
		return data[i];
	}

}
