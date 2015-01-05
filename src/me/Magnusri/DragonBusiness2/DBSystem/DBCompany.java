package me.Magnusri.DragonBusiness2.DBSystem;

public class DBCompany {
	int id;
	String name;
	double value;
	String info;
	
	public DBCompany(int id, String name, double value2, String info) {
		super();
		this.id = id;
		this.name = name;
		this.value = value2;
		this.info = info;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
