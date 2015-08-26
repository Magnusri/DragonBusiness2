package me.Magnusri.DragonBusiness2.commands;

import me.Magnusri.DragonBusiness2.DBSystem.DBCompany;
import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;
import me.Magnusri.DragonBusiness2.DBSystem.DBPlayer;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class CmdExecutor {
	
	//Prepare helpcenter
	Help help;
	Tools tools;
	Config config;
	
	public CmdExecutor(Plugin plugin, Player player, Command cmd, String[] args, DBHandler db, Economy economy){
		
		tools = new Tools(db, player, plugin, economy);
		config = new Config(plugin);
		
		plugin.reloadConfig();
		
		if (args.length != 0){
			switch (args[0]){
			case "help":
				if (args.length == 2){
					help = new Help(plugin, player);
					if (args[1].equals("create"))
						help.createCo();
					if (args[1].equals("disband"))
						help.disbandCo();
					if (args[1].equals("invite"))
						help.invite();
					if (args[1].equals("fire"))
						help.fire();
					if (args[1].equals("changedesc"))
						help.changedesc();
					if (args[1].equals("promote"))
						help.promote();
					if (args[1].equals("demote"))
						help.demote();
					if (args[1].equals("deposit"))
						help.deposit();
					if (args[1].equals("top"))
						help.top();
					if (args[1].equals("info"))
						help.info();
					if (args[1].equals("pinfo"))
						help.pinfo();
					if (args[1].equals("leave"))
						help.leave();
					if (args[1].equals("makeceo"))
						help.makeceo();
					break;
				}
				help = new Help(plugin, player);
				help.all();
				break;
			case "test":
				//TEST COMMAND
				
				break;
			case "changedesc":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can change the description!");
					break;
				}
				if (args.length < 2){
					help = new Help(plugin, player);
					help.changedesc();
					break;
				} else {
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					String infoLine = "";
					
					for (String word : args){
						infoLine += (word + " ");
					}
					
					db.setCompanyInfo(plugin, dbCompany.getName(), infoLine.substring(11));
					
					player.sendMessage(ChatColor.AQUA + "Company description changed to:");
					player.sendMessage(ChatColor.GOLD + infoLine.substring(11));
				}
				break;
			case "makeceo":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can do this!");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player);
					help.makeceo();
					break;
				} else {
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					boolean sameCo = false;
					
					DBPlayer targetDbPlayer = null;
					Player targetPlayer = null;
					
					for (Player playerSearch : plugin.getServer().getOnlinePlayers()){
						if (playerSearch.getName().equals(args[1])){
							targetPlayer = playerSearch;
						}
					}
					for (DBPlayer dbPlayerSearch : db.getPlayerListInCompany(dbCompany.getName())){
						if (dbPlayerSearch.getName().equals(args[1])){
							sameCo = true;
							targetDbPlayer = dbPlayerSearch;
						}
					}
					
					if (targetPlayer == null){
						player.sendMessage(ChatColor.RED + "Target player must be online!");
						break;
					}
					
					if (sameCo){
						db.setPlayerRank(plugin, targetPlayer, "CEO");
						db.setPlayerRank(plugin, player, "Leader");
						tools.msgPlayersInCo(dbCompany.getName(), ChatColor.AQUA + "Company leadership changed!");
						tools.msgPlayersInCo(dbCompany.getName(), ChatColor.GOLD + targetPlayer.getName() + " is now the new CEO of the company!");
					} else {
						player.sendMessage(ChatColor.RED + "This player is not in your company!");
					}
				}
				break;
			case "demote":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can demote employees!");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player);
					help.promote();
					break;
				} else {
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					boolean sameCo = false;
					
					DBPlayer targetDbPlayer = null;
					Player targetPlayer = null;
					
					for (Player playerSearch : plugin.getServer().getOnlinePlayers()){
						if (playerSearch.getName().equals(args[1])){
							targetPlayer = playerSearch;
						}
					}
					for (DBPlayer dbPlayerSearch : db.getPlayerListInCompany(dbCompany.getName())){
						if (dbPlayerSearch.getName().equals(args[1])){
							sameCo = true;
							targetDbPlayer = dbPlayerSearch;
						}
					}
					
					if (targetPlayer == null){
						player.sendMessage(ChatColor.RED + "Target player must be online!");
						break;
					}
					
					if (sameCo){
						if (targetDbPlayer.getRank().equals("Leader")){
							db.setPlayerRank(plugin, targetPlayer, "Employee");
							tools.msgPlayersInCo(dbCompany.getName(), ChatColor.AQUA + args[1] + " has been demoted to Employee!");
						} else {
							player.sendMessage(ChatColor.RED + "Only Leaders can be demoted");
						}
					} else {
						player.sendMessage(ChatColor.RED + "This player is not in your company!");
					}
				}
				break;
			case "promote":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can promote employees!");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player);
					help.promote();
					break;
				} else {
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					boolean sameCo = false;
					
					DBPlayer targetDbPlayer = null;
					Player targetPlayer = null;
					
					for (Player playerSearch : plugin.getServer().getOnlinePlayers()){
						if (playerSearch.getName().equals(args[1])){
							targetPlayer = playerSearch;
						}
					}
					for (DBPlayer dbPlayerSearch : db.getPlayerListInCompany(dbCompany.getName())){
						if (dbPlayerSearch.getName().equals(args[1])){
							sameCo = true;
							targetDbPlayer = dbPlayerSearch;
						}
					}
					
					if (targetPlayer == null){
						player.sendMessage(ChatColor.RED + "Target player must be online!");
						break;
					}
					
					if (sameCo){
						if (targetDbPlayer.getRank().equals("Employee")){
							db.setPlayerRank(plugin, targetPlayer, "Leader");
							tools.msgPlayersInCo(dbCompany.getName(), ChatColor.AQUA + args[1] + " has been promoted to Leader!");
						} else {
							player.sendMessage(ChatColor.RED + "Only Employees can be promoted");
						}
					} else {
						player.sendMessage(ChatColor.RED + "This player is not in your company!");
					}
				}
				break;
			case "deposit":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				if (args.length != 2 || !tools.isNumeric(args[1])){
					help = new Help(plugin, player);
					help.deposit();
					break;
				}
				
				if (economy.getBalance(player.getName()) < Double.parseDouble(args[1])){
					player.sendMessage(ChatColor.RED + "You do not have this much money!");
				} else {
					if (tools.sign(Double.parseDouble(args[1])) != +1){
						player.sendMessage(ChatColor.RED + "The number has to be positive!");
						break;
					}
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					double oldValue = db.getCompany(dbCompany.getName()).getValue();
					
					double newValue = oldValue + (Double.parseDouble(args[1]) - (Double.parseDouble(args[1]) * config.getDepositFee()));
					
					if (config.isMilestonesEnabled()){
						tools.doMilestones(dbCompany.getName(), oldValue, newValue);
					}
					
					db.setCompanyValue(plugin, dbCompany.getName(), newValue);
					economy.withdrawPlayer(player.getName(), Double.parseDouble(args[1]));
					player.sendMessage(ChatColor.RED + "You deposited " + args[1] + "$, with a fee of " + config.getDepositFee() + "%.");
				}
				break;
			case "sell":
				if (!config.isSellingInvEnabled())
					break;
				if (args.length != 1){
						help = new Help(plugin, player);
						help.sell();
						break;
					}
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				
				Inventory inventory = plugin.getServer().createInventory(player, 36, "Sell Items");
			    player.openInventory(inventory);
			    
				break;
			case "fire":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player);
					help.ERRORnotInCo();
					break;
				}
				if (!(db.getPlayer(player).getRank().equals("CEO") || db.getPlayer(player).getRank().equals("Leader"))){
					player.sendMessage(ChatColor.RED + "Only the CEO and Leaders can fire employees!");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player);
					help.fire();
					break;
				} else {
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					boolean sameCo = false;
				
					for (DBPlayer dbPlayerSearch : db.getPlayerListInCompany(dbCompany.getName())){
						if (dbPlayerSearch.getName().equals(args[1])){
							sameCo = true;
						}
					}
					
					if (sameCo){
						db.removePlayerFromCompany(plugin, args[1]);
						db.removePlayerRank(plugin, args[1]);
						tools.msgPlayersInCo(dbCompany.getName(), ChatColor.AQUA + args[1] + " has been fired from the company!");
						tools.msgPlayerByName(args[1], ChatColor.RED + "You have been fired from the company! You are now unemployed!");
					} else {
						player.sendMessage(ChatColor.RED + "This player is not in your company!");
					}
				}
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
					if (dbCompany.getBankrupt())
						player.sendMessage(ChatColor.RED + "This company is bankrupt, and available on the open market!");
					player.sendMessage(ChatColor.GOLD + "  - CEO:");
					player.sendMessage(ChatColor.WHITE + tools.getCEOInCo(dbCompany).getName());
					player.sendMessage(ChatColor.GOLD + "  - Description:");
					player.sendMessage(ChatColor.WHITE + dbCompany.getInfo());
					player.sendMessage(ChatColor.GOLD + "  - Value:");
					player.sendMessage(ChatColor.WHITE + "" + dbCompany.getValue() + "$");
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
			case "pinfo":
				if (args.length == 2){
					if (db.getPlayer(player).getRank().equals("none")){
						help = new Help(plugin, player);
						help.pinfo();
						player.sendMessage(ChatColor.AQUA + "/c pinfo <playername>");
						break;
					}
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					player.sendMessage(ChatColor.AQUA + "--- " + dbPlayer.getName() + " ---");
					player.sendMessage(ChatColor.AQUA + "Company: " + ChatColor.WHITE + dbPlayer.getCompanyid());
					player.sendMessage(ChatColor.AQUA + "Rank: " + ChatColor.WHITE + dbPlayer.getRank());
					player.sendMessage(ChatColor.AQUA + "Earned: " + ChatColor.WHITE + dbPlayer.getEarned());
					//player.sendMessage(ChatColor.AQUA + "Level: " + dbPlayer.getLevel());
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
				if (!(db.getPlayer(player).getRank().equals("CEO") || db.getPlayer(player).getRank().equals("Leader"))){
					player.sendMessage(ChatColor.RED + "Only the CEO or Leaders can invite people to the company");
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
					player.sendMessage(ChatColor.AQUA + "You have sent a company invite to " + args[1]);
					tools.msgPlayerByName(args[1], ChatColor.AQUA + "You have been invited to join " + companyName);
					tools.msgPlayerByName(args[1], ChatColor.GOLD + "To accept invite, type: /c accept");
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
					if (economy.getBalance(player.getName()) < config.getDisbandCost()){
						player.sendMessage(ChatColor.RED + "You do not have enough money to disband your company.");
						break;
					}
					int companyID = db.getPlayer(player).getCompanyid();
					tools.msgPlayersInCo(db.getCompany(db.getPlayer(player).getCompanyid()).getName(), ChatColor.GOLD + "Your company was disbanded! You are now unemployed");
					for (DBPlayer player1 : db.getPlayerListInCompany(db.getCompany(db.getPlayer(player).getCompanyid()).getName())){
						db.removePlayerFromCompany(plugin, player1.getName());
						db.removePlayerRank(plugin, player1.getName());
					}
					db.removeCompany(plugin, companyID);
					economy.withdrawPlayer(player.getName(), config.getDisbandCost());
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
					if (economy.getBalance(player.getName()) < config.getCreateCost()){
						player.sendMessage(ChatColor.RED + "You do not have enough money to create a company.");
						break;
					}
					db.insertCompany(plugin, player, args[1], "This is the informative description", config.getCreateCost());
					player.sendMessage(ChatColor.AQUA + "--- " + args[1] + " was just created! ---");
					player.sendMessage(ChatColor.GOLD + "  - Description:");
					player.sendMessage(ChatColor.WHITE + db.getCompany(args[1]).getInfo());
					player.sendMessage(ChatColor.GRAY + "This description can be changed by using the following command:");
					player.sendMessage(ChatColor.GRAY + "/c changedesc <text>");
					player.sendMessage(ChatColor.GOLD + "  - Your rank:");
					player.sendMessage(ChatColor.WHITE + "    - " + db.getPlayer(player).getRank());
					player.sendMessage(ChatColor.AQUA + "---");
					tools.msgOnlinePlayers(ChatColor.AQUA + player.getName() + " just created the company " + ChatColor.GOLD + args[1] + "!");
					economy.withdrawPlayer(player.getName(), config.getCreateCost());
				}else{
					help = new Help(plugin, player);
					help.createCo();
				}
				break;
			default:
				help = new Help(plugin, player);
				help.all();
				break;
			}
		} else {
			help = new Help(plugin, player);
			help.all();
		}
	}
}
