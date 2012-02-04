package me.helmetk.hungerGames;

import java.util.*;

import org.bukkit.entity.Player;

public class HungerGame {
	Set<Player> vivos, muertos;
	boolean activo;
	
	public HungerGame(Player[] players) {
		Set<Player> set = new HashSet<Player>();
		Set<Player> set2 = new HashSet<Player>();
		for(Player p:players) {
			set.add(p);
		}
		this.vivos = set;
		this.muertos = set2;
	}
	
	public void startGame() {
		for(Player p:vivos) {
			p.chat("Soy " + p.getName() + " y voy a jugar a los Hunger Games!");
		}
		activo = true;
		// Set Jugadores en inicio.
	}
	public void muerto(Player player){
		if(activo == true){
			vivos.remove(player);
			muertos.add(player);
			if(isFinished())
				finish();
		}

	}
	public boolean isFinished() {
		return vivos.size() == 0;
	}
	
	public Player getWinner() {
		Player res = null;
		if(vivos.size() > 1)
			return res;
		for(Player p:vivos){
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
		vivos.addAll(muertos);
		muertos.clear();
		activo = false;
	}
	
	
}
