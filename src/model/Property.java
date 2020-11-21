package model;

import java.io.Serializable;

public class Property implements Serializable{

	private String name;
	private String value;
	private int type;
	private String note;
	
	public Property(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	
	
	public Property(String name, String value, int type, String note) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
		this.note = note;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
