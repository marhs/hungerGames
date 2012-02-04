package me.helmetk.hungerGames;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HungerGame {
	private Logger log = Logger.getLogger("Minecraft");
	Set<Player> vivos, muertos;
	boolean activo;
	Location inicio;
	
	public Set<Player> getVivos() {
		return vivos;
	}

	public void setVivos(Set<Player> vivos) {
		this.vivos = vivos;
	}

	public Set<Player> getMuertos() {
		return muertos;
	}

	public void setMuertos(Set<Player> muertos) {
		this.muertos = muertos;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Location getInicio() {
		return inicio;
	}

	public void setInicio(Location inicio) {
		this.inicio = inicio;
	}

	public Logger getLog() {
		return log;
	}

	public HungerGame(Player[] players, Location inicio) {
		Set<Player> set = new HashSet<Player>();
		Set<Player> set2 = new HashSet<Player>();
		for(Player p:players) {
			set.add(p);
		}
		this.vivos = set;
		this.muertos = set2;
		this.inicio = inicio;
	}
	
	public void startGame() {
		for(Player p:getVivos()) {
			p.chat("Soy " + p.getName() + " y voy a jugar a los Hunger Games!");
		}
		activo = true;
		// Set Jugadores en inicio.
	}
	public void muerto(Player player){
		if(isActivo() == true){
			getVivos().remove(player);
			getMuertos().add(player);
			if(isFinished())
				finish();
		}

	}
	public boolean isFinished() {
		return getVivos().size() == 0;
	}
	
	public Player getWinner() {
		Player res = null;
		if(getVivos().size() > 1)
			return res;
		for(Player p:getVivos()){
			res = p;
		}
		return res;
	}
	public void finish() {
		String msg;
		if(getWinner() == null)
			msg = "No hay ganador en esta edicion";
		else 
			msg = "El ganador es" + getWinner().getName();
		for(Player p:muertos){
			p.sendMessage(msg);
		}
		getVivos().addAll(getMuertos());
		getMuertos().clear();
    	getLog().info(msg);
		setActivo(false);
	}
	
	
}