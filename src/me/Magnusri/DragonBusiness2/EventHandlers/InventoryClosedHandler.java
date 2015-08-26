package me.Magnusri.DragonBusiness2.EventHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import me.Magnusri.DragonBusiness2.DragonBusiness2;
import me.Magnusri.DragonBusiness2.commands.Config;

public class InventoryClosedHandler implements Listener {

	private DragonBusiness2 plugin;
	private Config config;
	
	public InventoryClosedHandler(DragonBusiness2 dragonbusiness, Config config){
		this.plugin = dragonbusiness;
		this.config = config;
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
	public void onInventoryClose(InventoryCloseEvent event) {
		//ERROR CHECKS
		if (!(event.getPlayer() instanceof Player)) {
			return;
		}
		Player localPlayer = (Player)event.getPlayer();
		Inventory localInventory = localPlayer.getInventory();
		if (localInventory == null) {
			return;
		}
		if (!localInventory.getTitle().equals("Sell Items")){
			return;
		}
		
		//ACTUAL SALES AND INVENTORY HANDLING BELOW
		
		
		
		localPlayer.sendMessage("Items has been sold!");
	}
}


























