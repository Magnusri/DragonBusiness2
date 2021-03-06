package me.Magnusri.DragonBusiness2.commands;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.Magnusri.DragonBusiness2.DBSystem.DBCompany;
import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;
import me.Magnusri.DragonBusiness2.DBSystem.DBPlayer;

public class Tools {
	
	DBHandler db;
	Plugin plugin;
	Config config;
	Economy economy;
	
	public Tools (DBHandler db, Plugin plugin, Economy economy){
		this.db = db;
		this.plugin = plugin;
		this.config = new Config(plugin);
		this.economy = economy;
	}
	
	public boolean isPlayerInCompany(Player player){
		ArrayList<DBPlayer> playerList = db.getPlayerList();
		
		for (DBPlayer dbplayer : playerList){
			if (dbplayer.getUuid().equals(player.getUniqueId().toString())){
				if (dbplayer.getCompanyid() != 0){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isPlayerInCompany(String player){
		ArrayList<DBPlayer> playerList = db.getPlayerList();
		
		for (DBPlayer dbplayer : playerList){
			if (dbplayer.getName().equals(player)){
				if (dbplayer.getCompanyid() != 0){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean msgPlayerByName(String playerName, String message){
		for (Player player : plugin.getServer().getOnlinePlayers()){
			if (player.getName().equals(playerName)){
				player.sendMessage(message);
			}
		}
		return true;
	}
	
	public DBPlayer getCEOInCo(DBCompany company){
		for (DBPlayer dbPlayer : db.getPlayerListInCompany(company.getName())){
			if (dbPlayer.getRank().equals("CEO"))
				return dbPlayer;
		}
		return null;
	}
	
	public ArrayList<DBPlayer> getLeadersInCo(DBCompany company){
	  	ArrayList<DBPlayer> dbPlayerLeaderList = new ArrayList<DBPlayer>();
		for (DBPlayer dbPlayer : db.getPlayerListInCompany(company.getName())){
			if (dbPlayer.getRank().equals("Leader")){
				dbPlayerLeaderList.add(dbPlayer);
			}
		}
		return dbPlayerLeaderList;
	}
	
	public ArrayList<DBPlayer> getEmployeesInCo(DBCompany company){
	  	ArrayList<DBPlayer> dbPlayerEmployeeList = new ArrayList<DBPlayer>();
		for (DBPlayer dbPlayer : db.getPlayerListInCompany(company.getName())){
			if (dbPlayer.getRank().equals("Employee")){
				dbPlayerEmployeeList.add(dbPlayer);
			}
		}
		return dbPlayerEmployeeList;
	}
	
	public boolean msgOnlinePlayers(String message){
		for (Player player : plugin.getServer().getOnlinePlayers()){
			player.sendMessage(message);
		}
		return true;
	}
	
	public boolean isNumeric(String str){
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	public boolean msgPlayersInCo(String company, String message){
		for (DBPlayer player1 : db.getPlayerListInCompany(company)){
			for (Player checkPlayer : plugin.getServer().getOnlinePlayers()){
				if (checkPlayer.getName().equals(player1.getName())){
					plugin.getServer().getPlayer(player1.getName()).sendMessage(message);
				}
			}
		}
		return true;
	}
	
	public int sign(double f) {
	    if (f != f) throw new IllegalArgumentException("NaN");
	    if (f == 0) return 0;
	    f *= Double.POSITIVE_INFINITY;
	    if (f == Double.POSITIVE_INFINITY) return +1;
	    if (f == Double.NEGATIVE_INFINITY) return -1;

	    //this should never be reached, but I've been wrong before...
	    throw new IllegalArgumentException("Unfathomed double");
	}
	
	public boolean payBonusToPlayer(String playername){
		economy.depositPlayer(playername, config.getBonusAmount());
		msgPlayerByName(playername, ChatColor.AQUA + "You earned a company bonus of $" + config.getBonusAmount());
		return true;
	}
	
	public boolean playerIncome(Player player, double amount){
		
		DBPlayer dbplayer = db.getPlayer(player);
		DBCompany company = db.getCompany(dbplayer.getCompanyid());
		
		dbplayer.setEarned(dbplayer.getEarned() + amount);
		doPlayerLevel(dbplayer);
		
		List<DBPlayer> players = db.getPlayerListInCompany(company.getName());
		
		double moneyforco = amount;
		
		for (int i = 0; i < players.size(); i++){
			
			double payout = 0.0;
			
			if (config.isIncomeLevelsEnabled())
				payout = amount * ((double)players.get(i).getLevel() / (double)100.0);
			else
				payout = ((amount * 20) / 100) / players.size();
			
			economy.depositPlayer(players.get(i).getName(), round(payout, 2));
			moneyforco -= payout;
			doPlayerLevel(players.get(i));
			msgPlayerByName(players.get(i).getName(), ChatColor.GREEN + "You earned " + round(payout, 2) + " from the last company sale!");
		}
		
		db.setCompanyValue(plugin, company.getName(), company.getValue() + round(moneyforco, 2));
		
		if (company.getBankrupt() && (company.getValue() + round(moneyforco, 2)) > 1000){
			db.setCompanyBankrupt(plugin, company.getName(), false);
		}
		
		return true;
	}
	
	public boolean doPlayerLevel(DBPlayer dbplayer){
		
		DBCompany company = db.getCompany(dbplayer.getCompanyid());
		
		double percentage = (dbplayer.getEarned() / company.getValue()) * 100;
		
		if (percentage < 5){
			dbplayer.setLevel(0);
			return true;
		}
		if (percentage < 10){
			dbplayer.setLevel(1);
			return true;
		}
		if (percentage < 20){
			dbplayer.setLevel(2);
			return true;
		}
		if (percentage < 30){
			dbplayer.setLevel(3);
			return true;
		}
		if (percentage < 40){
			dbplayer.setLevel(4);
			return true;
		}
		if (percentage < 50){
			dbplayer.setLevel(5);
			return true;
		}
		if (percentage < 60){
			dbplayer.setLevel(6);
			return true;
		}
		if (percentage < 70){
			dbplayer.setLevel(7);
			return true;
		}
		if (percentage < 80){
			dbplayer.setLevel(8);
			return true;
		}
		if (percentage < 90){
			dbplayer.setLevel(9);
			return true;
		}
		if (percentage < 100){
			dbplayer.setLevel(10);
			return true;
		}
		if (percentage > 100){
			dbplayer.setLevel(10);
			return true;
		}
		
		return true;
	}

	public boolean doMilestones(String company, double oldValue, double newValue) {
		if (config.isMilestonesEnabled()){
			double[] milestones = config.getMilestones();
			for (int i = milestones.length - 1; i > 0; i--){
				if (milestones[i] <= newValue && oldValue <= milestones[i]){
					msgOnlinePlayers(ChatColor.GOLD + company + " reached the milestone of $" + milestones[i]);
					return true;
				}
			}
		}
		return false;
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public boolean doCompanyDecay(int companyId) {

		DBCompany company = db.getCompany(companyId);
		double companyValue = company.getValue();
		double decay = 0;
		
		if(companyValue < 1000 && companyValue >= 0){
            //for every 1 day - $10
			decay = 5;
			db.setCompanyValue(plugin, company.getName(), companyValue - decay);
        }
        if(companyValue < 10000 && companyValue > 1000){
            //for every 1 day - $100
			decay = 50;
			db.setCompanyValue(plugin, company.getName(), companyValue - decay);
        }
        if(companyValue < 100000 && companyValue > 10000){
            //for every 1 day - $1000
			decay = 500;
			db.setCompanyValue(plugin, company.getName(), companyValue - decay);
        }
        if(companyValue < 1000000 && companyValue > 100000){
            //for every 1 day - $10000
			decay = 5000;
			db.setCompanyValue(plugin, company.getName(), companyValue - decay);
        }
        if(companyValue < 10000000 && companyValue > 1000000){
            //for every 1 day - $100000
			decay = 50000;
			db.setCompanyValue(plugin, company.getName(), companyValue - decay);
        }   
        if(companyValue < 100000000 && companyValue > 10000000){
            //for every 1 day - $1000000
			decay = 500000;
			db.setCompanyValue(plugin, company.getName(), companyValue - decay);
        }   
        if(companyValue < 1000000000 && companyValue > 100000000){
            //for every 1 day - $10000000
			decay = 5000000;
			db.setCompanyValue(plugin, company.getName(), companyValue - decay);
        }       
 
        if((companyValue - decay) <= 0){
            // DO BANKRUPT
        	// WARN ALL ONLINE THAT THIS CO WENT BANKRUPT
        	if (!company.getBankrupt()){
        		msgOnlinePlayers(ChatColor.RED + "The company " + company.getName() + " just went bankrupt,");
        		msgOnlinePlayers(ChatColor.RED + "The company " + company.getName() + "and is now available on the market!");
        		db.setCompanyBankrupt(plugin, company.getName(), true);
        	}
        }
		
		return false;
	}
}
