package model;

public class Parameter {

	private String name;
	private String value;
	private int type;//0:只读；1：有选项；2：可更改
	private String note;//针对类型1的参数的选项
	
	public Parameter(String name, String value, int type1, String note) {
		super();
		this.name = name;
		this.value = value;
		this.type = type1;
		this.note = note;
	}
	
	
	public Parameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
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
