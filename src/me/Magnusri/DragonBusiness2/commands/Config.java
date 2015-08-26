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
	boolean sellingInvEnabled;
	double[] milestones;
	String[] pricelist;
	
	Plugin plugin;
	FileConfiguration config;
	
	public Config(double createCost, double disbandCost, double depositFee, boolean milestonesEnabled, boolean sellingInvEnabled, double[] milestones, String[] pricelist, double bonusAmount, Plugin plugin) {
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		
		super();
		this.createCost = createCost;
		this.disbandCost = disbandCost;
		this.depositFee = depositFee;
		this.milestonesEnabled = milestonesEnabled;
		this.sellingInvEnabled = sellingInvEnabled;
		this.milestones = milestones;
		this.pricelist = pricelist;
		this.bonusAmount = bonusAmount;
		this.plugin = plugin;
	}
	public Config(Plugin plugin) {
		
		super();
		this.plugin = plugin;
		
		this.config = plugin.getConfig();
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		if (!plugin.getConfig().contains("configSet")){
			
			config.set("config.createCost", 1000);
			config.set("config.disbandCost", 200);
			config.set("config.depositFee", 0);
			config.set("config.milestonesEnabled", true);
			config.set("config.sellingInvEnabled", true);
			config.set("config.bonusAmount", 20);

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
			
			ArrayList<String> priceList = new ArrayList<>();
			priceList.add("4-0.5");
			priceList.add("5-0.6");
			config.set("config.pricelist", priceList);
			
			plugin.saveConfig();
		}
		
		//READ OUT THE CONFIG
		this.createCost = config.getDouble("config.createCost");
		this.disbandCost = config.getDouble("config.disbandCost");
		this.depositFee = config.getDouble("config.depositFee");
		this.milestonesEnabled = config.getBoolean("config.milestonesEnabled");
		this.sellingInvEnabled = config.getBoolean("config.sellingInvEnabled");
		
		List<Double> list = config.getDoubleList("config.milestones");
		double[] array = new double[config.getDoubleList("config.milestones").size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i);
		this.milestones = array;
		
		List<String> list1 = config.getStringList("config.pricelist");
		String[] array1 = new String[config.getStringList("config.pricelist").size()];
		for(int i = 0; i < list1.size(); i++) array1[i] = list1.get(i);
		this.pricelist = array1;
		
		this.bonusAmount = config.getDouble("config.bonusAmount");
	}

	public double getBonusAmount() {
		return bonusAmount;
	}
	
	public void setBonusAmount(double bonusAmount) {
		this.bonusAmount = bonusAmount;
		config.set("config.bonusAmount", bonusAmount);
	}
	
	public double getCreateCost() {
		return createCost;
	}

	public void setCreateCost(float createCost) {
		this.createCost = createCost;
		config.set("config.createCost", createCost);
	}

	public double getDepositFee() {
		return depositFee;
	}

	public void setDepositFee(float depositFee) {
		this.depositFee = depositFee;
		config.set("config.depositFee", depositFee);
	}

	public double getDisbandCost() {
		return disbandCost;
	}

	public void setDisbandCost(float disbandCost) {
		this.disbandCost = disbandCost;
		config.set("config.disbandCost", disbandCost);
	}
	public boolean isMilestonesEnabled() {
		return milestonesEnabled;
	}
	public void setMilestonesEnabled(boolean milestonesEnabled) {
		this.milestonesEnabled = milestonesEnabled;
		config.set("config.milestonesEnabled", milestonesEnabled);
	}
	public double[] getMilestones() {
		return milestones;
	}
	public boolean isSellingInvEnabled() {
		return sellingInvEnabled;
	}
	public void setSellingInvEnabled(boolean sellingInvEnabled) {
		this.sellingInvEnabled = sellingInvEnabled;
		config.set("config.sellingInvEnabled", sellingInvEnabled);
	}
	public String[] getPricelist() {
		return pricelist;
	}
	public void setPricelist(String[] pricelist) {
		this.pricelist = pricelist;
	}
}