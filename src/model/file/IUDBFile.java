package model.file;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import model.Attribute;
import model.Database;
import model.Entity;
import model.Entry;

public class IUDBFile extends AbstractFile {

	
	private List<Attribute> atributiZaIspis;
	
	public IUDBFile(String name, String tip, Database database, String path) {
		super(name, tip, database, path);
		atributiZaIspis = getAttributes();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean fetchBlock(long pointer) throws IOException, SQLException, ClassNotFoundException {
		// broj slogova u tabeli :
		Statement stmt0;
		stmt0 = getDatabase().getInfResources().getConn().createStatement();
		ResultSet rs0 = stmt0.executeQuery("SELECT COUNT(*) FROM " + getName());
		// ako sadrži rezultat ResulSet ce imati samo jedan red zbog toga
		// koristim if,a ne while.
		if (rs0.next()) {
			RECORD_NUM = rs0.getInt(1);
		}
		// obavezno zatvaranje ResulSet objekta
		rs0.close();
		// formiranje liste naziva kolona razdvojenih zarezom koja će se koristiti
		// u SELECT iskazu npr: OZNAKA_PLANA,SIFRA_PREDMETA,NAZIV_PREDMETA
		String columnParams = null;
		for (int i = 0; i < getAttributes().size(); i++) {
			if (columnParams == null) {
				columnParams = getAttributes().get(i).getName();
			} else {
				columnParams += "," + getAttributes().get(i).getName();
			}
		}

		// kreiranje Statement objekta korišćenjem konekcije iz klase AppCore
		Statement stmt = getDatabase().getInfResources().getConn().createStatement();
		// pošto nam treba rezultat izvršavanja pozivamo metodu executeQuery()
		ResultSet rs = stmt.executeQuery("SELECT " + columnParams + " FROM " + getName());
		data = new String[(int) RECORD_NUM][];
		System.out.println(getDatabase().getInfResources().getConn());
		// u objektu ResultSet-a rs nalaze se svi podaci iz date tabele
		// iz result set-a se podaci čitaju i prebacuju u matricu data[][]
		int i = 0;
		while (rs.next()) {
			data[i] = new String[getAttributes().size()];
			for (int j = 0; j < getAttributes().size(); j++) {
				data[i][j] = rs.getString(j + 1);
			}
			i++;
		}
		rs.close();
		// osvežavanje prikaza podataka iz tabele u klasi FileView
		atributiZaIspis = getAttributes();
		notifyObservers();
		return true;
		//JOptionPane.showMessageDialog(null, e.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public boolean fetchNextBlock() throws IOException, SQLException, ClassNotFoundException {
		// broj slogova u tabeli :
		Statement stmt0 = getDatabase().getInfResources().getConn().createStatement();
		ResultSet rs0 = stmt0.executeQuery("SELECT COUNT(*) FROM " + getName());
		// ako sadrži rezultat ResulSet ce imati samo jedan red zbog toga
		// koristim if,a ne while.
		if (rs0.next()) {
			RECORD_NUM = rs0.getInt(1);
		}
		
		System.out.println("record size " +RECORD_NUM);
		// obavezno zatvaranje ResulSet objekta
		rs0.close();
		// formiranje liste naziva kolona razdvojenih zarezom koja će se koristiti
		// u SELECT iskazu npr: OZNAKA_PLANA,SIFRA_PREDMETA,NAZIV_PREDMETA
		String columnParams = null;
		for (int i = 0; i < getAttributes().size(); i++) {
			if (columnParams == null) {
				columnParams = getAttributes().get(i).getName();
			} else {
				columnParams += "," + getAttributes().get(i).getName();
			}
		}

		// kreiranje Statement objekta korišćenjem konekcije iz klase AppCore
		Statement stmt = getDatabase().getInfResources().getConn().createStatement();
		// pošto nam treba rezultat izvršavanja pozivamo metodu executeQuery()
		ResultSet rs = stmt.executeQuery("SELECT " + columnParams + " FROM " + getName());
		data = new String[(int) RECORD_NUM][];
		System.out.println(getDatabase().getInfResources().getConn());
		// u objektu ResultSet-a rs nalaze se svi podaci iz date tabele
		// iz result set-a se podaci čitaju i prebacuju u matricu data[][]
		int i = 0;
		while (rs.next()) {
			data[i] = new String[getAttributes().size()];
			for (int j = 0; j < getAttributes().size(); j++) {
				data[i][j] = rs.getString(j + 1);
			}
			i++;
		}
		// osvežavanje prikaza podataka iz tabele u klasi FileView
		atributiZaIspis = getAttributes();
		notifyObservers();

		return true;
	}


	public boolean addRecord(ArrayList<String> attrs, ArrayList<String> record) throws IOException, ClassNotFoundException {
		String addSQL = " INSERT INTO " + getName() + "( ";
		for (int i = 0; i < getAttributes().size(); i++) {
			if (i == 0) {
				addSQL += " "+attrs.get(i);
			} else {
				addSQL += ", "+attrs.get(i);
			}
		}
		addSQL += ") VALUES (";
		for (int i = 0; i < getAttributes().size(); i++) {
			if (i == 0) {
				addSQL += " ? ";
			} else {
				addSQL += ", ?";
			}

		}
		addSQL += ")";

		try {
			PreparedStatement pstmt = getDatabase().getInfResources().getConn().prepareStatement(addSQL);
			System.out.println(pstmt.getParameterMetaData());
			int j = 1;

			for (int i = 0; i < getAttributes().size(); i++) {

				pstmt.setObject(j++, record.get(i));

			}
			System.out.println(pstmt.toString());
			pstmt.execute();
			
			Statement stmt = getDatabase().getInfResources().getConn().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT SCOPE_IDENTITY()");
			//System.out.println(rs.getInt(1));
			fetchNextBlock();
			return true;

		} catch (SQLException e) {
			errorMsg(e);
			e.printStackTrace();
			// TODO: handle exception

		}
		return false;
	}

	@Override
	public boolean updateRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findRecord(ArrayList<String> searchRec, int[] position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRecord(ArrayList<String> deleteRec) throws IOException, ClassNotFoundException {
		String deleteSQL = " DELETE FROM " + getName() + " WHERE ";
		for (int i = 0; i < getAttributes().size(); i++) {
			// u WHERE uslov ulaze samo polja iz primarnog kljuca
			if (getAttributes().get(i).isPrimaryKey()) {
				if (i == 0) {
					deleteSQL += getAttributes().get(i).getName() + " = ? ";
				} else {
					deleteSQL += " AND " + getAttributes().get(i).getName() + " = ?";
				}
			}

		}
		try {
			PreparedStatement pstmt = getDatabase().getInfResources().getConn().prepareStatement(deleteSQL);
			for (int i = 0; i < getAttributes().size(); i++) {
				// u WHERE uslov ulaze samo polja iz primarnog kljuca
				if (getAttributes().get(i).isPrimaryKey()) {
					pstmt.setObject(i + 1, deleteRec.get(i));
				}
			}

			pstmt.execute();
			fetchNextBlock();
			return true;
		} catch (SQLException e) {
			errorMsg(e);
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public void sortMDI() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sortMM() throws IOException {
		// TODO Auto-generated method stub

	}

	public String[][] findAll(ArrayList<String> searchRec, List<Attribute> keys) throws SQLException, ClassNotFoundException {

		int record = 0;
		int num = 0;
		while (searchRec.get(num).equals(""))
			num++;
		Statement stmt0 = getDatabase().getInfResources().getConn().createStatement();
		ResultSet rs0 = stmt0.executeQuery(
				"SELECT COUNT(*) FROM " + getName() + " WHERE " + keys.get(0).getName() + "=" + searchRec.get(num));
		if (rs0.next()) {
			record = rs0.getInt(1);
		}
		rs0.close();
		Statement stmt = getDatabase().getInfResources().getConn().createStatement();
		StringBuilder strb = new StringBuilder();
		strb.append("SELECT * FROM " + getName() + " WHERE " + keys.get(0).getName() + "=" + searchRec.get(num));
		if (keys.size() > 1 && searchRec.size() > 1) {
			for (int k = 0; k < keys.size(); k++) {
				strb.append(" AND WHERE" + keys.get(k).getName() + "=" + searchRec.get(k));
			}
		}
		ResultSet rs = stmt.executeQuery(strb.toString());
		String[][] newdata = new String[record][];
		int i = 0;
		while (rs.next()) {
			newdata[i] = new String[getAttributes().size()];
			for (int j = 0; j < getAttributes().size(); j++) {
				newdata[i][j] = rs.getString(j + 1);
			}
			i++;
		}
		rs.close();
		return newdata;
	}

	private boolean isRowEqual(String[] aData, ArrayList<String> searchRec) {
		boolean result = true;

		for (int col = 0; col < getAttributes().size(); col++) {
			if (!searchRec.get(col).trim().equals("")) {
				if (!aData[col].trim().equals(searchRec.get(col).trim())) {
					result = false;
					return result;
				}
			}
		}

		return result;
	}

	public boolean ﬁndFilterRecord(String statement, List<String> lines,List<Attribute> attZaIsp) throws ClassNotFoundException {
		atributiZaIspis = attZaIsp;
		return ﬁndFilterRecord1(statement, lines);
		
	}
	public boolean ﬁndFilterRecord(String statement, List<String> lines) throws ClassNotFoundException {
		atributiZaIspis = getAttributes();
		return ﬁndFilterRecord1(statement, lines);
	}
	private boolean ﬁndFilterRecord1(String statement, List<String> lines) throws ClassNotFoundException {
		PreparedStatement prepStatement;
		//String[][] newdata = new String[(int) RECORD_NUM][];

		
		//data = new String[(int) RECORD_NUM][];
		try {
			prepStatement = getDatabase().getInfResources().getConn().prepareStatement(statement);

			System.out.println(lines.size());
			
			for (int i = 0; i < lines.size(); i++) {
				prepStatement.setObject(i+1, lines.get(i));
			}
			ResultSet rs = prepStatement.executeQuery();

			ArrayList<String[]> newdata = new ArrayList<>();
			
			
			while (rs.next()) {
				String[] datarow = new String[atributiZaIspis.size()];
				for (int j = 0; j < atributiZaIspis.size(); j++) {
					datarow[j] = rs.getString(j + 1);
					//System.out.println(String.format("%d %d %s", i,j,rs.getString(j + 1)));
				}
				newdata.add(datarow);
			}
			RECORD_NUM = newdata.size();
			data = new String[(int) RECORD_NUM][];
			System.out.println("Rec Size "+newdata.size());
			for(int i=0;i<newdata.size();i++) {
				data[i]=newdata.get(i);
			}
			//prepStatement.close();
			rs.close();
		} catch (SQLException e1) {
			errorMsg(e1);
			e1.printStackTrace();
			return false;
		}
		
		notifyObservers();
		return true;
	}

	public List<Attribute> getAtributiZaIspis() {
		return atributiZaIspis;
	}

	public void setAtributiZaIspis(List<Attribute> atributiZaIspis) {
		this.atributiZaIspis = atributiZaIspis;
	}

	@Override
	public boolean addRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	public void errorMsg(Exception e) {
		 setLast_exeption(e);
		 notifyObservers();
	}
	
	Entry lastEdded;

	public Entry getLastEdded() {
		return lastEdded;
	}

	public void setLastAdded(Entry slog) {
		lastEdded = slog;
		
	}
}
