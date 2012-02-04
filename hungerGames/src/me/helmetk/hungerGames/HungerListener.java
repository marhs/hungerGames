package me.helmetk.hungerGames;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class HungerListener implements Listener{
	Logger log = Logger.getLogger("Minecraft");
	hungerGames plugin;
	public HungerListener(hungerGames plugin) {
		this.plugin = plugin;

	}
	@EventHandler
	public void onPlayerDeath(EntityDeathEvent event) {
		if(event.getEntity() instanceof Player && plugin.getHG().isActivo() == false){
			Player player = (Player)event.getEntity();
			plugin.getHG().muerto(player);
			for(Player p:plugin.getServer().getOnlinePlayers()){
				p.sendMessage("Ha muerto un jugador");
			}
			player.kickPlayer("Has muerto");
		}
	}
	
	/* Nota de kick:
	 * Para la implementaci—n que hace kick, no hace falta la lista muertos,
	 * pero ya que planeo que los jugadores se queden en una zona especial 
	 * del mapa (Ànether? Àplataforma? Àthe_end?), voy a mantener as’ la 
	 * implementacion */
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		if(plugin.getHG().isActivo() == true){
			event.getPlayer().kickPlayer("Hay un juego activo, no puedes entrar hasta que termine");
		}
	}
	@EventHandler
	public void onMasterPlaceBlock(BlockPlaceEvent event) {
		if(!plugin.getHG().isActivo() && 
			event.getBlockPlaced().getTypeId() == 19 && 
			event.getPlayer().equals(plugin.getHG().getMaster())){
			plugin.getHG().addInicio(event.getBlock().getLocation().add(0, 1, 0));
		}
	}
	@EventHandler
	public void onMasterRemoveBlock(BlockBreakEvent event) {
		if(!plugin.getHG().isActivo() && 
			event.getBlock().getTypeId() == 19 && 
			event.getPlayer().equals(plugin.getHG().getMaster())){
			plugin.getHG().removeInicio(event.getBlock().getLocation().add(0, 1, 0));
		}
	}
	
}
