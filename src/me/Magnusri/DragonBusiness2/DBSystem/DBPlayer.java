package me.Magnusri.DragonBusiness2.DBSystem;

import org.bukkit.plugin.Plugin;

public class DBPlayer {
	int id;
	String uuid;
	String name;
	String rank;
	int companyid;
	String pendingInvite;
	double level;
	Double earned;
	
	DBHandler db;
	Plugin plugin;
	
	public DBPlayer(Plugin plugin, DBHandler db, int id, String uuid, String name, String rank,int companyid, String pendingInvite, Double earned, double level) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.rank = rank;
		this.companyid = companyid;
		this.pendingInvite = pendingInvite;
		this.level = level;
		this.earned = earned;
		this.db = db;
		this.plugin = plugin;
	}

	public String toString(){
		return name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
		db.setPlayerRank(plugin, plugin.getServer().getPlayer(getUuid()), rank);
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public String getPendingInvite() {
		return pendingInvite;
	}

	public void setPendingInvite(String pendingInvite) {
		this.pendingInvite = pendingInvite;
	}

	public Double getEarned() {
		return earned;
	}

	public void setEarned(Double earned) {
		this.earned = earned;
	}

	public double getLevel() {
		return level;
	}

	public void setLevel(double level) {
		this.level = level;
	}
	
	
	
	
}
