package me.Magnusri.DragonBusiness2.commands;

import java.util.ArrayList;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.Magnusri.DragonBusiness2.DBSystem.DBCompany;
import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;
import me.Magnusri.DragonBusiness2.DBSystem.DBPlayer;

public class Tools {
	
	DBHandler db;
	Plugin plugin;
	Player player;
	
	public Tools (DBHandler db, Player player, Plugin plugin){
		this.db = db;
		this.plugin = plugin;
		this.player = player;
	}
	
	public boolean isPlayerInCompany(){
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
}
