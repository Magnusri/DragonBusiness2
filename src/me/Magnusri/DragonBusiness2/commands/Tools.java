package me.Magnusri.DragonBusiness2.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
}
