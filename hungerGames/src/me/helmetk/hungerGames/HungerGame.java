package me.helmetk.hungerGames;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import spectator.Spectator;
import spectator.SpectatorImpl;
import spectator.spectatorListeners;


public class HungerGame {
	private Logger log = Logger.getLogger("Minecraft");
	private Set<Player> vivos, muertos;
	private boolean activo;
	private Location inicio;
	private Player master;
	private Boolean masterPlays;
	private boolean movementAllowed;
	private Spectator espectador;
	private Plugin plugin;
	
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
	// Esto determina si el master va a jugar la partida o no va a hacerlo.
	public Boolean getMasterPlays() {
		return masterPlays;
	}

	public void setMasterPlays(Boolean masterPlays) {
		this.masterPlays = masterPlays;
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
	public void startGame(Player master, Set<Player> players, Location inicio, Boolean masterPlays) {
		setMaster(master);
		setVivos(players);
		setInicio(inicio);
		for(Player p:getVivos()) {
			//p.teleport(inicio);
			p.setExp(0);
			p.setLevel(0);
			// TODO Investigar cual es el maximo foodLevel()
			p.setFoodLevel(20);
			p.setHealth(p.getMaxHealth());
			p.getInventory().clear();
		}
		espectador=new SpectatorImpl(master);
		plugin.getServer().getPluginManager().registerEvents(new spectatorListeners(espectador), plugin);
		//espectador.setSpectated(master);
		//espectador.Next();
		espectador.setActivo(true);		
		activo = true;
		this.masterPlays = masterPlays;
	}
	
	public Spectator getEspectador (){
		return espectador;
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
		master.sendMessage(msg);
		
		setMovementAllowed(true);
		getVivos().addAll(getMuertos());
		getMuertos().clear();
    	getLog().info(msg);
		setActivo(false);
		espectador.setActivo(false);
		espectador.SpectatorVisible();
	}
	
	
}
