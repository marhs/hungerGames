package me.helmetk.hungerGames;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


class EventTimeDawn extends Event{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private static final HandlerList handlers = new HandlerList();
    
    public EventTimeDawn (){
    	super();
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    
}