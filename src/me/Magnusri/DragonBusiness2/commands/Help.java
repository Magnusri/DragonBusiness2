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
	public boolean all(){
		//EXECUTE HELP PROGRAM HERE
		player.sendMessage("Help Executed!");
		return true;
	}
	public boolean createCo(){
		//EXECUTE CREATECO HELP PROGRAM HERE
		player.sendMessage("Help CreateCo Executed!");
		return true;
	}
	public boolean disbandCo() {
		//EXECUTE DISBAND HELP PROGRAM HERE
		player.sendMessage("Help DisbandCo Executed!");
		return true;
	}
}