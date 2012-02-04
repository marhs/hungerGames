package me.helmetk.hungerGames;

import java.util.logging.Logger;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

public class hungerGames extends JavaPlugin{
	
	Logger log = Logger.getLogger("Minecraft");
	
    public void onEnable() { 
    	log.info("[Hunger Games] ready!");
    }
     
    public void onDisable() { 
    	log.info("[Hunger Games] has been disabled");

    }
    

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	
    	Player player = (Player) sender;
    	if(cmd.getName().equalsIgnoreCase("hungerGames")){
    		if(args.length == 0) {
    			// s
    			player.sendMessage("Hunger Games, v0.1 - To start a new game, use /hungerGames start");
    		} else 
    		if(args.length != 0) {
    			if(args[0].equalsIgnoreCase("start")){
    				player.sendMessage("Starting Hunger Games");
    				HungerGame hg = new HungerGame(this.getServer().getOnlinePlayers());
    				hg.startGame();
    				hg.finish();
    			} else
    			if(args[0].equalsIgnoreCase("stop")) {
    				player.sendMessage("Stopping Hunger Games");
    			}
    		}
    		return true;
    	}
    	
    	return false;
    }


}
