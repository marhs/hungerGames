package spectator;

import net.minecraft.server.EntityPlayer;

import org.bukkit.entity.Player;

public interface Spectator {
	
	Player getSpectated();
	
	Player getSpectator();
	
	void setSpectated(Player p);
	
	void setSpectator(Player p);
	
	void Next ();
		
	EntityPlayer getEntidad (Player pl);
	
	Boolean isActivo();
	
	void setActivo(Boolean act);
	
	void SpectatorVisible();
	
}
 