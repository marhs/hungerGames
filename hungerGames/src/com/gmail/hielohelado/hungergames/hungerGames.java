package com.gmail.hielohelado.hungergames;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class hungerGames extends JavaPlugin{
	
	Logger log = Logger.getLogger("Minecraft");
	
    public void onEnable() { 
    	log.info("Hunger Games ready!");
    }
     
    public void onDisable() { 
    	log.info("Hunger Games has been disabled");

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	return false;
    }
}
