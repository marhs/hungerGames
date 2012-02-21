package me.helmetk.hungerGames;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


class EventTimeDawn extends Event{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private static final HandlerList handlers = new HandlerList();
    
    enum TipoEvent{Muertes,Regalos};
    private TipoEvent tipo;
    
    public EventTimeDawn (TipoEvent te){
    	super();
    	setTipo(te);
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }

	public TipoEvent getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvent tipo) {
		this.tipo = tipo;
	}
    
    
}