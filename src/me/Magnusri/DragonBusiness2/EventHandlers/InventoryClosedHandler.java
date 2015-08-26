package me.Magnusri.DragonBusiness2.EventHandlers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
		
		for (int i = 0; i < event.getInventory().getSize(); i++){
			if (event.getInventory().getItem(i) != null){
				boolean giveback = false;
				boolean stackSold = false;
				ItemStack stack = event.getInventory().getItem(i);
				for (int j = 0; j < priceList.length; j++){
					if (stack.getTypeId() == Integer.parseInt(priceList[j].split("-")[0])){
						itemsSold = true;
						stackSold = true;
						giveback = false;
						
						int amount = event.getInventory().getItem(i).getAmount();
						double unitPrice = Double.parseDouble(priceList[j].split("-")[1]) * amount;
						
						income += unitPrice;
					}
					if (!(stack.getTypeId() == Integer.parseInt(priceList[j].split("-")[0]))){
						if (!stackSold)
							giveback = true;
					}
				}
				if (giveback)
					plugin.getServer().getPlayer(event.getPlayer().getName()).getWorld().dropItem(event.getPlayer().getLocation(), stack);
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


























