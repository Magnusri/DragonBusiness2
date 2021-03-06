package me.Magnusri.DragonBusiness2.commands;

import me.Magnusri.DragonBusiness2.DBSystem.DBCompany;
import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;
import me.Magnusri.DragonBusiness2.DBSystem.DBPlayer;
import net.milkbowl.vault.economy.Economy;

import java.util.ArrayList;
import java.util.List;

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
		
		tools = new Tools(db, plugin, economy);
		config = new Config(plugin);
		
		db.insertPlayer(plugin, player);
		
		plugin.reloadConfig();
		
		if (args.length != 0){
			switch (args[0]){
			case "help":
				if (args.length == 2){
					help = new Help(plugin, player, tools, db);
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
					if (args[1].equals("employees"))
						help.employees();
					if (args[1].equals("hiring"))
						help.hiring();
					if (args[1].equals("buyout"))
						help.buyout();
					if (args[1].equals("applications"))
						help.applications();
					if (args[1].equals("application"))
						help.application();
					if (args[1].equals("apply"))
						help.apply();
					if (args[1].equals("market"))
						help.market();
					if (args[1].equals("buyout"))
						help.buyout();
					if (args[1].equals("sell"))
						help.sell();
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
				help = new Help(plugin, player, tools, db);
				help.all();
				break;
			case "test":
				//TEST COMMAND
				
				break;
			case "changedesc":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can change the description!");
					break;
				}
				if (args.length < 2){
					help = new Help(plugin, player, tools, db);
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
					player.sendMessage(ChatColor.WHITE + infoLine.substring(11));
				}
				break;
			case "makeceo":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can do this!");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player, tools, db);
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
						db.setPlayerRank(plugin, db.getPlayer(targetPlayer), "CEO");
						db.setPlayerRank(plugin, db.getPlayer(player), "Leader");
						tools.msgPlayersInCo(dbCompany.getName(), ChatColor.AQUA + "Company leadership changed!");
						tools.msgPlayersInCo(dbCompany.getName(), ChatColor.WHITE + targetPlayer.getName() + " is now the new CEO of the company!");
					} else {
						player.sendMessage(ChatColor.RED + "This player is not in your company!");
					}
				}
				break;
			case "hiring":
				if (args.length == 1){
					player.sendMessage(ChatColor.AQUA + "--- "+ "Hiring Companies" + " ---");
					for (DBCompany dbCompany : db.getCompanyList()){
						if (dbCompany.isHiring()){
							player.sendMessage(ChatColor.DARK_AQUA + "  - " + dbCompany.getName());
						}
					}
					player.sendMessage(ChatColor.AQUA + "--- "+ "Hiring Companies" + " ---");
					break;
				}
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can do this!");
					break;
				}
				if (args.length == 2) {
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					if (args[1].equals("on")){
						//db.setCompanyHiring(plugin, dbCompany.getName(), true);
						dbCompany.setHiring(true);
						player.sendMessage(ChatColor.GREEN + "Your company is now hiring!");
					} else if (args[1].equals("off")){
						//db.setCompanyHiring(plugin, dbCompany.getName(), false);
						dbCompany.setHiring(false);
						player.sendMessage(ChatColor.GREEN + "Your company is no longer hiring!");
					} else {
						help = new Help(plugin, player, tools, db);
						help.hiring();
					}
					break;
				} else {
					help = new Help(plugin, player, tools, db);
					help.hiring();
					break;
				}
			case "demote":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can demote employees!");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player, tools, db);
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
							db.setPlayerRank(plugin, targetDbPlayer, "Employee");
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
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can promote employees!");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player, tools, db);
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
							db.setPlayerRank(plugin, targetDbPlayer, "Leader");
							tools.msgPlayersInCo(dbCompany.getName(), ChatColor.AQUA + args[1] + " has been promoted to Leader!");
						} else {
							player.sendMessage(ChatColor.RED + "Only Employees can be promoted");
						}
					} else {
						player.sendMessage(ChatColor.RED + "This player is not in your company!");
					}
				}
				break;
			case "buyout":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO")){
					player.sendMessage(ChatColor.RED + "Only the CEO can buyout companies!");
					break;
				}
				if (args.length == 1 || args.length > 3){
					help = new Help(plugin, player, tools, db);
					help.buyout();
					break;
				}
				if (args.length == 2){
					DBCompany targetCompany = db.getCompany(args[1]);
					int targetCompanyEmployees = db.getPlayerListInCompany(targetCompany.getName()).size();
					double targetCompanyPrice = config.getBuyoutEmployeePrice() * (double)targetCompanyEmployees;
					
					if (targetCompany.getId() == db.getPlayer(player).getCompanyid()){
						player.sendMessage(ChatColor.RED + "You cannot buyout your own company!");
						break;
					}
					
					if (!targetCompany.getBankrupt()){
						player.sendMessage(ChatColor.RED + "This company is not bankrupt, or on the market!");
						break;
					}
					
					if (economy.getBalance(player.getName()) < targetCompanyPrice){
						player.sendMessage(ChatColor.RED + "This company costs $" + targetCompanyPrice + ". You only have $" + economy.getBalance(player.getName()) + ".");
					} else {
						player.sendMessage(ChatColor.RED + "This company has " + targetCompanyEmployees + " employees, and will cost you $" + targetCompanyPrice + " to buyout.");
						player.sendMessage(ChatColor.DARK_AQUA + "To buyout this company, type /c buyout " + targetCompany.getName() + " confirm");
					}
					break;
				}
				if (args.length == 3 && args[2].equals("confirm")){
					DBCompany targetCompany = db.getCompany(args[1]);
					int targetCompanyEmployees = db.getPlayerListInCompany(targetCompany.getName()).size();
					double targetCompanyPrice = config.getBuyoutEmployeePrice() * (double)targetCompanyEmployees;
					
					if (!targetCompany.getBankrupt()){
						player.sendMessage(ChatColor.RED + "This company is not bankrupt, or on the market!");
						break;
					}
					
					if (targetCompany.getId() == db.getPlayer(player).getCompanyid()){
						player.sendMessage(ChatColor.RED + "You cannot buyout your own company!");
						break;
					}
					
					if (economy.getBalance(player.getName()) < targetCompanyPrice){
						player.sendMessage(ChatColor.RED + "This company costs $" + targetCompanyPrice + ". You only have $" + economy.getBalance(player.getName()) + ".");
					} else {
						List<DBPlayer> targetPlayers = db.getPlayerListInCompany(targetCompany.getName());
						DBCompany company = db.getCompany(db.getPlayer(player.getName()).getCompanyid());
						
						economy.depositPlayer(tools.getCEOInCo(targetCompany).getName(), targetCompanyPrice);
						tools.msgPlayerByName(tools.getCEOInCo(targetCompany).getName(), "Your company was bought out by " + company.getName() + ". You earned $" + targetCompanyPrice);
						
						int targetCompanyID = targetCompany.getId();
						tools.msgPlayersInCo(db.getCompany(targetCompany.getId()).getName(), ChatColor.GOLD + "Your company was bought out. You are now part of " + company.getName() + "!");
						for (DBPlayer player1 : db.getPlayerListInCompany(targetCompany.getName())){
							db.removePlayerFromCompany(plugin, player1.getName());
						}
						db.removeCompany(plugin, targetCompanyID);
						
						for (DBPlayer player2 : targetPlayers){
							db.addPlayerToCompany(plugin, player2, company.getName());
							db.setPlayerInvite(plugin, player2.getName(), "none");
							db.setPlayerRank(plugin, player2, "Employee");
						}
						tools.msgOnlinePlayers(company.getName() + " has bought out " + targetCompany.getName() + "!");
						economy.withdrawPlayer(player.getName(), targetCompanyPrice);
					}
					break;
				}
				
				break;
			case "market":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO") && !db.getPlayer(player).getRank().equals("Leader")){
					player.sendMessage(ChatColor.RED + "Only CEO and Leaders can see the market!");
					break;
				}
				if (args.length != 1){
					help = new Help(plugin, player, tools, db);
					help.market();
					break;
				} else {
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					List<DBCompany> bankruptCompanies = db.getCompanyList();
					
					boolean bankrupt = false;
					
					for (DBCompany bankruptDBCompany : bankruptCompanies){
						if (bankruptDBCompany.getBankrupt()){
							bankrupt = true;
						}
					}
					
					if (bankrupt){
						player.sendMessage(ChatColor.AQUA + "--- "+ "Market" + " ---");
						for (DBCompany bankruptDBCompany : bankruptCompanies){
							if (bankruptDBCompany.getBankrupt()){
								player.sendMessage(ChatColor.DARK_AQUA + " " + bankruptDBCompany.getName());
							}
						}
						player.sendMessage(ChatColor.AQUA + "--- "+ "Market" + " ---");
					} else {
						player.sendMessage(ChatColor.RED + "There are no companies on the market at the moment.");
					}
					
				}
				break;
			case "apps":
			case "applications":
				if (db.getPlayer(player).getRank().equals("none")){
					if (!db.getPlayer(player).getApplication().equals("none"))
						player.sendMessage(ChatColor.AQUA + "You have applied to join " + ChatColor.DARK_AQUA + db.getPlayer(player).getApplication());
					else
						player.sendMessage(ChatColor.AQUA + "You do not have any applications pending.");
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO") && !db.getPlayer(player).getRank().equals("Leader")){
					player.sendMessage(ChatColor.RED + "Only CEO and Leaders can see applications!");
					break;
				}
				if (args.length != 1){
					help = new Help(plugin, player, tools, db);
					help.applications();
					break;
				} else {
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					List<DBPlayer> playersWithApplicationToCompany = new ArrayList<DBPlayer>();
					boolean applicationExists = false;
					
					for (DBPlayer application : db.getPlayerList()){
						if (application.getApplication().equals(dbCompany.getName())){
							playersWithApplicationToCompany.add(application);
							applicationExists = true;
						}
					}
					
					if (applicationExists){
						player.sendMessage(ChatColor.AQUA + "--- "+ "Applications" + " ---");
						for (DBPlayer application : playersWithApplicationToCompany){
							player.sendMessage(ChatColor.DARK_AQUA + " " + application.getName());
						}
						player.sendMessage(ChatColor.AQUA + "--- "+ "Applications" + " ---");
					} else {
						player.sendMessage(ChatColor.RED + "There are no applications.");
					}
				}
				break;
			case "app":
			case "application":
				if (args.length == 2){
					if (args[1].equals("cancel")){
						DBPlayer dbPlayer = db.getPlayer(player.getName());
						if (!dbPlayer.getApplication().equals("none")){
							db.setPlayerApplication(plugin, dbPlayer, "none");
							player.sendMessage(ChatColor.RED + "Your application has been canceled");
						} else {
							
						}
						break;
					}
				}
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!db.getPlayer(player).getRank().equals("CEO") && !db.getPlayer(player).getRank().equals("Leader")){
					player.sendMessage(ChatColor.RED + "Only CEO and Leaders can see applications!");
					break;
				}
				if (args.length == 3){
					if (args[2].equals("accept")){
						String playerName = args[1];
						DBPlayer targetPlayer = db.getPlayer(playerName);
						
						if (targetPlayer.getApplication().equals(db.getCompany(db.getPlayer(player).getCompanyid()).getName())){
							
							String company = targetPlayer.getApplication();
							
							tools.msgPlayerByName(playerName, ChatColor.AQUA + "Your application was accepted!");
							tools.msgPlayerByName(playerName, ChatColor.AQUA + "You joined " + company + "!");
							
							db.addPlayerToCompany(plugin, targetPlayer, company);
							db.setPlayerInvite(plugin, playerName, "none");
							db.setPlayerRank(plugin, targetPlayer, "Employee");
							db.setPlayerApplication(plugin, targetPlayer, "none");
							
							tools.msgOnlinePlayers(ChatColor.AQUA + targetPlayer.getName() + " has been hired by " + company + "!");
						}
						break;
					}
					if (args[2].equals("decline")){
						
						String playerName = args[1];
						DBPlayer targetPlayer = db.getPlayer(playerName);
						
						if (targetPlayer.getApplication().equals(db.getCompany(db.getPlayer(player).getCompanyid()).getName())){
							
							String company = targetPlayer.getApplication();
							
							tools.msgPlayerByName(playerName, ChatColor.AQUA + "Your application to join " + company + " was declined!");
							player.sendMessage(ChatColor.AQUA + "You declined " + playerName + "'s application.");
							
							db.setPlayerApplication(plugin, targetPlayer, "none");
						} else {
							player.sendMessage(ChatColor.RED + playerName + " has not submitted any application for your company.");
						}
						break;
					}
					help = new Help(plugin, player, tools, db);
					help.application();
				}
				if (args.length == 1 || args.length < 3){
					help = new Help(plugin, player, tools, db);
					help.application();
					break;
				}
				break;
			case "apply":
				if (db.getPlayer(player).getApplication().equals("none")){
					if (!db.getPlayer(player).getRank().equals("none")){
						player.sendMessage(ChatColor.RED + "You are already in a company!");
						break;
					}
					
					if (args.length == 2){
						String company = args[1];
						if (db.getCompany(company) == null){
							player.sendMessage(ChatColor.RED + "This company " + company + " does not exist!");
							break;
						}
						
						player.sendMessage(ChatColor.AQUA + "You sent an application to " + company + "!");
						
						db.setPlayerApplication(plugin, db.getPlayer(player), company);
						for (DBPlayer dbplayer : db.getPlayerListInCompany(company)){
							if (dbplayer.getRank().equals("CEO") || dbplayer.getRank().equals("Leader")){
								tools.msgPlayerByName(dbplayer.getName(), ChatColor.AQUA + player.getName() + " has applied to join your company!");
								tools.msgPlayerByName(dbplayer.getName(), ChatColor.AQUA + " /c application " + player.getName() + " accept, or");
								tools.msgPlayerByName(dbplayer.getName(), ChatColor.AQUA + " /c application " + player.getName() + " decline.");
							}
						}
					} else {
						help = new Help(plugin, player, tools, db);
						help.apply();
					}
				} else {
					help = new Help(plugin, player, tools, db);
					help.ERRORapplicationInPlace();
				}
				break;
			case "deposit":
				if (!config.depositEnabled){
					player.sendMessage(ChatColor.RED + "This command has been disabled!");
					break;
				}
				
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (args.length != 2 || !tools.isNumeric(args[1])){
					help = new Help(plugin, player, tools, db);
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
					
					double newValue = oldValue + (Double.parseDouble(args[1]) - (Double.parseDouble(args[1]) * (config.getDepositFee() / 100)));
					
					if (config.isMilestonesEnabled()){
						tools.doMilestones(dbCompany.getName(), oldValue, newValue);
					}
					
					db.setCompanyValue(plugin, dbCompany.getName(), newValue);
					economy.withdrawPlayer(player.getName(), Double.parseDouble(args[1]));
					player.sendMessage(ChatColor.RED + "You deposited $" + args[1] + ", with a fee of " + config.getDepositFee() + "%.");
				}
				break;
			case "sell":
				if (!config.isSellingInvEnabled())
					break;
				if (args.length != 1){
						help = new Help(plugin, player, tools, db);
						help.sell();
						break;
					}
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				
				Inventory inventory = plugin.getServer().createInventory(player, 36, "Sell Items");
			    player.openInventory(inventory);
			    
				break;
			case "fire":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!(db.getPlayer(player).getRank().equals("CEO") || db.getPlayer(player).getRank().equals("Leader"))){
					player.sendMessage(ChatColor.RED + "Only the CEO and Leaders can fire employees!");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player, tools, db);
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
				player.sendMessage(ChatColor.AQUA + "--- Top 10 Companies ---");
				int counter = 1;
				for (DBCompany dbCompany : db.getTopCompanyList()){
					player.sendMessage(ChatColor.DARK_AQUA + "" + counter + ". " + dbCompany.getName() + ChatColor.WHITE + " - $" + tools.round(dbCompany.getValue(), 2));
					counter++;
				}
				player.sendMessage(ChatColor.AQUA + "--- Top 10 Companies ---");
				break;
			case "employees":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				player.sendMessage(ChatColor.AQUA + "--- Your companies' employees ---");
				int employeeCounter = 1;
				for (DBPlayer dbPlayer : db.getOrderedPlayerListInCompany(db.getCompany(db.getPlayer(player).getCompanyid()).getName())){
					player.sendMessage(ChatColor.DARK_AQUA + "" + employeeCounter + ". " + dbPlayer.getName() + ChatColor.WHITE + " - $" + tools.round(dbPlayer.getEarned(), 2));
					employeeCounter++;
				}
				player.sendMessage(ChatColor.AQUA + "--- Your companies' employees ---");
				break;
			case "info":
				if (args.length == 1){
					if (db.getPlayer(player).getRank().equals("none")){
						help = new Help(plugin, player, tools, db);
						help.ERRORnotInCo();
						player.sendMessage(ChatColor.AQUA + "If you were looking for another company, please add company name!");
						break;
					}
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					player.sendMessage(ChatColor.AQUA + "--- " + dbCompany.getName() + " ---");
					if (dbCompany.getBankrupt())
						player.sendMessage(ChatColor.RED + "This company is bankrupt, and available on the market!");
					player.sendMessage(ChatColor.DARK_AQUA + "CEO: "+ChatColor.WHITE + tools.getCEOInCo(dbCompany).getName());
					player.sendMessage(ChatColor.DARK_AQUA + "Description: "+ ChatColor.WHITE + dbCompany.getInfo());
					if (dbCompany.isHiring())
						player.sendMessage(ChatColor.DARK_AQUA + "Hiring: "+ ChatColor.WHITE + "Yes");
					if (!dbCompany.isHiring())
						player.sendMessage(ChatColor.DARK_AQUA + "Hiring: "+ ChatColor.WHITE + "No");
					player.sendMessage(ChatColor.DARK_AQUA + "Value: " +ChatColor.WHITE + "$" + tools.round(dbCompany.getValue(), 2));
					if (tools.getLeadersInCo(dbCompany).size() > 0){
						player.sendMessage(ChatColor.DARK_AQUA + "Leaders: "+ChatColor.WHITE + tools.getLeadersInCo(dbCompany).toString());
					}
					if (tools.getEmployeesInCo(dbCompany).size() > 0){
						player.sendMessage(ChatColor.DARK_AQUA + "Employees: "+ChatColor.WHITE + tools.getEmployeesInCo(dbCompany).toString());
					}
					player.sendMessage(ChatColor.AQUA + "--- " + dbCompany.getName() + " ---");
					
					break;
				}
				if (args.length == 2){
					DBCompany dbCompany = db.getCompany(args[1]);
					if (dbCompany == null){
						player.sendMessage(ChatColor.RED + "A company with the name " + args[1] + " was not found!");
						break;
					}
					
					player.sendMessage(ChatColor.AQUA + "--- " + dbCompany.getName() + " ---");
					if (tools.getCEOInCo(dbCompany) != null)
						player.sendMessage(ChatColor.DARK_AQUA + "CEO: "+ChatColor.WHITE + tools.getCEOInCo(dbCompany).getName());
					player.sendMessage(ChatColor.DARK_AQUA + "Description: "+ ChatColor.WHITE + dbCompany.getInfo());
					player.sendMessage(ChatColor.DARK_AQUA + "Value: "+ChatColor.WHITE +"$"+ Double.toString(dbCompany.getValue()));
					if (tools.getLeadersInCo(dbCompany).size() > 0){
						player.sendMessage(ChatColor.DARK_AQUA + "Leaders: "+ChatColor.WHITE + tools.getLeadersInCo(dbCompany).toString());
					}
					if (tools.getEmployeesInCo(dbCompany).size() > 0){
						player.sendMessage(ChatColor.DARK_AQUA + "Employees: "+ChatColor.WHITE + tools.getEmployeesInCo(dbCompany).toString());
					}
					player.sendMessage(ChatColor.AQUA + "--- " + dbCompany.getName() + " ---");
					
					break;
				}
				break;
			case "pinfo":
				if (args.length == 1){
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					if (db.getPlayer(player).getRank().equals("none")){
						player.sendMessage(ChatColor.AQUA + "--- " + dbPlayer.getName() + " ---");
						player.sendMessage(ChatColor.RED + "You are not in a company!");
						break;
					} else {
						player.sendMessage(ChatColor.AQUA + "--- " + dbPlayer.getName() + " ---");
						player.sendMessage(ChatColor.DARK_AQUA + "Company: " + ChatColor.WHITE + dbCompany.getName());
						player.sendMessage(ChatColor.DARK_AQUA + "Rank: " + ChatColor.WHITE + dbPlayer.getRank());
						player.sendMessage(ChatColor.DARK_AQUA + "Earned: " + ChatColor.WHITE +"$"+ dbPlayer.getEarned());
						player.sendMessage(ChatColor.DARK_AQUA + "Level: " + ChatColor.WHITE + dbPlayer.getLevel());
					}
					break;
				}
				if (args.length == 2){
					DBPlayer dbPlayer = db.getPlayer(player);
					DBCompany dbCompany = db.getCompany(dbPlayer.getCompanyid());
					
					if (!tools.isPlayerInCompany(args[1])){
						player.sendMessage(ChatColor.AQUA + "--- " + args[1] + " ---");
						player.sendMessage(ChatColor.RED + "This player is not in a company!");
						break;
					}

					DBPlayer targetDBPlayer = db.getPlayer(args[1]);
					DBCompany targetDBCompany = db.getCompany(targetDBPlayer.getCompanyid());
					
					player.sendMessage(ChatColor.AQUA + "--- " + targetDBPlayer.getName() + " ---");
					player.sendMessage(ChatColor.DARK_AQUA + "Company: " + ChatColor.WHITE + targetDBCompany.getName());
					player.sendMessage(ChatColor.DARK_AQUA + "Rank: " + ChatColor.WHITE + targetDBPlayer.getRank());
					player.sendMessage(ChatColor.DARK_AQUA + "Earned: " + ChatColor.WHITE +"$"+ targetDBPlayer.getEarned());
					player.sendMessage(ChatColor.DARK_AQUA + "Level: " + ChatColor.WHITE + targetDBPlayer.getLevel());
				}
				break;
			case "accept":
				if (!db.getPlayer(player).getPendingInvite().equals("none")){
					String company = db.getPlayer(player).getPendingInvite();
					player.sendMessage(ChatColor.AQUA + "You joined " + company + "!");
					db.addPlayerToCompany(plugin, db.getPlayer(player), company);
					db.setPlayerInvite(plugin, player.getName(), "none");
					db.setPlayerRank(plugin, db.getPlayer(player), "Employee");
					tools.msgOnlinePlayers(ChatColor.AQUA + player.getName() + " has been hired by " + company + "!");
				} else {
					help = new Help(plugin, player, tools, db);
					help.ERRORnoInvitesPending();
				}
				break;
			case "decline":
				if (!db.getPlayer(player).getPendingInvite().equals("none")){
					player.sendMessage(ChatColor.AQUA + "You declined the invite from " + db.getPlayer(player).getPendingInvite());
					db.setPlayerInvite(plugin, player.getName(), "none");
				} else {
					help = new Help(plugin, player, tools, db);
					help.ERRORnoInvitesPending();
				}
				break;
			case "invite":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
					break;
				}
				if (!(db.getPlayer(player).getRank().equals("CEO") || db.getPlayer(player).getRank().equals("Leader"))){
					player.sendMessage(ChatColor.RED + "Only the CEO or Leaders can invite people to the company");
					break;
				}
				if (args.length != 2){
					help = new Help(plugin, player, tools, db);
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
					help = new Help(plugin, player, tools, db);
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
						tools.msgPlayersInCo(db.getCompany(db.getPlayer(player).getCompanyid()).getName(), ChatColor.AQUA + player.getName() + " has left the company.");
					} else {
						player.sendMessage(ChatColor.RED + "A CEO cannot leave his company! Please disband it, ");
						player.sendMessage(ChatColor.RED + "  or make someone else the new CEO first!");
					}
				} else {
					help = new Help(plugin, player, tools, db);
					help.ERRORnotInCo();
				}
				break;
			case "disband":
				if (db.getPlayer(player).getRank().equals("none")){
					help = new Help(plugin, player, tools, db);
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
					help = new Help(plugin, player, tools, db);
					help.disbandCo();
				}
				break;
			case "create":
				if (tools.isPlayerInCompany(player)){
					player.sendMessage(ChatColor.RED + "You are already in a company!");
					break;
				}
				if (args.length == 2){
					if (economy.getBalance(player.getName()) < config.getCreateCost()){
						player.sendMessage(ChatColor.RED + "You do not have enough money to create a company.");
						break;
					}
					db.insertCompany(plugin, db.getPlayer(player), args[1], "This is the informative description", config.getCreateCost());
					player.sendMessage(ChatColor.AQUA + "--- " + args[1] + " was just created! ---");
					player.sendMessage(ChatColor.GOLD + "  - Description:");
					player.sendMessage(ChatColor.WHITE + db.getCompany(args[1]).getInfo());
					player.sendMessage(ChatColor.GRAY + "This description can be changed by using the following command:");
					player.sendMessage(ChatColor.GRAY + "/c changedesc <text>");
					player.sendMessage(ChatColor.GOLD + "  - Your rank:");
					player.sendMessage(ChatColor.WHITE + "    - " + db.getPlayer(player).getRank());
					player.sendMessage(ChatColor.AQUA + "---");
					db.setPlayerEarned(plugin, player.getName(), db.getPlayer(player).getEarned() + config.getCreateCost());
					tools.doPlayerLevel(db.getPlayer(player));
					tools.msgOnlinePlayers(ChatColor.AQUA + player.getName() + " just created the company " + ChatColor.GOLD + args[1] + "!");
					economy.withdrawPlayer(player.getName(), config.getCreateCost());
				}else{
					help = new Help(plugin, player, tools, db);
					help.createCo();
				}
				break;
			default:
				help = new Help(plugin, player, tools, db);
				help.all();
				break;
			}
		} else {
			help = new Help(plugin, player, tools, db);
			help.all();
		}
	}
}
