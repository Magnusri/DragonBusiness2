package me.Magnusri.DragonBusiness2.EventHandlers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import me.Magnusri.DragonBusiness2.DragonBusiness2;
import me.Magnusri.DragonBusiness2.commands.Config;
import me.Magnusri.DragonBusiness2.commands.Tools;

public class InventoryClosedHandler implements Listener {

	private DragonBusiness2 plugin;
	
	public InventoryClosedHandler(DragonBusiness2 dragonbusiness){
		this.plugin = dragonbusiness;
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
	public void onInventoryClose(InventoryCloseEvent event) {
		//ERROR CHECKS
		if (!(event.getPlayer() instanceof Player)) {
			return;
		}
		Player localPlayer = (Player)event.getPlayer();
		Inventory localInventory = localPlayer.getInventory();
		if (event.getInventory() == null) {
			return;
		}
		if (!event.getInventory().getTitle().equals("Sell Items")){
			return;
		}
		
		//ACTUAL SALES AND INVENTORY HANDLING BELOW
		String[] priceList = plugin.config.getPricelist();
		
		boolean itemsSold = false;
		double income = 0.0;
		
		for (int j = 0; j < event.getInventory().getSize(); j++){
			if (event.getInventory().getItem(j) != null){
				for (int i = 0; i < priceList.length; i++){
					if (priceList[i].split("-")[0].equals(Integer.toString(event.getInventory().getItem(j).getTypeId()))){
						itemsSold = true;
						
						int amount = event.getInventory().getItem(j).getAmount();
						double unitPrice = Double.parseDouble(priceList[i].split("-")[1]) * amount;
						
						income += unitPrice;
						
						//event.getInventory().remove(event.getInventory().getItem(j));
						
						//event.getInventory().getItem(j) THIS STACK HAS BEEN CLEARED FOR SALE. CALC PRICE, AND SELL.
						
					}
					if (!priceList[i].split("-")[0].equals(Integer.toString(event.getInventory().getItem(j).getTypeId()))){
						plugin.getServer().getPlayer(event.getPlayer().getName()).getWorld().dropItem(event.getPlayer().getLocation(), event.getInventory().getItem(j));
						//event.getInventory().remove(event.getInventory().getItem(j));
					}
				}
			}
		}
		if (itemsSold){
			localPlayer.sendMessage(ChatColor.GREEN + "Items were sold!");
			plugin.tools.playerIncome(localPlayer, income);
		} else {
			localPlayer.sendMessage("No items could be sold!");
		}
		event.getInventory().clear();
	}
}


























