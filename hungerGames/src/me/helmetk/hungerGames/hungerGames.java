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
    			// Aqui se inicia los Hunger Games
    			if(args[0].equalsIgnoreCase("start") && args.length == 1){
    				player.sendMessage("Starting Hunger Games");
    				for(Player p:getServer().getOnlinePlayers()){
    					p.teleport(getHG().getInicio());
    					hg.getVivos().add(p);
    				}
    				hg.startGame();
    			} else
    			// Con esto se termina el juego.
    			if(args[0].equalsIgnoreCase("stop") && args.length == 1) {
    				player.sendMessage("Stopping Hunger Games");
    				getHG().finish();
    			} else
    			// Te permite sabes si hay algœn juego activo, y los jugadores que quedan vivos.
    			// TODO Hay que hacer que s—lo el master pueda ver a los jugadores activos.
    			if(args[0].equalsIgnoreCase("alive") && args.length == 1) {
    				if(getHG().isActivo() == false) {
    					player.sendMessage("No hay ningun juego activo");
    				} else {
    					String msg ="";
    					for(Player p:getHG().getVivos()){
    						msg += p.getName() + " ";
    					}
    					player.sendMessage(msg);
    				} 
    			} else {
    				player.sendMessage("HungerGames start/stop/alive");
    			}
   
    		}
    		return true;
    	}
    	
    	return false;
    }


}
