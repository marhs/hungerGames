package me.helmetk.hungerGames;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

public class hungerGames extends JavaPlugin{
	public HungerGame hg;
	private Logger log = Logger.getLogger("Minecraft");
	
    public void onEnable() { 
        getServer().getPluginManager().registerEvents(new HungerListener(this), this);
    	log.info("[Hunger Games] ready!");
    	Map<Location, Player> locations = new HashMap<Location, Player>();
    	hg = new HungerGame(this.getServer().getOnlinePlayers(), locations);
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
    			if(args[0].equalsIgnoreCase("prepare")){
    				hg.setMaster(player);
    			} else
    			if(args[0].equalsIgnoreCase("start")){
    				player.sendMessage("Starting Hunger Games");
    				for(Player p:getServer().getOnlinePlayers()){
    					Set<Location> loc = getHG().getInicio().keySet();
    					for(Location l:loc) {
    						if(getHG().getInicio().get(l) == null)
    							getHG().getInicio().put(l, p);
    							p.teleport(l);
    					}
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
