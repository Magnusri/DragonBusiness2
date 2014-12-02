package me.Magnusri.DragonBusiness2;

import java.util.logging.Logger;

import me.Magnusri.DragonBusiness2.commands.CmdExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DragonBusiness2 extends JavaPlugin{
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public DBConnection DBcon;
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.logger.info("DragonBusiness2 has been enabled!");
		this.saveDefaultConfig();
		
		DBcon = new DBConnection();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		this.logger.info("DragonBusiness2 has been disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		String[] allArgs;
		if (args.length != 0)
			allArgs = args;
		else
			allArgs = new String[]{"help"};
		if (cmd.getName().equalsIgnoreCase("company") && player.hasPermission("DragonBusiness2.player")){
			
			player.sendMessage("Command sent!");
			CmdExecutor exec = new CmdExecutor(this, player, cmd, args);
			
		} else {
			player.sendMessage("You do not have permission to use this command!");
		}
		return false;
	}
}
