package me.Magnusri.DragonBusiness2.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
	double createCost;
	double disbandCost;
	double depositFee;
	double bonusAmount;
	boolean milestonesEnabled;
	double[] milestones;
	
	Plugin plugin;
	
	public Config(double createCost, double disbandCost, double depositFee, boolean milestonesEnabled, double[] milestones, double bonusAmount, Plugin plugin) {
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		
		super();
		this.createCost = createCost;
		this.disbandCost = disbandCost;
		this.depositFee = depositFee;
		this.milestonesEnabled = milestonesEnabled;
		this.milestones = milestones;
		this.bonusAmount = bonusAmount;
		this.plugin = plugin;
	}
	public Config(Plugin plugin) {
		
		super();
		this.plugin = plugin;
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		if (!plugin.getConfig().contains("configSet")){
			
			FileConfiguration config = plugin.getConfig();
			
			config.set("config.createCost", 1000);
			config.set("config.disbandCost", 200);
			config.set("config.depositFee", 0);
			config.set("config.milestonesEnabled", true);

			ArrayList<Double> milestonesList = new ArrayList<>();
			milestonesList.add((double) 5000);
			milestonesList.add((double) 10000);
			milestonesList.add((double) 20000);
			milestonesList.add((double) 50000);
			milestonesList.add((double) 80000);
			milestonesList.add((double) 100000);
			milestonesList.add((double) 200000);
			milestonesList.add((double) 500000);
			milestonesList.add((double) 1000000);
			milestonesList.add((double) 1500000);
			milestonesList.add((double) 2000000);
			milestonesList.add((double) 5000000);
			milestonesList.add((double) 10000000);
			
			config.set("config.milestones", milestonesList);
			config.set("config.bonusAmount", 20);
			plugin.saveConfig();
			
			this.createCost = config.getDouble("config.createCost");
			this.disbandCost = config.getDouble("config.disbandCost");
			this.depositFee = config.getDouble("config.depositFee");
			this.milestonesEnabled = config.getBoolean("config.milestonesEnabled");
			
			List<Double> list = config.getDoubleList("config.milestones");
			double[] array = new double[config.getDoubleList("config.milestones").size()];
			for(int i = 0; i < list.size(); i++) array[i] = list.get(i);
			this.milestones = array;
			
			this.bonusAmount = config.getDouble("config.bonusAmount");
		}
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