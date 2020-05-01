package model.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import model.Attribute;
import model.Database;

public class SERFile extends AbstractFile {

	public SERFile(String name, String tip, Database database, String path) {
		super(name, tip, database, path);
		// TODO Auto-generated constructor stub
	}

	private char[] isDeleted;



	public boolean fetchBlock(long pointer) throws IOException {
		FILE_POINTER = pointer;
		System.out.println(this.getType());
		RandomAccessFile afile = new RandomAccessFile(path + "/" + name + ".txt", "r");
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
			data[i] = new String[getAttributes().size()];
			int k = 0;
			for (int j = 0; j < getAttributes().size(); j++) {
				String field = null;
				field = line.substring(k, k + getAttributes().get(j).getLength());
				if (line.charAt(line.length() - 3) == '0') {
					data[i][j] = field;
				} else
					data[i][j] = "";
				k = k + getAttributes().get(j).getLength();
			}

		}
		FILE_POINTER = afile.getFilePointer();
		afile.close();

		return true;
	}

	public boolean refreshBlock() throws IOException {
		return fetchBlock(FILE_POINTER - (BLOCK_SIZE * RECORD_SIZE));
	}

	public boolean fetchNextBlockRecycle() throws IOException {
		return fetchBlockRecycle(FILE_POINTER);
	}

	public boolean fetchBlockRecycle(long pointer) throws IOException {
		FILE_POINTER = pointer;

		RandomAccessFile afile = new RandomAccessFile(path + "/" + name + ".txt", "r");
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

		isDeleted = new char[BLOCK_SIZE];

		for (int i = 0; i < BUFFER_SIZE / RECORD_SIZE; i++) {

			String line = contentS.substring(i * RECORD_SIZE, i * RECORD_SIZE + RECORD_SIZE);
			isDeleted[i] = line.charAt(line.length() - 3);
			data[i] = new String[getAttributes().size()];
			int k = 0;
			for (int j = 0; j < getAttributes().size(); j++) {
				String field = null;
				field = line.substring(k, k + getAttributes().get(j).getLength());
				data[i][j] = field;

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
		String newr = "\r\n";
		for (int i = 0; i < record.size(); i++) {
			newr = newr + record.get(i);
		}
		newr = newr + "0";
		RandomAccessFile raf = new RandomAccessFile(path + "/" + name + ".txt", "rw");
		raf.seek(raf.length());
		raf.writeBytes(newr);
		raf.close();
		System.out.println(newr);
		return true;
	}

	@Override
	public boolean updateRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findRecord(ArrayList<String> searchRec, int[] position) {
		FILE_POINTER = 0;
		while (FILE_POINTER < FILE_SIZE) {
			try {
				fetchNextBlock();
			} catch (IOException e) {
				System.out.println("Can't fetch next SER block (find)");
				e.printStackTrace();
			}
			int j = 0;
			for (int i = 0; i < data.length; i++) {
				if (isRowEqual(data[i], searchRec) && data[i][RECORD_SIZE - 2].equals("0")) {
					position[j] = i;
					j++;
				}
			}
		}
		if (position.length > 0)
			return true;
		return false;
	}

	private boolean isRowEqual(String[] data, ArrayList<String> searchRec) {

		int n = 0;
		for (Attribute a : getAttributes()) {
			if (a.isPrimaryKey())
				n++;
		}

		for (int i = 0; i < n; i++) {
			if ((searchRec.get(i).equals(null)) || !(data[i].equalsIgnoreCase(searchRec.get(i))))
				return false;
		}

		return true;
	}

	@Override
	public boolean deleteRecord(ArrayList<String> searchRec) {
		long pointer = FILE_POINTER;
		FILE_POINTER = 0;
		while (FILE_POINTER < FILE_SIZE) {
			try {
				fetchNextBlock();
			} catch (IOException e) {
				System.out.println("Can't fetch next SER block (delete)");
				e.printStackTrace();
			}

			for (int i = 0; i < data.length; i++) {
				if (isRowEqual(data[i], searchRec)) {
					long position = i * RECORD_SIZE + FILE_POINTER - BLOCK_SIZE * RECORD_SIZE + RECORD_SIZE - 3;
					RandomAccessFile raf;
					try {
						raf = new RandomAccessFile(path + "/" + name + ".txt", "rw");
						raf.seek(position);
						raf.writeBytes("1");
						raf.close();
						FILE_POINTER = pointer;
						return true;
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		FILE_POINTER = pointer;
		return false;
	}

	public ArrayList<String> recycleBin() {
		ArrayList<String> deleted = new ArrayList<>();
		long pointer = FILE_POINTER;
		FILE_POINTER = 0;
		while (FILE_POINTER < FILE_SIZE) {
			try {
				fetchNextBlockRecycle();
			} catch (IOException e) {
				System.out.println("Can't fetch next SER block (find)");
				e.printStackTrace();
			}
			String line;
			for (int i = 0; i < isDeleted.length; i++) {
				if (isDeleted[i] == '1') {
					line = new String();
					line = "";
					for (int j = 0; j < data[i].length; j++) {
						line = line + data[i][j];
					}
					deleted.add(line);
				}
			}
		}
		FILE_POINTER = pointer;
		return deleted;
	}

	public boolean returnFromRecycleBin(ArrayList<String> searchRec) {
		long pointer = FILE_POINTER;
		FILE_POINTER = 0;
		while (FILE_POINTER < FILE_SIZE) {
			try {
				fetchNextBlockRecycle();
			} catch (IOException e) {
				System.out.println("Can't fetch next SER block (find)");
				e.printStackTrace();
			}
			int j = 0;
			for (int i = 0; i < data.length; i++) {
				if (isRowEqual(data[i], searchRec) && isDeleted[i] == '1') {
					long position = i * RECORD_SIZE + FILE_POINTER - BLOCK_SIZE * RECORD_SIZE + RECORD_SIZE - 3;
					RandomAccessFile raf;
					try {
						raf = new RandomAccessFile(path + "/" + name + ".txt", "rw");
						raf.seek(position);
						raf.writeBytes("0");
						raf.close();
						return true;
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		FILE_POINTER = pointer;
		return false;
	}

	public ArrayList<String> findSer(ArrayList<String> searchRec) {
		ArrayList<String> found = new ArrayList<>();
		long pointer = FILE_POINTER;
		FILE_POINTER = 0;
		while (FILE_POINTER < FILE_SIZE) {
			try {
				fetchNextBlock();
			} catch (IOException e) {
				System.out.println("Can't fetch next SER block (find)");
				e.printStackTrace();
			}
			for (int i = 0; i < data.length; i++) {
				if (findEqual(data[i], searchRec)) {
					String line = new String();
					line = "";
					for (int j = 0; j < data[i].length; j++) {
						line = line + data[i][j];
					}
					found.add(line);
					if (found.size() == BLOCK_SIZE) {
						FILE_POINTER = pointer;
						return found;
					}
				}
			}
		}
		FILE_POINTER = pointer;
		return found;
	}

	public boolean findEqual(String[] data, ArrayList<String> searchRec) {
		int k = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i].replaceAll(" ", "").equalsIgnoreCase(searchRec.get(i).replaceAll(" ", ""))
					|| searchRec.get(i).equals(""))
				k++;
		}
		if (k == data.length)
			return true;
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
