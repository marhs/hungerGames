package me.helmetk.hungerGames;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
		if(event.getEntity() instanceof Player){
			plugin.hg.muerto((Player)event.getEntity());
			log.info("Ha muerto un " + event.getEntity().toString());
		}
	}
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		if(plugin.hg.activo == true){
			event.getPlayer().kickPlayer("Hay un juego activo, no puedes entrar hasta que termine");
		}
	}
	
}
