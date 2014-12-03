package me.Magnusri.DragonBusiness2;

import java.util.logging.Logger;

import me.Magnusri.DragonBusiness2.DBSystem.DBHandler;
import me.Magnusri.DragonBusiness2.commands.CmdExecutor;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DragonBusiness2 extends JavaPlugin{
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public DBHandler db;
	
	public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.logger.info("DragonBusiness2 has been enabled!");
		this.saveDefaultConfig();
		
		setupEconomy();
		setupChat();
		setupPermissions();
		
		db = new DBHandler();
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
			CmdExecutor exec = new CmdExecutor(this, player, cmd, allArgs, db);
			
		} else {
			player.sendMessage("You do not have permission to use this command!");
		}
		return false;
	}
}
