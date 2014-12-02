package me.Magnusri.DragonBusiness2;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Help {
	//CHANGE FORMAT ACCORDING TO STANDARD .cfg FILE!
	
	//FORMAT FOR help LIST:
	//
	// sample line:
	// HELP-TOPIC:Line of text that will help,Another line that will help
	//
	public ArrayList<String> help;
	public Plugin plugin;
	
	public Help(Plugin plugin){
		this.plugin = plugin;
		
		//LOAD HELPLIST FROM SETTINGS. IF NOT EXIST, CREATE DEFAULTS
	}
	
	public boolean createDefaultHelpfile(){
		return false;
	}
	
	public boolean printHelp(Player recipient, String topic){
		
		//TRANSFORM TO FIT STANDARD .cfg FILE!
		for (int i = 0; i < help.size(); i++){
			String[] helpLines = help.get(i).split(":")[1].split(",");
			if (help.get(i).split(":")[0].split("-")[1].equals(topic)){
				for (int j = 0; j < helpLines.length; j++){
					recipient.sendMessage(helpLines[j]);
				}
			}
		}
		
		return false;
	}
}
