package me.helmetk.hungerGames;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class hungerGames extends JavaPlugin{
	public HungerGame hg;
	private Logger log = Logger.getLogger("Minecraft");
	Set<Player> muertosDiarios = new HashSet<Player>();
	//World world1 = getServer().getWorld("world");
	BukkitScheduler tareas;
	Boolean mensajeDelDia = false;
	
	public Boolean getMensajeDelDia(){
		return mensajeDelDia;
	}
	
	public void setMensajeDelDia(Boolean bol){
		mensajeDelDia = bol;
	}
	
    public Set<Player> getMuertosDiarios() {
		return muertosDiarios;
	}

	public void setMuertosDiarios(Set<Player> muertosDiarios) {
		this.muertosDiarios = muertosDiarios;
	}


	public void onEnable() { 
        //Activa Listeners
		getServer().getPluginManager().registerEvents(new HungerListener(this), this);
		//Crea Tareas
		tareas = getServer().getScheduler();
		
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
    	/* El nombre del comando es "hungerGames", que si se escribe solo /hungerGames
    	 * te devuelve la descripcion del plugin, despues definimos los comandos como
    	 * por ejemplo "/hungerGames start" */
    	if(cmd.getName().equalsIgnoreCase("hungerGames")){
    		if(args.length == 0) {
    			// s
    			player.sendMessage("Hunger Games, v0.1 - To start a new game, use /hungerGames start");
    		} else 
    		if(args.length != 0) {
    			
    			// Comando "start": Aqui se inicia los Hunger Games
    			if(args[0].equalsIgnoreCase("start") && args.length == 1){
    				if(!getHG().isActivo()) {
    					
 
    					//Actica tarea de control del tiempo
    					 tareas.scheduleAsyncRepeatingTask(this, new Runnable() {
    							@Override
    							public void run() {
    								Long Hora=getServer().getWorld("world").getTime();
    								
    								if( Hora == 18000 && getMensajeDelDia() == false){
    									getServer().getPluginManager().callEvent(new EventTimeDawn());
    								}
    								if( getMensajeDelDia() == true && Hora >0 && Hora < 1000){
    									setMensajeDelDia(false);
    								}
    							}
    						}, 0, 1);
    					 
    					broadcast("Starting new game");
    					broadcast("El juego empieza en 10 segundos");
    					Set<Player> jug = new HashSet<Player>();
    					for(Player p:getServer().getOnlinePlayers()){
    						jug.add(p);
    					}
    					getHG().startGame(player, jug, getServer().getWorld("world").getSpawnLocation().add(0, 10, 0));
    					broadcast("The master is " + getHG().getMaster().getName());
    					getHG().setMovementAllowed(false);
    					getServer().getWorld("world").setPVP(false);
    					// Tarea retrasada, el tiempo (200L) viene dado en ticks, hay 20 ticks por segundo.
    					getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    						public void run() {
    							getHG().setMovementAllowed(true);
    	    					getServer().getWorld("world").setPVP(true);
    							broadcast("Adelante!");
    						}
    					}, 200L);
    					
    				} else {
    					player.sendMessage("[hungerGames] Ya hay un juego en marcha");
    				}
    			} else
    				
    			// Comando "stop": Con esto se termina el juego.
    			if(args[0].equalsIgnoreCase("stop") && args.length == 1 && player.equals(getHG().getMaster())) {
    				broadcast("Stopping Hunger Games");
    				getHG().finish();
    			} else
    				
    			// Comando "status": Te permite sabes si hay algœn juego activo, y los jugadores que quedan vivos.
    			if(args[0].equalsIgnoreCase("status") && args.length == 1) {
    				if(getHG().isActivo() == false) {
    					player.sendMessage("No hay ningun juego activo");
    				} else {
    					player.sendMessage("El juego esta en marcha");
    					if(player.equals(getHG().getMaster())){
    						String msg ="Vivos: ";
    						for(Player p:getHG().getVivos()){
    							msg += p.getName() + " ";
    						}
    						player.sendMessage(msg);
    					}
    				} 
    			} else {
    				player.sendMessage("HungerGames start/stop/alive");
    			}
   
    		}
    		return true;
    	}
    	
    	return false;
    }
    
    public void broadcast(String msg){
    	for(Player p:getServer().getOnlinePlayers()){
    		p.sendMessage("[hungerGames] " + msg);
    	}
    	log.info("[hungerGames] " + msg);
    }

}

class EventTimeDawn extends Event{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private static final HandlerList handlers = new HandlerList();
    
    public EventTimeDawn (){
    	super();
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    
}
