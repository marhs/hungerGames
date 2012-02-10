package me.helmetk.hungerGames;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HungerListener implements Listener{
	Logger log = Logger.getLogger("Minecraft");
	hungerGames plugin;
	public HungerListener(hungerGames plugin) {
		this.plugin = plugin;
	

	}
	
	@EventHandler
	public void onPlayerDeath(EntityDeathEvent event) {
		if(event.getEntity() instanceof Player && plugin.getHG().isActivo()) {
			Player player = (Player)event.getEntity();
			plugin.getHG().muerto(player);
			player.kickPlayer("You has been slain");
			plugin.getMuertosDiarios().add(player);
			PlayerDeathEvent e1=(PlayerDeathEvent) event;
			e1.setDeathMessage("[huntergames] A player has been slain.");
		}
	}
	
	/* Nota de kick:
	 * Para la implementacion que hace kick, no hace falta la lista muertos,
	 * pero ya que planeo que los jugadores se queden en una zona especial 
	 * del mapa (Ànether? Àplataforma? Àthe_end?), voy a mantener as’ la 
	 * implementacion */
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		if(plugin.getHG().isActivo() && !plugin.getHG().getMaster().equals(event.getPlayer())){
			event.getPlayer().kickPlayer("There is a game in progress");
		}
	}
	
	// Elimina de la partida a un jugador cuando abandona el server, que no sea el master.
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(plugin.getHG().getVivos().contains(event.getPlayer())){
			//Mensaje De Salida Del Servidor
			event.setQuitMessage("");
			plugin.getHG().getVivos().remove(event.getPlayer());
			plugin.getMuertosDiarios().add(event.getPlayer());
			
			//Rayo en spawn
			plugin.getServer().getWorld("hgWorld").strikeLightning(plugin.getServer().getWorld("hgWorld").getSpawnLocation());
		}
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		if(plugin.getHG().getVivos().contains(event.getPlayer())){
			//Mensaje De Salida Del Servidor
			event.setLeaveMessage("");
		}
	}
	
	// Si Movement Allowed esta en false, te permite mirar, pero no moverte.
	@EventHandler 
    public void onPlayerMove(PlayerMoveEvent event) {
		if(!plugin.getHG().isMovementAllowed()){
			Location destino = new Location(event.getFrom().getWorld(), 
											event.getFrom().getX(), 
											event.getFrom().getY(), 
											event.getFrom().getZ(), 
											event.getTo().getYaw(), 
											event.getTo().getPitch());
			
			event.setTo(destino);
			return;
		}
	}
	
	//Listener para el EventTimeDawn
	
	@EventHandler
	public void onCustomEvent(EventTimeDawn event){
		if(plugin.getHG().isActivo()){
		if(event instanceof EventTimeDawn){
			if( !plugin.getMuertosDiarios().isEmpty() ){
			String e = "";
			for(Player p:plugin.getMuertosDiarios()){
				e += " "+p.getName();
			}
			plugin.getMuertosDiarios().clear();
			plugin.broadcast("Los jugadores muertos hoy son:" +e);
			}
		}
		}
	}
}
