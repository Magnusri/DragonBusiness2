package me.Magnusri.DragonBusiness2;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DragonBusiness2 extends JavaPlugin{
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.logger.info("DragonBusiness2 has been enabled!");
		this.saveDefaultConfig();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		this.logger.info("DragonBusiness2 has been disabled!");
	}
	
	public void showHelp(Player player){
		player.sendMessage("Commands:");
		player.sendMessage(" - /company help");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		if (commandLabel.equalsIgnoreCase("company") && player.hasPermission("DragonBusiness2.player")){
			player.sendMessage("Command sent!");
		} else {
			player.sendMessage("You do not have permission to use this command!");
		}
		return false;
	}
}
