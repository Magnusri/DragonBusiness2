package me.Magnusri.DragonBusiness2.commands;

public class Config {
	double createCost;
	double disbandCost;
	double depositFee;
	double bonusAmount;
	boolean milestonesEnabled;
	double[] milestones;
	
	public Config(double createCost, double disbandCost, double depositFee, boolean milestonesEnabled, double[] milestones, double bonusAmount) {
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		
		super();
		this.createCost = createCost;
		this.disbandCost = disbandCost;
		this.depositFee = depositFee;
		this.milestonesEnabled = milestonesEnabled;
		this.milestones = milestones;
		this.bonusAmount = bonusAmount;
	}
	public Config() {
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		
		super();
		this.createCost = 1000;
		this.disbandCost = 200;
		this.depositFee = 0;
		this.milestonesEnabled = true;
		this.milestones = new double[]{
				5000,
				10000,
				20000,
				50000,
				80000,
				100000,
				200000,
				500000,
				1000000,
				1500000,
				2000000,
				5000000,
				10000000,
			};
		this.bonusAmount = 20;
	}

	public double getBonusAmount() {
		return bonusAmount;
	}
	
	public void setBonusAmount(double bonusAmount) {
		this.bonusAmount = bonusAmount;
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
	public boolean isMilestonesEnabled() {
		return milestonesEnabled;
	}
	public double[] getMilestones() {
		return milestones;
	}
	
}