package me.Magnusri.DragonBusiness2.commands;

import org.bukkit.ChatColor;
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
	public boolean invite() {
		//EXECUTE DISBAND HELP PROGRAM HERE
		player.sendMessage("Help invite Executed!");
		return true;
	}
	public boolean ERRORnotInCo() {
		player.sendMessage(ChatColor.RED + "You are not in a company");
		return true;
	}
	public boolean ERRORtargetNotOnline() {
		player.sendMessage(ChatColor.RED + "Target player is not online!");
		return true;
	}
	public boolean ERRORnoInvitesPending() {
		player.sendMessage(ChatColor.RED + "You have no invitations pending");
		return true;
	}
}