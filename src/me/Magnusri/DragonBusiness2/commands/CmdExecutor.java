package me.Magnusri.DragonBusiness2.commands;

import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CmdExecutor {
	
	//Prepare helpcenter
	Help help;
	Tools tools;
	
	public CmdExecutor(Plugin plugin, Player player, Command cmd, String[] args, DBHandler db){
		
		tools = new Tools(db, player, plugin);
		
		if (args.length != 0){
			switch (args[0]){
			case "help":
				//Instantiate helpcenter
				help = new Help(plugin, player);
				help.all();
				break;
			case "disband":
				if (args.length == 1){
					player.sendMessage(ChatColor.GOLD + "Enter: \"/c disband confirm\" to disband! This is permanent!");
				}else if (args.length == 2 && args[1].equals("confirm")){
					
				}else{
					help = new Help(plugin, player);
					help.disbandCo();
				}
				break;
			case "create":
				if (tools.isPlayerInCompany()){
					player.sendMessage(ChatColor.RED + "You are already in a company!");
					break;
				}
				if (args.length == 2){
					db.insertCompany(plugin, player, args[1], "This is the informative description");
				}else{
					help = new Help(plugin, player);
					help.createCo();
				}
				break;
			default:
				//Instantiate helpcenter
				help = new Help(plugin, player);
				help.all();
				break;
			}
		} else {
			//Instantiate command
			help = new Help(plugin, player);
			help.all();
		}
		
		
	}
}