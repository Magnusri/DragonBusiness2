package me.Magnusri.DragonBusiness2;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandInterface {
	public ArrayList<String> list;
	public Commands cmd;
	public Plugin plugin;
	
	public CommandInterface(Plugin plugin){
		this.plugin = plugin;
	}
	
	public class Commands{
		public boolean execute(String command, Player sender, String[] allArgs){
			ArrayList<String> args = (ArrayList<String>) Arrays.asList(allArgs);
			args.remove(0);
			if (tryCommand(command, sender)){
				switch (command){
				case "help":
					
				break;
				default:
					sender.sendMessage("Error Executing command! Contact developer with this info:");
					sender.sendMessage(" -: Error Executing Command: " + command);
				break;
				}
				return true;
			} else {
				return false;
			}
		}
		
		public boolean tryCommand(String command, Player sender){
			if (list.toString().contains(command)){
				return true;
			} else {
				//CHANGE TO GET COMMAND-ERROR-TEXT FROM .cfg FILE
				sender.sendMessage("Plugin error: Command not found!");
				return false;
			}
		}
	}
	
	public ArrayList<String> getList(){
		return list;
	}
}
