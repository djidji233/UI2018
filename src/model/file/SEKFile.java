package model.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import model.Attribute;
import model.AttributeType;
import model.Database;
import model.Entity;
import model.IzmenaItem;
import model.Observer;
import model.tree.KeyElement;
import model.tree.Node;
import model.tree.NodeElement;
import model.tree.Tree;


public class SEKFile extends AbstractFile {

	public SEKFile(String name, String tip, Database database, String path) {
		super(name, tip, database, path);
		// TODO Auto-generated constructor stub
	}

	

	public boolean fetchBlock(long pointer) throws IOException {
		FILE_POINTER = pointer;
		System.out.println(this.getType());
		String putanja = path + "/" + name + ".stxt";
		RandomAccessFile afile = new RandomAccessFile(putanja, "r");

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	public String[][] findAll(ArrayList<String> searchRec) {


		List<String[]> a = new ArrayList<>();

		FILE_POINTER = 0;

		String putanja = path + "/" + name + ".stxt";
		RandomAccessFile afile;

		try {
			afile = new RandomAccessFile(putanja, "r");
			FILE_SIZE = afile.length();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(FILE_SIZE);

		while (FILE_POINTER < FILE_SIZE) {
			try {
				fetchNextBlock();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			// System.out.println("DATAL:"+data.length);
			for (int row = 0; row < data.length; row++) {

				// for(int i=0;i<data[0].length;i++)
				// System.out.printf("%s ",data[row][i]);
				// System.out.println();

				if (isRowEqual(data[row], searchRec)) {

					a.add(new String[data[0].length]);

					for (int i = 0; i < data[0].length; i++) {
						a.get(a.size() - 1)[i] = new String(data[row][i]);

						System.out.print(data[row][i] + " ");
					}
					System.out.println();
				}

			}
		}

		String[][] res = new String[a.size()][getAttributes().size()];

		for (int i = 0; i < a.size(); i++)
			for (int j = 0; j < getAttributes().size(); j++) {
				res[i][j] = new String(a.get(i)[j]);
			}
		return res;

	}

	public boolean findNext(ArrayList<String> searchRec, int[] position) {
		boolean result = false;

		while (FILE_POINTER < FILE_SIZE && position[0] == -1) {
			try {
				fetchNextBlock();
			} catch (IOException e) {
				e.printStackTrace();
				position[0] = -1;
				return false;
			}

			for (int row = 0; row < data.length; row++) {

				if (isRowEqual(data[row], searchRec)) {
					position[0] = row;
					return true;
				} else if (isRowGreater(data[row], searchRec)) {
					position[0] = row;

					return false;
				}

			}
		}
		return result;

	}

	public ArrayList<String> findOne(ArrayList<String> searchRec, int pointerFlag) {
		ArrayList<String> found = new ArrayList<>();

		long pointer = FILE_POINTER;

		if (pointerFlag == 0)
			FILE_POINTER = 0;

		while (FILE_POINTER < FILE_SIZE) {
			try {
				fetchNextBlock();
			} catch (IOException e) {
				System.out.println("Can't fetch next SEK block (find)");
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
					if (found.size() == BLOCK_SIZE)
						return found;
				}
			}
		}

		FILE_POINTER = pointer;
		return found;
	}

	public String[][] findMultiple(ArrayList<String> searchRec, int pointerFlag) {


		List<String[]> a = new ArrayList<>();

		long pointer = FILE_POINTER;

		if (pointerFlag == 0)
			FILE_POINTER = 0;

		String putanja = path + "/" + name + ".stxt";
		RandomAccessFile afile;

		try {
			afile = new RandomAccessFile(putanja, "r");
			FILE_SIZE = afile.length();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(FILE_SIZE);

		while (FILE_POINTER < FILE_SIZE) {
			try {
				fetchNextBlock();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			for (int row = 0; row < data.length; row++) {

				if (isRowEqual(data[row], searchRec)) {

					a.add(new String[data[0].length]);

					for (int i = 0; i < data[0].length; i++) {
						a.get(a.size() - 1)[i] = new String(data[row][i]);

						System.out.print(data[row][i] + " ");
					}
					System.out.println();
				}

			}
		}

		String[][] res = new String[a.size()][getAttributes().size()];

		for (int i = 0; i < a.size(); i++)
			for (int j = 0; j < getAttributes().size(); j++) {
				res[i][j] = new String(a.get(i)[j]);
			}
		FILE_POINTER = pointer;
		return res;
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

	public boolean findRecord(ArrayList<String> searchRec, int[] position) {
		FILE_POINTER = 0;
		return findNext(searchRec, position);

	}

	private boolean isRowEqual(String[] aData, ArrayList<String> searchRec) {
		boolean result = true;

		for (int col = 0; col < getAttributes().size(); col++) {

			// System.out.println(aData[col].trim()+"="+searchRec.get(col).trim() );
			if (!searchRec.get(col).trim().equals("")) {
				if (!aData[col].trim().equals(searchRec.get(col).trim())) {
					result = false;
					return result;
				}
			}
			// System.out.println("================");
		}

		return result;
	}

	private boolean isRowGreater(String[] aData, ArrayList<String> searchRec) {

		boolean result = true;
		int noPK = 0;
		boolean prev = true;

		for (int i = 0; i < getAttributes().size(); i++) {

			if (!searchRec.get(i).trim().equals("") && !getAttributes().get(i).isPrimaryKey()) {
				return false;
			}
			if (getAttributes().get(i).isPrimaryKey())
				noPK++;

		}

		for (int col = 0; col < getAttributes().size(); col++) {

			if (!searchRec.get(col).trim().equals("")) {

				if (aData[col].trim().compareToIgnoreCase(searchRec.get(col).trim()) > 0 && col < noPK - 1) {
					return true;

				} else if (aData[col].trim().compareToIgnoreCase(searchRec.get(col).trim()) != 0 && col < noPK - 1) {
					result = false;
					prev = false;
				} else if (aData[col].trim().compareToIgnoreCase(searchRec.get(col).trim()) <= 0) {
					result = false;

				} else if (aData[col].trim().compareToIgnoreCase(searchRec.get(col).trim()) > 0 && prev
						&& col == (noPK - 1)) {
					result = true;
				}
			}
		}

		return result;
	}

	public void sinhronizuj() {

	
		String izmene_putanja = path + "/" + getName() + ".todo";
		String tmp_putanja = path + "/" + getName() + ".tmp";
		String data_pitanja = path + "/" + getName() + ".stxt";
		String error_putanja = path + "/" + getName() + ".error";
		
		sortIzmenaFile(izmene_putanja);

		RandomAccessFile izmeneFile;
		FileWriter tmpFile;
		RandomAccessFile dataFile;
		FileWriter errorFile;

		try {

			izmeneFile = new RandomAccessFile(izmene_putanja, "r");
			tmpFile = new FileWriter(tmp_putanja, false);
			dataFile = new RandomAccessFile(data_pitanja, "r");
			errorFile = new FileWriter(error_putanja, false);

			FILE_SIZE = dataFile.length();
			long izmenaSize = izmeneFile.length();
			int izmenaRecordSize = RECORD_SIZE + 1;

			RECORD_NUM = (long) Math.ceil((FILE_SIZE * 1.0000) / (RECORD_SIZE * 1.0000));
			long izmenaRecordNumber = (long) Math.ceil((izmenaSize * 1.0000) / (izmenaRecordSize * 1.0000));

			if (izmenaRecordNumber < 1 || RECORD_NUM < 1) {

				izmeneFile.close();
				tmpFile.close();
				dataFile.close();
				errorFile.close();

				return;
			}

			int i = 0, j = 0;

			byte[] buffer1 = new byte[RECORD_SIZE];
			dataFile.read(buffer1);

			byte[] buffer2 = new byte[izmenaRecordSize];
			izmeneFile.read(buffer2);

			int pk_size = 0;

			List<Attribute> atributi = getAttributes();
			for (int t = 0; t < atributi.size(); t++) {
				if (atributi.get(i).isPrimaryKey())
					pk_size += atributi.get(i).getLength();

			}
			
			int br_add =0;
			int br_change =0;
			int br_delete =0;
			int br_error =0;
			while (i < RECORD_NUM && j < izmenaRecordNumber) {
				String line1 = (new String(buffer1)).substring(0, RECORD_SIZE - 2);
				String line2 = (new String(buffer2)).substring(0, RECORD_SIZE - 2);

				System.out.println(line1);
				System.out.println(line2);

				// String key1 = line1.substring(0,pk_size);
				// String key2 = line2.substring(0,pk_size);

				int mode = (new String(buffer2)).charAt(RECORD_SIZE - 2) - '0';

				int cmp = compereRows(this, line1, line2);
				if (cmp == 0) {
					if (mode == IzmenaItem.BRISANJE) {
						i++;
						j++;
						br_delete++;
						dataFile.read(buffer1);
						izmeneFile.read(buffer2);
					} else if (mode == IzmenaItem.IZMENA) {
						br_change++;
						tmpFile.write(line2 + "\r\n");
						izmeneFile.read(buffer2);
						j++;
					}

					else if (mode == IzmenaItem.DODAVANJE) {
						br_error++;
						errorFile.write(line2 + mode + "\r\n");
						izmeneFile.read(buffer2);
						j++;
					}
				}
				else if (cmp > 0) {
					if (mode == IzmenaItem.DODAVANJE) {
						br_add++;
						tmpFile.write(line2 + "\r\n");
						izmeneFile.read(buffer2);
						j++;
					}

					else {
						br_error++;
						errorFile.write(line2 + mode + "\r\n");
						izmeneFile.read(buffer2);
						j++;
					}

				} else {
					tmpFile.write(line1 + "\r\n");
					dataFile.read(buffer1);
					i++;
				}

			}
			
			for (int i1 = i; i1 < RECORD_NUM; i1++) {

				String line1 = (new String(buffer1)).substring(0, RECORD_SIZE - 2);

				tmpFile.write(line1 + "\r\n");
				dataFile.read(buffer1);
			}
			for (int i1 = j; i1 < izmenaRecordNumber; i1++) {
				
				
				br_error++;
				String line2 = (new String(buffer2)).substring(0, RECORD_SIZE - 2);

				int mode = (new String(buffer2)).charAt(RECORD_SIZE - 2) - '0';
				errorFile.write(line2 + mode + "\r\n");
				izmeneFile.read(buffer2);
			}

			izmeneFile.close();
			tmpFile.close();
			dataFile.close();
			errorFile.close();

			Files.deleteIfExists(Paths.get(izmene_putanja));
			
			 changeFile();
			 
			 Files.deleteIfExists(Paths.get(tmp_putanja)); 
			 
			 fetchBlock(0);
			 			 
			  notifyObservers();
			 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean deleteRecord(ArrayList<String> searchRec) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void dodajIzmenu(IzmenaItem izmena) {
		
			try {
				String putanja = path + "/" + name + ".todo";
				FileWriter izmeneFile = new FileWriter(putanja, true);
				izmeneFile.write(izmena.toString());

				System.out.println(izmena.toString());
				izmeneFile.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void sortIzmenaFile(String todo_putanja) {
		Scanner sc;
		try {
			
			File f =new File(todo_putanja);
			sc = new Scanner(f);

			List<IzmenaItem> izmene = new ArrayList<>();

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				izmene.add(new IzmenaItem(this, line));
			}
			
			Collections.sort(izmene);
			sc.close();
			
			FileWriter izmeneFile = new FileWriter(todo_putanja, false);
			for(IzmenaItem izmena:izmene)
			{
				
				izmeneFile.write(izmena.toString());
				System.out.println(izmena.toString());
			}
			
			izmeneFile.close();
			
		
			
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public String binarySearch(String key) throws IOException {
		long low = 0;
		long high = RECORD_NUM;
		byte[] buff = new byte[BUFFER_SIZE];
		while (low < high) {
			// System.out.println("high: " + high + " low: " + low);
			long mid = ((high + low) / 2) * RECORD_SIZE;
			// System.out.println("mid: " + mid);
			String putanja = path + "/" + name + ".stxt";
			RandomAccessFile afile = new RandomAccessFile(putanja, "r");
			afile.seek(mid);
			afile.read(buff);
			String contentS = new String(buff);
			for (int x = contentS.length(); x < buffer.length; x++)
				contentS = contentS + " ";

			String str = contentS.substring(0, RECORD_SIZE);
			// System.out.println(str);
			int compare = compareKey(key, str);
			afile.close();
			if (compare == 0) {
				return str;
			}
			if (compare < 0) {
				high = ((high + low) / 2);
			} else if (compare > 0) {
				low = ((high + low) / 2) + 1;
			}

		}
		return null;
	}

	private int compareKey(String key, String line) {
		int n = 0;
		for (Attribute a : getAttributes()) {
			if (a.isPrimaryKey())
				n++;
		}
		int k = 0;
		for (int i = 0; i < n; i++) {
			String field = null;
			String field2 = null;
			field = key.substring(k, k + getAttributes().get(i).getLength());
			field2 = line.substring(k, k + getAttributes().get(i).getLength());
			int j = stringCompare(field.trim(), field2.trim(), getAttributes().get(i).getType());
			if (j > 0)
				return j;
			if (j < 0)
				return j;
			k = k + getAttributes().get(i).getLength();
		}
		return 0;
	}

	private static int stringCompare(String str1, String str2, AttributeType type) {
		if (!(AttributeType.TYPE_NUMERIC.equals(type))) {
			int l1 = str1.length();
			int l2 = str2.length();
			int lmin = Math.min(l1, l2);
			for (int i = 0; i < lmin; i++) {
				int str1_ch = (int) str1.charAt(i);
				int str2_ch = (int) str2.charAt(i);

				if (str1_ch != str2_ch) {
					return str1_ch - str2_ch;
				}
			}
			if (l1 != l2) {
				return l1 - l2;
			} else {
				return 0;
			}
		} else {
			int a = Integer.parseInt(str1);
			int b = Integer.parseInt(str2);
			return a - b;
		}
	}

	static int compereRows(Entity e, String row1, String row2) {

		List<Attribute> atributi = e.getAttributes();

		int k = 0;

		for (int i = 0; i < atributi.size(); i++) {

			if (!atributi.get(i).isPrimaryKey())
				return 0;

			int len = atributi.get(i).getLength();
			String s1 = row1.substring(k, k + len).trim();
			String s2 = row2.substring(k, k + len).trim();

			if (!s1.equalsIgnoreCase(s2))
				if (atributi.get(i).getType().equals(AttributeType.TYPE_NUMERIC)) {
					return Integer.parseInt(s1) - Integer.parseInt(s2);
				} else
					return s1.compareTo(s2);

			k += len;

		}

		return 0;
	}

	private String returnType(AttributeType at) {
		if (at.equals(AttributeType.TYPE_CHAR))
			return "TYPE_CHAR";
		if (at.equals(AttributeType.TYPE_DATETIME))
			return "TYPE_DATETIME";
		if (at.equals(AttributeType.TYPE_NUMERIC))
			return "TYPE_NUMERIC";
		else
			return "TYPE_VARCHAR";
	}

	public void makeTree() throws IOException {
		long pointer = FILE_POINTER;
		FILE_POINTER = 0;
		List<Node> listNodes = new ArrayList<Node>();
		long tFilePointer = 0;
		while (FILE_POINTER < FILE_SIZE) {
			tFilePointer = FILE_POINTER;
			fetchNextBlock();
			List<KeyElement> listKeyElements = new ArrayList<>();
			List<NodeElement> listNodeElements = new ArrayList<>();
			for (int i = 0; i < getAttributes().size(); i++) {
				if (getAttributes().get(i).isPrimaryKey()) {
					KeyElement keyElement = new KeyElement(returnType(getAttributes().get(i).getType()),
							data[0][i]);
					listKeyElements.add(keyElement);
				}
			}
			NodeElement nodeElement = new NodeElement((int) (tFilePointer / RECORD_SIZE), listKeyElements);
			listNodeElements.add(nodeElement);
			Node node = new Node(listNodeElements);
			tFilePointer = FILE_POINTER;
			fetchNextBlock();
			listKeyElements = new ArrayList<KeyElement>();
			for (int i = 0; i < getAttributes().size(); i++) {
				if (getAttributes().get(i).isPrimaryKey()) {
					KeyElement keyElement = new KeyElement(returnType(getAttributes().get(i).getType()),
							data[0][i]);
					listKeyElements.add(keyElement);
				}
			}
			nodeElement = new NodeElement((int) (tFilePointer / RECORD_SIZE), listKeyElements);
			listNodeElements.add(nodeElement);
			node = new Node(listNodeElements);
			listNodes.add(node);
		}
		FILE_POINTER = pointer;
		int size = listNodes.size();
		if (listNodes.size() % 2 != 0)
			size++;
		int k = 2;
		int l = 0;
		while (size / k != 0) {
			if (listNodes.size() % 2 != 0)
				l = 1;

			for (int i = 0; i < (size / k) + l; i++) {
				if (i + 1 >= listNodes.size())
					continue;
				Node node1 = listNodes.get(i);
				Node node2 = listNodes.get(i + 1);
				NodeElement isne1 = node1.getData().get(0).clone();
				NodeElement isne2 = node2.getData().get(0).clone();
				List<NodeElement> listNodeElements = new ArrayList<>();
				listNodeElements.add(isne1);
				listNodeElements.add(isne2);
				Node node3 = new Node(listNodeElements);
				listNodes.remove(node1);
				listNodes.remove(node2);
				node3.addChildren(node1);
				node3.addChildren(node2);
				listNodes.add(i, node3);
			}
			k = k * 2;
		}

		Node root = new Node();
		Tree tree = new Tree();
		size = listNodes.size();
		k = 2;
		while (size / k != 0 && size != 2) {
			for (int i = 0; i < (size / k); i++) {
				Node node1 = listNodes.get(i);
				if (i + 1 >= listNodes.size())
					continue;
				Node node2 = listNodes.get(i + 1);
				NodeElement isne1 = node1.getData().get(0).clone();
				NodeElement isne2 = node2.getData().get(0).clone();
				List<NodeElement> listNodeElements = new ArrayList<>();
				listNodeElements.add(isne1);
				listNodeElements.add(isne2);
				Node node3 = new Node(listNodeElements);
				listNodes.remove(node1);
				listNodes.remove(node2);
				node3.addChildren(node1);
				node3.addChildren(node2);
				listNodes.add(i, node3);
			}
			k = k * 2;
		}

		if (listNodes.size() == 3) {
			Node node1 = listNodes.get(0);
			Node node2 = listNodes.get(1);
			Node node3 = listNodes.get(2);
			NodeElement isne1 = node1.getData().get(0).clone();
			NodeElement isne2 = node2.getData().get(0).clone();
			NodeElement isne3 = node3.getData().get(0).clone();
			List<NodeElement> listNodeElements = new ArrayList<>();
			listNodeElements.add(isne2);
			listNodeElements.add(isne3);
			Node node4 = new Node(listNodeElements);
			node4.addChildren(node2);
			node4.addChildren(node3);
			listNodeElements = new ArrayList<>();
			NodeElement isne4 = node4.getData().get(0).clone();
			listNodeElements.add(isne1);
			listNodeElements.add(isne4);
			root = new Node(listNodeElements);
			root.addChildren(node1);
			root.addChildren(node4);
		} else if (listNodes.size() == 2) {
			Node node1 = listNodes.get(0);
			Node node2 = listNodes.get(1);
			NodeElement isne1 = node1.getData().get(0).clone();
			NodeElement isne2 = node2.getData().get(0).clone();
			List<NodeElement> listNodeElements = new ArrayList<>();
			listNodeElements.add(isne1);
			listNodeElements.add(isne2);
			root = new Node(listNodeElements);
			root.addChildren(node1);
			root.addChildren(node2);
		} else if (listNodes.size() == 1) {
			root = listNodes.get(0);
		}
		tree.setRootElement(root);
		ObjectOutputStream os;
		os = new ObjectOutputStream(new FileOutputStream(path + File.separator + name + "1" + ".tree"));
		os.writeObject(tree);
		os.close();
	}
	
	public void changeFile() {
		
		String tmp_putanja = path + "/" + getName() + ".tmp";
		String data_pitanja = path + "/" + getName() + ".stxt";
		
	      File source = new File(tmp_putanja);
	      File dest = new File(data_pitanja);
	      
		
	      try {
			Files.copy(source.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
