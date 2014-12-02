package me.Magnusri.DragonBusiness2.commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Help {
	
	Player player;
	Plugin plugin;
	
	public Help(Plugin plugin, Player player){
		this.player = player;
		this.plugin = plugin;
	}
	public boolean execute(){
		//EXECUTE HELP PROGRAM HERE
		player.sendMessage("Help Executed!");
		return true;
	}
}