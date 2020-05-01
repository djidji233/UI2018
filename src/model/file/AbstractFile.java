package model.file;

import java.io.IOException;
import java.util.ArrayList;

import model.Attribute;
import model.Database;
import model.Entity;

public abstract class AbstractFile extends Entity implements IFile {
	protected String path;
	protected int BLOCK_SIZE = 100;
	protected int BUFFER_SIZE = 0;
	protected int RECORD_SIZE = 0;
	protected long BLOCK_NUM = 0;
	protected long RECORD_NUM = 0;
	protected long FILE_POINTER = 0;
	protected long FILE_SIZE = 0;
	protected byte[] buffer;
	protected String[][] data = null;

	public AbstractFile(String name, String tip, Database database, String path) {
		super(name,tip,database);
		this.path = path;
	}

	public void updateRECORD_SIZE() {
		for (Attribute a : getAttributes()) {
			RECORD_SIZE += a.getLength();
		}
		if (getType().equals("ser"))
			RECORD_SIZE += 3;
		else
			RECORD_SIZE += 2;
	}
	
	public void setBLOCK_SIZE(int bLOCK_SIZE) {
		BLOCK_SIZE = bLOCK_SIZE;
	}

	public String[][] getData() {
		return data;
	}

	public int getBLOCK_SIZE() {
		return BLOCK_SIZE;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getBUFFER_SIZE() {
		return BUFFER_SIZE;
	}

	public void setBUFFER_SIZE(int bUFFER_SIZE) {
		BUFFER_SIZE = bUFFER_SIZE;
	}

	public int getRECORD_SIZE() {
		return RECORD_SIZE;
	}

	public void setRECORD_SIZE(int rECORD_SIZE) {
		RECORD_SIZE = rECORD_SIZE;
	}

	public long getBLOCK_NUM() {
		return BLOCK_NUM;
	}

	public void setBLOCK_NUM(long bLOCK_NUM) {
		BLOCK_NUM = bLOCK_NUM;
	}

	public long getRECORD_NUM() {
		return RECORD_NUM;
	}

	public void setRECORD_NUM(long rECORD_NUM) {
		RECORD_NUM = rECORD_NUM;
	}

	public long getFILE_POINTER() {
		return FILE_POINTER;
	}

	public void setFILE_POINTER(long fILE_POINTER) {
		FILE_POINTER = fILE_POINTER;
	}

	public long getFILE_SIZE() {
		return FILE_SIZE;
	}

	public void setFILE_SIZE(long fILE_SIZE) {
		FILE_SIZE = fILE_SIZE;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
}
