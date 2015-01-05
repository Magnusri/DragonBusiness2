package me.Magnusri.DragonBusiness2.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Help {
	
	Player player;
	Plugin plugin;
	Config config;
	
	public Help(Plugin plugin, Player player){
		this.player = player;
		this.plugin = plugin;
		this.config = new Config();
	}
	public boolean all(){
		player.sendMessage(ChatColor.AQUA + "--- DragonBusiness 2.0 Help ---");
		player.sendMessage(ChatColor.WHITE + "For information on a command, do /c help <command>");
		player.sendMessage(ChatColor.GOLD + " - /c create <companyname>");
		player.sendMessage(ChatColor.RED + "    - Cost: " + config.getCreateCost() + "$");
		player.sendMessage(ChatColor.GOLD + " - /c disband");
		player.sendMessage(ChatColor.RED + "    - Cost: " + config.getDisbandCost() + "$");
		player.sendMessage(ChatColor.GOLD + " - /c invite <playername>");
		player.sendMessage(ChatColor.GOLD + " - /c fire <playername>");
		player.sendMessage(ChatColor.GOLD + " - /c changedesc <new description>");
		player.sendMessage(ChatColor.GOLD + " - /c promote <playername>");
		player.sendMessage(ChatColor.GOLD + " - /c demote <playername>");
		player.sendMessage(ChatColor.GOLD + " - /c deposit <amount>");
		player.sendMessage(ChatColor.GOLD + " - /c top");
		player.sendMessage(ChatColor.GOLD + " - /c info");
		player.sendMessage(ChatColor.GOLD + " - /c leave");
		player.sendMessage(ChatColor.GOLD + " - /c makeceo <playername>");
		player.sendMessage(ChatColor.AQUA + "--- DragonBusiness 2.0 Help ---");
		return true;
	}
	public boolean createCo(){
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c create <companyname>");
		player.sendMessage(ChatColor.WHITE + "Creates a new company with the specified name. Cost: " + config.getCreateCost() + "$.");
		return true;
	}
	public boolean disbandCo() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c disband");
		player.sendMessage(ChatColor.WHITE + "Disbands your company. This is permanent, and only for CEOs. Cost: " + config.getDisbandCost() + "$.");
		return true;
	}
	public boolean invite() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c invite <playername>");
		player.sendMessage(ChatColor.WHITE + "Invites a player to join your company. Only for CEOs and Leaders.");
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
	public boolean fire() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c fire <playername>");
		player.sendMessage(ChatColor.WHITE + "Fires a player from your company. Only for CEOs and Leaders.");
		return true;
	}
	public boolean deposit() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c deposit <amount>");
		player.sendMessage(ChatColor.WHITE + "Deposits an amount of money into the company you are in.");
		return true;
	}
	public boolean promote() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c promote <playername>");
		player.sendMessage(ChatColor.WHITE + "Promotes an Employee to Leader. This allows him/her to use more company commands.");
		return true;
	}
	public boolean makeceo() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c makeceo <playername>");
		player.sendMessage(ChatColor.WHITE + "Resign your position as CEO, and pass it on to a new player.");
		return true;
	}
	public boolean changedesc() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c changedesc <the new company description>");
		player.sendMessage(ChatColor.WHITE + "Changes the company description! Accepts spaced sentences!");
		return true;
	}
	public boolean demote() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c demote <playername>");
		player.sendMessage(ChatColor.WHITE + "Demotes a players Leader rank, and makes him/her an Employee.");
		return true;
	}
	public boolean top() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c top");
		player.sendMessage(ChatColor.WHITE + "Displays the top 10 companies on the server.");
		return true;
	}
	public boolean info() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c info <Optional:companyname>");
		player.sendMessage(ChatColor.WHITE + "Displays information about your, or target company.");
		return true;
	}
	public boolean leave() {
		player.sendMessage(ChatColor.AQUA + "--- Command syntax: ---");
		player.sendMessage(ChatColor.GOLD + " - /c leave");
		player.sendMessage(ChatColor.WHITE + "Leave the company you are in.");
		return true;
	}
}