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
	double buyoutEmployeePrice;
	boolean milestonesEnabled;
	boolean sellingInvEnabled;
	boolean companyDecayEnabled;
	String decayTime;
	double[] milestones;
	String[] pricelist;
	
	Plugin plugin;
	FileConfiguration config;
	
	public Config(double createCost, double disbandCost, double depositFee, boolean milestonesEnabled, boolean sellingInvEnabled, double[] milestones, String[] pricelist, double bonusAmount, double buyoutEmployeePrice, boolean companyDecayEnabled, String decayTime, Plugin plugin) {
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		
		super();
		this.createCost = createCost;
		this.disbandCost = disbandCost;
		this.depositFee = depositFee;
		this.milestonesEnabled = milestonesEnabled;
		this.sellingInvEnabled = sellingInvEnabled;
		this.companyDecayEnabled = companyDecayEnabled;
		this.decayTime = decayTime;
		this.milestones = milestones;
		this.pricelist = pricelist;
		this.bonusAmount = bonusAmount;
		this.plugin = plugin;
		this.buyoutEmployeePrice = buyoutEmployeePrice;
	}
	public Config(Plugin plugin) {
		
		super();
		this.plugin = plugin;
		
		this.config = plugin.getConfig();
		
		//LOAD CONFIGS FROM FILE HERE, IF NO FILE; SET DEFAULTS.
		if (!plugin.getConfig().contains("config.configSet")){
			
			config.set("config.createCost", 1000);
			config.set("config.disbandCost", 200);
			config.set("config.depositFee", 0);
			config.set("config.buyoutEmployeePrice", 100);
			config.set("config.milestonesEnabled", true);
			config.set("config.sellingInvEnabled", true);
			config.set("config.companyDecayEnabled", true);
			config.set("config.decayTime", "14:00");
			config.set("config.bonusAmount", 20);
			config.set("config.configSet", true);

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
		this.buyoutEmployeePrice = config.getDouble("config.buyoutEmployeePrice");
		this.milestonesEnabled = config.getBoolean("config.milestonesEnabled");
		this.sellingInvEnabled = config.getBoolean("config.sellingInvEnabled");
		this.companyDecayEnabled = config.getBoolean("config.companyDecayEnabled");
		this.decayTime = config.getString("config.decayTime");
		this.bonusAmount = config.getDouble("config.bonusAmount");
		
		List<Double> list = config.getDoubleList("config.milestones");
		double[] array = new double[config.getDoubleList("config.milestones").size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i);
		this.milestones = array;
		
		List<String> list1 = config.getStringList("config.pricelist");
		String[] array1 = new String[config.getStringList("config.pricelist").size()];
		for(int i = 0; i < list1.size(); i++) array1[i] = list1.get(i);
		this.pricelist = array1;
	}

	public double getBonusAmount() {
		return bonusAmount;
	}
	
	public void setBonusAmount(double bonusAmount) {
		this.bonusAmount = bonusAmount;
		config.set("config.bonusAmount", bonusAmount);
		plugin.saveConfig();
	}
	
	public double getCreateCost() {
		return createCost;
	}

	public void setCreateCost(float createCost) {
		this.createCost = createCost;
		config.set("config.createCost", createCost);
		plugin.saveConfig();
	}

	public double getDepositFee() {
		return depositFee;
	}

	public void setDepositFee(float depositFee) {
		this.depositFee = depositFee;
		config.set("config.depositFee", depositFee);
		plugin.saveConfig();
	}

	public double getDisbandCost() {
		return disbandCost;
	}

	public void setDisbandCost(float disbandCost) {
		this.disbandCost = disbandCost;
		config.set("config.disbandCost", disbandCost);
		plugin.saveConfig();
	}
	public boolean isMilestonesEnabled() {
		return milestonesEnabled;
	}
	public void setMilestonesEnabled(boolean milestonesEnabled) {
		this.milestonesEnabled = milestonesEnabled;
		config.set("config.milestonesEnabled", milestonesEnabled);
		plugin.saveConfig();
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
		plugin.saveConfig();
	}
	public String[] getPricelist() {
		return pricelist;
	}
	public void setPricelist(String[] pricelist) {
		this.pricelist = pricelist;
		config.set("config.pricelist", pricelist);
		plugin.saveConfig();
	}
	public double getBuyoutEmployeePrice() {
		return buyoutEmployeePrice;
	}
	public void setBuyoutEmployeePrice(double buyoutEmployeePrice) {
		this.buyoutEmployeePrice = buyoutEmployeePrice;
		config.set("config.buyoutEmployeePrice", buyoutEmployeePrice);
		plugin.saveConfig();
	}
	public boolean isCompanyDecayEnabled() {
		return companyDecayEnabled;
	}
	public void setCompanyDecayEnabled(boolean companyDecayEnabled) {
		this.companyDecayEnabled = companyDecayEnabled;
		config.set("config.companyDecayEnabled", companyDecayEnabled);
		plugin.saveConfig();
	}
	public String getDecayTime() {
		return decayTime;
	}
	public void setDecayTime(String decayTime) {
		this.decayTime = decayTime;
		config.set("config.decayTime", decayTime);
		plugin.saveConfig();
	}
}