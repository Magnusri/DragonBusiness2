package me.Magnusri.DragonBusiness2.commands;

import java.sql.SQLException;

import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CmdExecutor {
	
	//Prepare Commands
	Help help;
	
	public CmdExecutor(Plugin plugin, Player player, Command cmd, String[] args, DBHandler db){
		
		
		if (args.length != 0){
			switch (args[0]){
			case "help":
				//Instantiate command
				help = new Help(plugin, player);
				help.execute();
				break;
			case "insertme":
				try {
					db.insertPlayer(plugin, player);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case "default":
				//Instantiate command
				help = new Help(plugin, player);
				help.execute();
				break;
			}
		} else {
			//Instantiate command
			help = new Help(plugin, player);
			help.execute();
		}
	}
}