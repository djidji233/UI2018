package model.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import model.AttributeType;
import model.Database;
import model.Entity;
import model.tree.Node;
import model.tree.NodeElement;
import model.tree.Tree;
import model.tree.KeyElement;

public class INDFile extends AbstractFile {



	public INDFile(String name, String tip, Database database, String path) {
		super(name, tip, database, path);
		// TODO Auto-generated constructor stub
	}

	public boolean fetchBlock(long pointer) throws IOException {
		FILE_POINTER = pointer;
		System.out.println(this.getType());
		System.out.println(path + "/" + name);

		RandomAccessFile afile = new RandomAccessFile(new File(path + "/" + name + ".stxt"), "r");
		FILE_SIZE = afile.length();
		RECORD_NUM = (long) Math.ceil((FILE_SIZE * 1.0000) / (RECORD_SIZE * 1.0000));
		BLOCK_NUM = (int) (RECORD_NUM / BLOCK_SIZE) + 1;

		if (FILE_POINTER / RECORD_SIZE + BLOCK_SIZE > RECORD_NUM)
			BUFFER_SIZE = (int) (RECORD_NUM - FILE_POINTER / RECORD_SIZE) * RECORD_SIZE;
		else
			BUFFER_SIZE = (int) (RECORD_SIZE * BLOCK_SIZE);

		buffer = new byte[BUFFER_SIZE];
		data = new String[BUFFER_SIZE / RECORD_SIZE][];

		afile.seek(FILE_POINTER);
		afile.read(buffer);
		String contentS = new String(buffer);

		for (int x = contentS.length(); x < buffer.length; x++)
			contentS = contentS + " ";

		for (int i = 0; i < BUFFER_SIZE / RECORD_SIZE; i++) {
			
			String line = contentS.substring(i * RECORD_SIZE, i * RECORD_SIZE + RECORD_SIZE);
			System.out.println(contentS); // debugg
			data[i] = new String[getAttributes().size()];
			int k = 0;
			for (int j = 0; j < getAttributes().size(); j++) {
				String field = null;
				field = line.substring(k, k + getAttributes().get(j).getLength());
				data[i][j] = field;
				System.out.println(field); // debugg
				k = k + getAttributes().get(j).getLength();
			}

		}

		FILE_POINTER = afile.getFilePointer();
		afile.close();

		return true;
	}

	@Override
	public boolean fetchNextBlock() throws IOException {
		return fetchBlock(FILE_POINTER);
	}

	@Override
	public boolean addRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
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
	public boolean deleteRecord(ArrayList<String> searchRec) {
		// TODO Auto-generated method stub
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

}
