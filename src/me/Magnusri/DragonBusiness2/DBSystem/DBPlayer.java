package me.Magnusri.DragonBusiness2.DBSystem;

public class DBPlayer {
	int id;
	String uuid;
	String name;
	String rank;
	int companyid;
	Double earned;
	
	public DBPlayer(int id, String uuid, String name, String rank,int companyid, Double earned) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.rank = rank;
		this.companyid = companyid;
		this.earned = earned;
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
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public Double getEarned() {
		return earned;
	}

	public void setEarned(Double earned) {
		this.earned = earned;
	}
	
	
}
