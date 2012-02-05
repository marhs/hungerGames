package me.helmetk.hungerGames;

import java.util.logging.Logger;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

public class hungerGames extends JavaPlugin{
	public HungerGame hg;
	private Logger log = Logger.getLogger("Minecraft");
	
    public void onEnable() { 
        getServer().getPluginManager().registerEvents(new HungerListener(this), this);
    	log.info("[Hunger Games] ready!");
    	hg = new HungerGame(this.getServer().getOnlinePlayers(), getServer().getWorld("world").getSpawnLocation().add(0, 2, 0));
    }
    
    public void onDisable() { 
    	log.info("[Hunger Games] has been disabled");

    }
    public HungerGame getHG() {
    	return hg;
    }
    
    public void setHG(HungerGame hg){
    	this.hg = hg;
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
    				for(Player p:getServer().getOnlinePlayers()){
    					p.teleport(getHG().getInicio());
    					hg.getVivos().add(p);
    				}
    				hg.startGame();
    			} else
    			if(args[0].equalsIgnoreCase("stop")) {
    				player.sendMessage("Stopping Hunger Games");
    				getHG().finish();
    			}

    			if(args[0].equalsIgnoreCase("alive")) {
    				if(getHG().isActivo() == false) {
    					player.sendMessage("No hay ningun juego activo");
    				} else {
    					String msg ="";
    					for(Player p:getHG().getVivos()){
    						msg += p.getName() + " ";
    					}
    					player.sendMessage(msg);
    				} 
    			}
   
    		}
    		return true;
    	}
    	
    	return false;
    }


}
