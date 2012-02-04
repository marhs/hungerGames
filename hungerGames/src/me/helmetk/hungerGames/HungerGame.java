package me.helmetk.hungerGames;

import java.util.*;

import org.bukkit.entity.Player;

public class HungerGame {
	Set<Player> vivos, muertos;
	boolean activo;
	
	public HungerGame(Player[] players) {
		Set<Player> set = new HashSet<Player>();
		for(Player p:players) {
			set.add(p);
		}
		this.vivos = set;
	}
	
	public void startGame() {
		for(Player p:vivos) {
			p.chat("Soy " + p.getName() + " y voy a jugar a los Hunger Games!");
		}
		activo = true;
		// Set Jugadores en inicio.
	}
	public void muerto(Player player){

	}
	public boolean isFinished() {
		return vivos.size() == 1;
	}
	
	public Player getWinner() {
		Player res = null;
		for(Player p:vivos){
			res = p;
		}
		return res;
	}
	public void finish() {
		if(isFinished())
			getWinner().sendMessage("El ganador es " + getWinner().getName());
		activo = false;
	}
	
	
}
