package me.helmetk.hungerGames;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class HungerGame {
	private Logger log = Logger.getLogger("Minecraft");
	private Set<Player> vivos, muertos;
	private boolean activo;
	private Location inicio;
	private Player master;
	private boolean movementAllowed;
	
	// Getters and setters.
	public boolean isMovementAllowed() {
		return movementAllowed;
	}
	
	public void setMovementAllowed(boolean estado) {
		this.movementAllowed = estado;
	}
	public Player getMaster() {
		return master;
	}
	
	public void setMaster(Player player) {
		master = player;
	}
	
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
	
	// Constructor
	public HungerGame(Player[] players, Location inicio) {
		Set<Player> set = new HashSet<Player>();
		Set<Player> set2 = new HashSet<Player>();
		for(Player p:players) {
			set.add(p);
		}
		this.movementAllowed = true;
		this.vivos = set;
		this.muertos = set2;
		this.inicio = inicio;
	}
	
	// Empieza un juego nuevo. 
	public void startGame(Player master, Set<Player> players, Location inicio) {
		setMaster(master);
		setVivos(players);
		setInicio(inicio);
		for(Player p:getVivos()) {
			p.teleport(inicio);
			p.setExp(0);
			p.setLevel(0);
			// TODO Investigar cual es el maximo foodLevel()
			p.setFoodLevel(20);
			p.setHealth(p.getMaxHealth());
			p.getInventory().clear();
		}
		activo = true;
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
		// Mientras esté en desarrollo, esto es == 0, pero deberia ser
		// == 1, ya que debe haber un ganador. Si lo pruebo y estoy yo
		// solo el juego termina justo al empezar.
		return getVivos().size() < 2;
	}
	
	public Player getWinner() {
		/* Da un ganador si y solo si hay un jugador vivo en ese momento, en
		 * caso contrario, devuelve null. */
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
			msg = "[hungerGames] There is no winner";
		else 
			msg = "[hungerGames] The winner is " + getWinner().getName();
		for(Player p:vivos){
			p.sendMessage(msg);
		}
		for(Player p:muertos){
			p.sendMessage(msg);
		}
		setMovementAllowed(true);
		getVivos().addAll(getMuertos());
		getMuertos().clear();
    	getLog().info(msg);
		setActivo(false);
	}
	
	
}
