package me.Magnusri.DragonBusiness2.commands;

import me.Magnusri.DragonBusiness2.DBSystem.DBCompany;
import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;
import me.Magnusri.DragonBusiness2.DBSystem.DBPlayer;

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
			case "test":
				//TEST COMMAND
				db.removeCompany(plugin, Integer.parseInt(args[1]));
				break;
			case "changedesc":
				player.sendMessage("This command is not yet implemented!");
				break;
			case "top":
				player.sendMessage(ChatColor.AQUA + "--- Top 10 companies ---");
				int counter = 1;
				for (DBCompany dbCompany : db.getTopCompanyList()){
					player.sendMessage(ChatColor.GOLD + "  - " + counter + ". " + dbCompany.getName() + " - " + dbCompany.getValue() + "$");
					counter++;
				}
				player.sendMessage(ChatColor.AQUA + "---");
				break;
			case "info":
				if (args.length == 1){
					if (db.getPlayer(player).getRank().equals("none")){
						help = new Help(plugin, player);
						help.ERRORnotInCo();
						player.sendMessage(ChatColor.AQUA + "If you were looking for another company, please add company name!");
						break;
					}
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					player.sendMessage(ChatColor.AQUA + "--- " + dbCompany.getName() + " ---");
					player.sendMessage(ChatColor.GOLD + "  - CEO:");
					player.sendMessage(ChatColor.WHITE + tools.getCEOInCo(dbCompany).getName());
					player.sendMessage(ChatColor.GOLD + "  - Description:");
					player.sendMessage(ChatColor.WHITE + dbCompany.getInfo());
					if (tools.getLeadersInCo(dbCompany).size() > 0){
						player.sendMessage(ChatColor.GOLD + "  - Leaders:");
						player.sendMessage(ChatColor.WHITE + tools.getLeadersInCo(dbCompany).toString());
					}
					if (tools.getEmployeesInCo(dbCompany).size() > 0){
						player.sendMessage(ChatColor.GOLD + "  - Employees:");
						player.sendMessage(ChatColor.WHITE + tools.getEmployeesInCo(dbCompany).toString());
					}
					player.sendMessage(ChatColor.AQUA + "---");
					
					break;
				}
				if (args.length == 2){
					DBCompany dbCompany = db.getCompany(args[1]);
					if (dbCompany == null){
						player.sendMessage(ChatColor.RED + "A company with the name " + args[1] + " was not found!");
						break;
					}
					
					player.sendMessage(ChatColor.AQUA + "--- " + dbCompany.getName() + " ---");
					player.sendMessage(ChatColor.GOLD + "  - CEO:");
					if (tools.getCEOInCo(dbCompany) != null)
						player.sendMessage(ChatColor.WHITE + tools.getCEOInCo(dbCompany).getName());
					player.sendMessage(ChatColor.GOLD + "  - Description:");
					player.sendMessage(ChatColor.WHITE + dbCompany.getInfo());
					player.sendMessage(ChatColor.GOLD + "  - Value:");
					player.sendMessage(ChatColor.WHITE + Double.toString(dbCompany.getValue()) + "$");
					if (tools.getLeadersInCo(dbCompany).size() > 0){
						player.sendMessage(ChatColor.GOLD + "  - Leaders:");
						player.sendMessage(ChatColor.WHITE + tools.getLeadersInCo(dbCompany).toString());
					}
					if (tools.getEmployeesInCo(dbCompany).size() > 0){
						player.sendMessage(ChatColor.GOLD + "  - Employees:");
						player.sendMessage(ChatColor.WHITE + tools.getEmployeesInCo(dbCompany).toString());
					}
					player.sendMessage(ChatColor.AQUA + "---");
					
					break;
				}
				
				
				
				break;
			case "accept":
				if (!db.getPlayer(player).getPendingInvite().equals("none")){
					String company = db.getPlayer(player).getPendingInvite();
					player.sendMessage(ChatColor.AQUA + "You joined " + company + "!");
					db.addPlayerToCompany(plugin, player, company);
					db.setPlayerInvite(plugin, player.getName(), "none");
					db.setPlayerRank(plugin, player, "Employee");
					tools.msgPlayersInCo(company, ChatColor.AQUA + player.getName() + " joined the company!");
				} else {
					help = new Help(plugin, player);
					help.ERRORnoInvitesPending();
				}
				break;
			case "decline":
				if (!db.getPlayer(player).getPendingInvite().equals("none")){
					player.sendMessage(ChatColor.AQUA + "You declined the invite from " + db.getPlayer(player).getPendingInvite());
					db.setPlayerInvite(plugin, player.getName(), "none");
				} else {
					help = new Help(plugin, player);
					help.ERRORnoInvitesPending();
				}
				break;
			case "invite":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can invite people to the company");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player);
					help.invite();
					break;
				}
				
				if (plugin.getServer().getPlayer(args[1]) != null){
					if (db.getPlayer(plugin.getServer().getPlayer(args[1])) == null){
						db.insertPlayer(plugin, plugin.getServer().getPlayer(args[1]));
					}
					if (!db.getPlayer(plugin.getServer().getPlayer(args[1])).getRank().equals("none")){
						player.sendMessage(ChatColor.RED + "That player is already in a company");
						break;
					}
					String companyName = db.getCompany(db.getPlayer(player).getCompanyid()).getName();
					db.setPlayerInvite(plugin, args[1], companyName);
					tools.msgPlayerByName(args[1], ChatColor.AQUA + "You have been invited to join " + companyName);
					tools.msgPlayerByName(args[1], ChatColor.GOLD + "To accept invite, type: /c acceptinvite");
				} else {
					help = new Help(plugin, player);
					help.ERRORtargetNotOnline();
					break;
				}
				
				break;
			case "leave":
				if (!db.getPlayer(player).getRank().equals("none")){
					if (!db.getPlayer(player).getRank().equals("CEO")){
						db.removePlayerFromCompany(plugin, player.getName());
						db.removePlayerRank(plugin, player.getName());
						player.sendMessage(ChatColor.GOLD + "You left the company");
					} else {
						player.sendMessage(ChatColor.RED + "A CEO cannot leave his company! Please disband it, ");
						player.sendMessage(ChatColor.RED + "  or make someone else the new CEO first!");
					}
				} else {
					help = new Help(plugin, player);
					help.ERRORnotInCo();
				}
				break;
			case "disband":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can disband the company!");
					break;
				}
				if (args.length == 1){
					player.sendMessage(ChatColor.GOLD + "Enter: \"/c disband confirm\" to disband! This is permanent!");
				}else if (args.length == 2 && args[1].equals("confirm")){
					int companyID = db.getPlayer(player).getCompanyid();
					tools.msgPlayersInCo(db.getCompany(db.getPlayer(player).getCompanyid()).getName(), ChatColor.GOLD + "Your company was disbanded! You are now unemployed");
					for (DBPlayer player1 : db.getPlayerListInCompany(db.getCompany(db.getPlayer(player).getCompanyid()).getName())){
						db.removePlayerFromCompany(plugin, player1.getName());
						db.removePlayerRank(plugin, player1.getName());
					}
					db.removeCompany(plugin, companyID);
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
					player.sendMessage(ChatColor.AQUA + "--- " + args[1] + " was just created! ---");
					player.sendMessage(ChatColor.GOLD + "  - Description:");
					player.sendMessage(ChatColor.WHITE + db.getCompany(args[1]).getInfo());
					player.sendMessage(ChatColor.GRAY + "This description can be changed by using the following command:");
					player.sendMessage(ChatColor.GRAY + "/c changedesc <text>");
					player.sendMessage(ChatColor.GOLD + "  - Your rank:");
					player.sendMessage(ChatColor.WHITE + "    - " + db.getPlayer(player).getRank());
					player.sendMessage(ChatColor.AQUA + "---");
					tools.msgOnlinePlayers(ChatColor.AQUA + player.getName() + " just created the company " + ChatColor.GOLD + args[1] + "!");
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