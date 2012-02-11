package spectator;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class spectatorListeners implements Listener {
	public Spectator sp;

	public spectatorListeners (Spectator se){
		sp=se;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

	if (sp.isActivo()) {
	
	if(sp.getSpectated() == null){
		sp.getSpectator().getInventory().clear();
	}
	
	if (sp.getSpectated() == event.getPlayer()) {

	sp.getSpectator().teleport(sp.getSpectated());
	sp.getSpectator().getInventory().clear();
	sp.getSpectator().getInventory().setContents(event.getPlayer().getInventory().getContents());
	sp.getSpectator().getInventory().setArmorContents(event.getPlayer().getInventory().getArmorContents());

	for (Player p : sp.getSpectated().getWorld().getPlayers()) {
	    		 //Destruimos al espectador a todos 
	             sp.getEntidad(p).netServerHandler.sendPacket(new Packet29DestroyEntity(sp.getSpectator().getEntityId()));
	            
	             }
	            //Destruimos al spectated del spectador
	             sp.getEntidad(sp.getSpectator()).netServerHandler.sendPacket(new Packet29DestroyEntity(event.getPlayer().getEntityId()));

	}

	}else {

	if (sp.isActivo()) {

	if (sp.getSpectator() == event.getPlayer()) {

		event.getPlayer().teleport(sp.getSpectated());

		for (Player p : event.getPlayer().getWorld().getPlayers()) {
		//Destruimos a todos el espectator
		sp.getEntidad(p).netServerHandler.sendPacket(new Packet29DestroyEntity(event.getPlayer().getEntityId()));

		}
		
		if(sp.getSpectated()!=null && sp.getSpectated().isOnline())
		sp.getEntidad(sp.getSpectator()).netServerHandler.sendPacket(new Packet29DestroyEntity(sp.getSpectated().getEntityId()));

	}

	}

	}

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {

	if (sp.isActivo()) {
		
		if (sp.getSpectated() == event.getPlayer()) {
			sp.getEntidad(sp.getSpectator()).netServerHandler.sendPacket(new Packet20NamedEntitySpawn((EntityHuman) sp.getSpectated()));
			sp.Next();
		}
	}
	
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {

	if (sp.isActivo()) {
		if (sp.getSpectator() == event.getPlayer() && sp.getSpectated() != null) {
			event.setCancelled(true);

		}
	}

	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {

	if (sp.isActivo()) {
		if (sp.getSpectator() == event.getPlayer() && sp.getSpectated() != null) {
			sp.Next();
		}
	}

	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {

	if (sp.isActivo() != null) {
		if (sp.getSpectator() == event.getPlayer() && sp.getSpectated() != null) {
			event.setCancelled(true);
		}
	}

	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

	if (sp.isActivo() != null) {
		if (sp.getSpectator() == event.getPlayer() && sp.getSpectated() != null) {
			event.setCancelled(true);
		}
	}

	}

	@EventHandler
	public void onEnitityDamage(EntityDamageEvent event) {

	if (event instanceof Player) {
	Player pla = (Player)event.getEntity();
		
		if (sp.isActivo() != null) {
			if (sp.getSpectator() == pla) {
				event.setCancelled(true);
			}
		}
	}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {

	if (event instanceof PlayerDeathEvent) {
		Player pla = (Player)event.getEntity();

		if (sp.isActivo()) {

			if (sp.getSpectated() == pla) {
				sp.getEntidad(sp.getSpectator()).netServerHandler.sendPacket(new Packet20NamedEntitySpawn((EntityHuman)pla));
				sp.Next();
			}
		}
	}

	}
	


}
