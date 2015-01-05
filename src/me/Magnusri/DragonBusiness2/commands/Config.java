package me.Magnusri.DragonBusiness2.commands;

public class Config {
	double createCost;
	double disbandCost;
	double depositFee;
	
	public Config(double createCost, double disbandCost, double depositFee) {
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		
		super();
		this.createCost = createCost;
		this.disbandCost = disbandCost;
		this.depositFee = depositFee;
	}
	public Config() {
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		
		super();
		this.createCost = 1000;
		this.disbandCost = 200;
		this.depositFee = 0;
	}

	public double getCreateCost() {
		return createCost;
	}

	public void setCreateCost(float createCost) {
		this.createCost = createCost;
	}

	public double getDepositFee() {
		return depositFee;
	}

	public void setDepositFee(float depositFee) {
		this.depositFee = depositFee;
	}

	public double getDisbandCost() {
		return disbandCost;
	}

	public void setDisbandCost(float disbandCost) {
		this.disbandCost = disbandCost;
	}
	
}