package me.Magnusri.DragonBusiness2.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;

public class Help {
	
	Player player;
	Plugin plugin;
	Config config;
	Tools tools;
	DBHandler db;
	
	public Help(Plugin plugin, Player player, Tools tools, DBHandler db){
		this.player = player;
		this.plugin = plugin;
		this.config = new Config(plugin);
		this.tools = tools;
		this.db = db;
	}
	public boolean all(){
		player.sendMessage(ChatColor.AQUA + "--- DragonBusiness 2.0 Help ---");
		player.sendMessage(ChatColor.GOLD + "For information on a command, do /c help <command>");
		player.sendMessage(ChatColor.DARK_AQUA + " /c create <companyname>");
		player.sendMessage(ChatColor.RED + "  - Cost: $" + config.getCreateCost());
		
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c disband");
		
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.RED + "  - Cost: $" + config.getDisbandCost());
		
		if (db.getPlayer(player).getRank().equals("Leader") || db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c invite <playername>");
		
		if (db.getPlayer(player).getRank().equals("Leader") || db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c fire <playername>");
		
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c changedesc <new description>");
		
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c promote <playername>");
		
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c demote <playername>");
		
		if (!db.getPlayer(player).getRank().equals("none"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c deposit <amount>");
		
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c hiring <on/off>");
		
		if (db.getPlayer(player).getRank().equals("Leader") || db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c hiring ");
		
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c buyout <companyname>");
		
		player.sendMessage(ChatColor.DARK_AQUA + " /c apply");
		
		player.sendMessage(ChatColor.DARK_AQUA + " /c application cancel");
		
		if (db.getPlayer(player).getRank().equals("Leader") || db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c application <playername> accept/decline ");
		
		if (db.getPlayer(player).getRank().equals("Leader") || db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c applications ");
		
		if (db.getPlayer(player).getRank().equals("Leader") || db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c market ");
		
		if (config.isSellingInvEnabled() && !db.getPlayer(player).getRank().equals("none")) 
			player.sendMessage(ChatColor.DARK_AQUA + " /c sell");
		
		player.sendMessage(ChatColor.DARK_AQUA + " /c top");
		player.sendMessage(ChatColor.DARK_AQUA + " /c info");
		player.sendMessage(ChatColor.DARK_AQUA + " /c pinfo");
		
		if (!db.getPlayer(player).getRank().equals("none"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c leave");
		
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.DARK_AQUA + " /c makeceo <playername>");
		
		player.sendMessage(ChatColor.AQUA + "--- DragonBusiness 2.0 Help ---");
		return true;
	}
	public boolean createCo(){
		player.sendMessage(ChatColor.AQUA + " - /c create <companyname>");
		player.sendMessage(ChatColor.WHITE + "Creates a new company with the specified name. Cost: $" + config.getCreateCost());
		return true;
	}
	public boolean disbandCo() {
		player.sendMessage(ChatColor.AQUA + " /c disband");
		player.sendMessage(ChatColor.WHITE + "Disbands your company. This is permanent, and only for CEOs. Cost: $" + config.getDisbandCost());
		return true;
	}
	public boolean invite() {
		player.sendMessage(ChatColor.AQUA + " - /c invite <playername>");
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
		player.sendMessage(ChatColor.AQUA + " - /c fire <playername>");
		player.sendMessage(ChatColor.WHITE + "Fires a player from your company. Only for CEOs and Leaders.");
		return true;
	}
	public boolean deposit() {
		player.sendMessage(ChatColor.AQUA + " - /c deposit <amount>");
		player.sendMessage(ChatColor.WHITE + "Deposits an amount of money into the company you are in.");
		return true;
	}
	public boolean promote() {
		player.sendMessage(ChatColor.AQUA + " - /c promote <playername>");
		player.sendMessage(ChatColor.WHITE + "Promotes an Employee to Leader. This allows him/her to use more company commands.");
		return true;
	}
	public boolean makeceo() {
		player.sendMessage(ChatColor.AQUA + " - /c makeceo <playername>");
		player.sendMessage(ChatColor.WHITE + "Resign your position as CEO, and pass it on to a new player.");
		return true;
	}
	public boolean changedesc() {
		player.sendMessage(ChatColor.AQUA + " - /c changedesc <the new company description>");
		player.sendMessage(ChatColor.WHITE + "Changes the company description! Accepts spaced sentences!");
		return true;
	}
	public boolean demote() {
		player.sendMessage(ChatColor.AQUA + " - /c demote <playername>");
		player.sendMessage(ChatColor.WHITE + "Demotes a players Leader rank, and makes him/her an Employee.");
		return true;
	}
	public boolean top() {
		player.sendMessage(ChatColor.AQUA + " - /c top");
		player.sendMessage(ChatColor.WHITE + "Displays the top 10 companies on the server.");
		return true;
	}
	public boolean info() {
		player.sendMessage(ChatColor.AQUA + " - /c info <optional:companyname>");
		player.sendMessage(ChatColor.WHITE + "Displays information about yours, or the company specified.");
		return true;
	}
	public boolean pinfo() {
		player.sendMessage(ChatColor.AQUA + " - /c pinfo <optional:playername>");
		player.sendMessage(ChatColor.WHITE + "Displays information about you, or the player specified.");
		return true;
	}
	public boolean leave() {
		player.sendMessage(ChatColor.AQUA + " - /c leave");
		player.sendMessage(ChatColor.WHITE + "Leave the company you are in.");
		return true;
	}
	public boolean sell() {
		player.sendMessage(ChatColor.AQUA + " - /c sell");
		player.sendMessage(ChatColor.WHITE + "Sell items on behalf of your company.");
		return true;
	}
	public boolean hiring() {
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.AQUA + " - /c hiring <on/off>");
		if (db.getPlayer(player).getRank().equals("CEO"))
			player.sendMessage(ChatColor.WHITE + "Choose if your company should appear as hiring.");
		player.sendMessage(ChatColor.AQUA + " - /c hiring");
		player.sendMessage(ChatColor.WHITE + "Show a list of companies that are hiring.");
		return true;
	}
	public boolean market() {
		player.sendMessage(ChatColor.AQUA + " - /c market");
		player.sendMessage(ChatColor.WHITE + "Get a list of the companies that are bankrupt, and available on the market.");
		return true;
	}
	public boolean buyout() {
		player.sendMessage(ChatColor.AQUA + " - /c buyout <companyname>");
		player.sendMessage(ChatColor.WHITE + "Buyout the chosen company. This will cost you $" + config.getBuyoutEmployeePrice() + " per employee in that company.");
		return true;
	}
	public boolean applications() {
		player.sendMessage(ChatColor.AQUA + " - /c applications");
		if (db.getPlayer(player).getRank().equals("CEO") || db.getPlayer(player).getRank().equals("Leader"))
			player.sendMessage(ChatColor.WHITE + "See the applications that has been submitted to join your company.");
		else
			player.sendMessage(ChatColor.WHITE + "Check what company you have applied to join.");
		return true;
	}
	public boolean ERRORapplicationInPlace() {
		player.sendMessage(ChatColor.RED + "You already have an application pending!");
		player.sendMessage(ChatColor.RED + "If you want to apply for another company, you must /c application cancel, first.");
		return true;
	}
	public boolean application() {
		if (db.getPlayer(player).getRank().equals("CEO") || db.getPlayer(player).getRank().equals("Leader"))
			player.sendMessage(ChatColor.AQUA + " - /c application <playername> decline/accept");
		if (db.getPlayer(player).getRank().equals("CEO") || db.getPlayer(player).getRank().equals("Leader"))
			player.sendMessage(ChatColor.WHITE + "Accept or decline an application to join your company.");
		player.sendMessage(ChatColor.AQUA + " - /c application cancel");
		player.sendMessage(ChatColor.WHITE + "Cancel your active application to join a company.");
		return true;
	}
	public boolean apply() {
		player.sendMessage(ChatColor.AQUA + " - /c apply <companyname>");
		player.sendMessage(ChatColor.WHITE + "Send an application to join a company.");
		return true;
	}
}
