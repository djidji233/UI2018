package model.file;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public interface IFile {

	public boolean fetchBlock(long pointer) throws IOException, SQLException, ClassNotFoundException;
	public boolean fetchNextBlock() throws IOException, SQLException, ClassNotFoundException;
	public boolean addRecord(ArrayList<String> record) throws IOException;
	public boolean updateRecord(ArrayList<String> record)throws IOException;
	public boolean findRecord(ArrayList<String> searchRec,int[] position);
	public boolean deleteRecord(ArrayList<String> searchRec) throws IOException, ClassNotFoundException;
	public void sortMDI()throws IOException;
	public void sortMM()throws IOException;
}
