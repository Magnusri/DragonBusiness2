package me.Magnusri.DragonBusiness2.EventHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import me.Magnusri.DragonBusiness2.DragonBusiness2;

public class InventoryClosedHandler implements Listener {

	private DragonBusiness2 dragonbusiness;
	
	public InventoryClosedHandler(DragonBusiness2 dragonbusiness){
		this.dragonbusiness = dragonbusiness;
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
	public void onInventoryClose(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player)) {
			return;
		}
		Player localPlayer = (Player)event.getPlayer();
		Inventory localInventory = localPlayer.getInventory();
		if (localInventory == null) {
			return;
		}
		if (!event.getInventory().getTitle().equals("Sell Items")){
			return;
		}
		
		Player player = (Player) event.getPlayer();
		player.sendMessage("Items has been sold!");
		
	}
}
