package spectator;

import java.util.List;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet20NamedEntitySpawn;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SpectatorImpl implements Spectator{
	
	private Player spectator;
	private Player spectated;
	private Boolean activo;
	
	
	
	public SpectatorImpl (Player p){
		setSpectator(p);
		
	}

	public Player getSpectated() {
		return spectated;
	}

	public void setSpectated(Player spectated) {
		this.spectated = spectated;
	}

	public Player getSpectator() {
		return spectator;
	}

	public void setSpectator(Player spectator) {
		this.spectator = spectator;
	}

	public EntityPlayer getEntidad (Player p){
		Entity entidad=(Entity)p;
		net.minecraft.server.Entity result=((CraftEntity)entidad).getHandle();
		return (EntityPlayer)result;
	}

	@Override
	public Boolean isActivo() {
		return activo;
	}
	
	public void setActivo(Boolean act){
		activo=act;
	}


	public void Next() {
		List<Player> players= spectated.getWorld().getPlayers();
		if(spectated == null)spectated=siguiente(0,players);
		int pos= players.indexOf(spectated);
		spectated=siguiente(pos+1,players);
	}

	private Player siguiente (int pos,List<Player> li){
		if(li.size() == pos)pos=0;
		if(li.get(pos) == spectator || li.get(pos).isOnline() == false)
			return siguiente(pos+1,li);
		
		return li.get(pos);
	}
	
	@Override
	public void SpectatorVisible() {
		for(Player p:spectator.getWorld().getPlayers()){
			getEntidad(p).netServerHandler.sendPacket(new Packet20NamedEntitySpawn((EntityHuman) spectator));
		}
		getEntidad(spectator).netServerHandler.sendPacket(new Packet20NamedEntitySpawn((EntityHuman) spectated));
		
	}
	
	
	
}
