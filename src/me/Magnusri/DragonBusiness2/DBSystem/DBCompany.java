package me.Magnusri.DragonBusiness2.DBSystem;

import org.bukkit.plugin.Plugin;

public class DBCompany {
	int id;
	String name;
	double value;
	String info;
	boolean bankrupt;
	boolean hiring;
	
	Plugin plugin;
	DBHandler db;
	
	public DBCompany(int id, String name, double value2, String info, boolean bankrupt, boolean hiring, Plugin plugin, DBHandler db) {
		super();
		this.id = id;
		this.name = name;
		this.value = value2;
		this.info = info;
		this.bankrupt = bankrupt;
		this.hiring = hiring;
		this.plugin = plugin;
		this.db = db;
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

	public void setValue(double value) {
		this.value = value;
		db.setCompanyValue(plugin, getName(), value);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
		db.setCompanyInfo(plugin, getName(), info);
	}

	public boolean getBankrupt() {
		return bankrupt;
	}

	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
		db.setCompanyBankrupt(plugin, getName(), bankrupt);
	}

	public boolean isHiring() {
		return hiring;
	}

	public void setHiring(boolean hiring) {
		this.hiring = hiring;
		db.setCompanyHiring(plugin, getName(), hiring);
	}
}
