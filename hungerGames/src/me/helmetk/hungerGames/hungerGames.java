package me.helmetk.hungerGames;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.command.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.entity.Player;


public class hungerGames extends JavaPlugin{
	public HungerGame hg;
	private Logger log = Logger.getLogger("Minecraft");
	private Set<Player> muertosDiarios = new HashSet<Player>();
	private World world1 ;
	private BukkitScheduler tareas;
	private Boolean mensajeDelDia = false;
	private Boolean preparado=false;
	
	public Map<Player, ItemStack[]> Inventarios = new HashMap<Player, ItemStack[]>();
	public Map<Player, ItemStack[]> Armaduras = new HashMap<Player, ItemStack[]>();
	public HashMap<Player, Location> origLocation = new HashMap<Player, Location>();
	public Map<Player, Integer> Comida = new HashMap<Player, Integer>();
	public Map<Player, Integer> Vida = new HashMap<Player, Integer>();
	public Map<Player, Float> Exps = new HashMap<Player, Float>();
	
	public Boolean getMensajeDelDia(){
		return mensajeDelDia;
	}
	
	public void setMensajeDelDia(Boolean bol){
		mensajeDelDia = bol;
	}
	
    public Set<Player> getMuertosDiarios() {
		return muertosDiarios;
	}

	public void setMuertosDiarios(Set<Player> muertosDiarios) {
		this.muertosDiarios = muertosDiarios;
	}


	public void onEnable() { 
        //Activa Listeners
		getServer().getPluginManager().registerEvents(new HungerListener(this), this);
		//Crea Tareas
		tareas = getServer().getScheduler();
		
		if(getServer().getWorld("hgWorld") == null){
			getServer().getLogger().info("[hungerGames] Creating world");
			WorldCreator wcreator = new WorldCreator("hgWorld");
			wcreator.environment(Environment.NORMAL);
			wcreator.type(WorldType.NORMAL);
			getServer().createWorld(wcreator);
		}
		
		if(getServer().getWorld("hgWorld") != null){
			world1 = getServer().getWorld("hgWorld");
		}
		
		log.info("[Hunger Games] ready!");
    	hg = new HungerGame(getServer().getWorld("world").getSpawnLocation().add(0, 2, 0));
    }
    
    public void onDisable() { 
    	log.info("[Hunger Games] has been disabled");

    }
    public HungerGame getHG() {
    	return hg;
    }
    
    public void setHG(HungerGame hg){
    	this.hg = hg;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	
    	// Check if the sender is a player
    	if(!(sender instanceof Player))
    		log.info("[hungerGames] This command can't be executed in console");
    	else {
    	/* El nombre del comando es "hungerGames", que si se escribe solo /hungerGames
    	 * te devuelve la descripcion del plugin, despues definimos los comandos como
    	 * por ejemplo "/hungerGames start" */
    	if(cmd.getName().equalsIgnoreCase("hungerGames")){
    		Player player = (Player) sender;
    		if(args.length == 0) {
    			// s
    			player.sendMessage("Hunger Games, v0.1 - To start a new game, use /hungerGames start");
    		} else 
    		if(args.length != 0) {
    			
    			// Comando "start": Aqui se inicia los Hunger Games
    			if(args[0].equalsIgnoreCase("start") && args.length == 1){
    				
    				if(!getHG().isActivo()) {
    					startGame(player, false);
    					return true;
    				} else {
    					player.sendMessage("[hungerGames] There is a game in progress, use stop.");
    				}
    			} else if(args[0].equalsIgnoreCase("start") && args[1].equalsIgnoreCase("nomaster") && args.length == 2) {
    				if(!getHG().isActivo()) {
    					startGame(player, true);
    					return true;
    				} else {
    					player.sendMessage("[hungerGames] There is a game in progress, use stop.");
    				}
    			} else
    				
    			// Comando "stop": Con esto se termina el juego.
    			if(args[0].equalsIgnoreCase("stop") && args.length == 1 && player.equals(getHG().getMaster())) {
    				//Comprueba si ha sido inciado algun juego
    					if(getHG().getMaster() != null){
    					//Finalizael juego si no esta finalizado
    					broadcast("Stopping Hunger Games");
    					if(getHG().isActivo()){
    						getHG().finish();
    					}
    					broadcast("Waiting for all players reconnect ...");
    					//Espera a que todos los jugadores se reconecten y esten vivos.
    					tareas.scheduleAsyncDelayedTask(this, new Runnable() {
							//Tarea para esperar a los jugadores
							public void run() {
								boolean reconectados=true;
		    					while(reconectados){
		    						boolean b1=true;
		    						for(Player p:getHG().getVivos()){
		    							b1 &= !p.isDead();
		    						}
		    						if(b1)reconectados=false;
		    						try {
										Thread.sleep(10);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
		    					}
		    					broadcast("Start teleport come back ...");
		    					//Teletransporte al otromundo
		    					resetPlayers();
		    					getHG().setMaster(null);
		    					broadcast("Huger Games Stopped !");
								
							}
						});
    					
    					return true;
    				}else{
    					broadcast("There isn't a game started");
    					return false;
    				}
    			} else
    				
    			// Comando "status": Te permite sabes si hay algœn juego activo, y los jugadores que quedan vivos.
    			if(args[0].equalsIgnoreCase("status") && args.length == 1) {
    				if(getHG().isActivo() == false) {
    					player.sendMessage("There is no game in progress");
    					return true;
    				} else {
    					player.sendMessage("There is a game in progress");
    					if(player.equals(getHG().getMaster())){
    						String msg ="Alive: ";
    						for(Player p:getHG().getVivos()){
    							msg += p.getName() + " ";
    						}
    						player.sendMessage(msg);
    						return true;
    					}
    				} 
    			} else 
    				
    			if(args[0].equalsIgnoreCase("prepare") && args.length == 1) {
    				if(getHG().isActivo()==false){
    					
    					player.sendMessage("Creating world..");

    					if(getServer().getWorld("hgWorld")==null){
    						player.sendMessage("Creando mapa ...");
    						WorldCreator wcreator = new WorldCreator("hgWorld");
        					wcreator.environment(Environment.NORMAL);
        					wcreator.type(WorldType.NORMAL);
        					getServer().createWorld(wcreator);
    					}
    						
    						World mundo1=getServer().getWorld("hgWorld");
    						Chunk source= mundo1.getSpawnLocation().getChunk();
    						
    						int spawnx = source.getX();
    						int spawnz = source.getZ();
    						int spawny = mundo1.getHighestBlockYAt(spawnx, spawnz) + 4;
    						
    						//Limpiado de Inicio
    						ChunkUtil.ChunkInicio(source, spawny,"");
    						ChunkUtil.ChunkInicio(mundo1.getChunkAt(spawnx, spawnz+1), spawny,"N");
    						ChunkUtil.ChunkInicio(mundo1.getChunkAt(spawnx, spawnz-1), spawny,"S");
    						
    						ChunkUtil.ChunkInicio(mundo1.getChunkAt(spawnx+1, spawnz), spawny,"E");
    						//Chunk Semicirculo Este
    						ChunkUtil.limpiarChunkSemicirculo(mundo1.getChunkAt(spawnx+1, spawnz+1), spawny, "NE");
    						ChunkUtil.limpiarChunkSemicirculo(mundo1.getChunkAt(spawnx+1, spawnz-1), spawny, "SE");
    						
    						//Chunk Semicirculo Oeste
    						ChunkUtil.ChunkInicio(mundo1.getChunkAt(spawnx-1, spawnz), spawny,"O");
    						ChunkUtil.limpiarChunkSemicirculo(mundo1.getChunkAt(spawnx-1, spawnz+1), spawny, "NO");
    						ChunkUtil.limpiarChunkSemicirculo(mundo1.getChunkAt(spawnx-1, spawnz-1), spawny, "SO");
    						
    						//player.teleport(mundo1.getSpawnLocation());
    						
    						preparado=true;
    						
    					
    					
    					player.sendMessage("World created, use start to start a new game");
    					return true;

    				}else{
    					player.sendMessage("There is a game in progress");
    					return true;
    				}
    			
    			
    			}
    			if(args[0].equalsIgnoreCase("spectate") && args.length == 2 && !getHG().isMasterPlaying()) {
    				//Activar desactivar modo espectador
    				if(getHG().isActivo()){
    					if(args[1].equalsIgnoreCase("1")){
    						getHG().getEspectador().Next();
    						player.sendMessage("Spectating Started !");
    					}
    					if(args[1].equalsIgnoreCase("0")){
    						getHG().getEspectador().setSpectated(null);
    						player.sendMessage("Spectating Stopped !");
    					}
    				}else{
    					player.sendMessage("There is not a game started.");
    					return true;
    				}
    			}
    			
    			else 
    				player.sendMessage("HungerGames start/status/prepare");
    			
   
    		}
    		return true;
    	}
    	}	
    	return false;
    
    }
    
    // Prepara el Set de los jugadores, y mete o no al master, dependiendo de si juega.
    public Set<Player> preparePlayers(Player player, boolean masterJuega) {
		Set<Player> jug = new HashSet<Player>();
		for(Player p:getServer().getOnlinePlayers()){
			if(player!=p || masterJuega)
				jug.add(p);
			//Guarda Inventarios y Locations y Exp
			Inventarios.put(p, p.getInventory().getContents());
			Armaduras.put(p, p.getInventory().getArmorContents());
			origLocation.put(p, p.getLocation());
			Exps.put(p, p.getExp());
			Vida.put(p, p.getHealth());
			Comida.put(p, p.getFoodLevel());
		}
		return jug;
	}

	public void resetPlayers(){
    	for(Player p:getHG().getVivos()){
    		p.setExp(Exps.get(p));
    		p.getInventory().setContents(Inventarios.get(p));
    		p.getInventory().setArmorContents(Armaduras.get(p));
			p.teleport(origLocation.get(p));
			p.setHealth(Vida.get(p));
			p.setFoodLevel(Comida.get(p));
		}
		for(Player p:getHG().getMuertos()){
			p.setExp(Exps.get(p));
    		p.getInventory().setContents(Inventarios.get(p));
    		p.getInventory().setArmorContents(Armaduras.get(p));
    		p.teleport(origLocation.get(p));
    		p.setHealth(Vida.get(p));
			p.setFoodLevel(Comida.get(p));
		}
		getHG().getMaster().setExp(Exps.get(getHG().getMaster()));
		getHG().getMaster().getInventory().setContents(Inventarios.get(getHG().getMaster()));
		getHG().getMaster().getInventory().setArmorContents(Armaduras.get(getHG().getMaster()));
		getHG().getMaster().teleport(origLocation.get(getHG().getMaster()));
		getHG().getMaster().setHealth(Vida.get(getHG().getMaster()));
		getHG().getMaster().setFoodLevel(Comida.get(getHG().getMaster()));
    }
    
    
    public World getWorld1() {
    	return world1;
	}
    
    public void broadcast(String msg){
    	for(Player p:getServer().getOnlinePlayers()){
    		p.sendMessage("[hungerGames] " + msg);
    	}
    	log.info("[hungerGames] " + msg);
    }
    
    public boolean checkWorld(Player player){
		// Busca el mundo "hgWorld", y falla si no existe (tiene que usar antes prepare)
		world1 = getServer().getWorld("hgWorld");
		if(world1 == null){
			player.sendMessage("World has not been created");
			return true;
		}
		// Comprueba que el mapa este preparado
		if(!preparado){
			player.sendMessage("World not ready, use prepare");
			return true;
		}
		return false;
    }
    
    public boolean startGame(Player player, boolean masterJuega){
    	getHG().setMasterPlaying(masterJuega);
    	// Comprueba que todos los mundos estan preparados
    	if (checkWorld(player))
    		return true;
		// Activa tarea de control del tiempo
		tareas.scheduleAsyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					Long Hora=world1.getTime();
					
					if( Hora == 18000 && getMensajeDelDia() == false){
						getServer().getPluginManager().callEvent(new EventTimeDawn());
					}
					if( getMensajeDelDia() == true && Hora >0 && Hora < 1000){
						setMensajeDelDia(false);
					}
				}
			}, 0, 1);
		 
		// Prepara a los jugadores
		Set<Player> jug = preparePlayers(player, masterJuega);
		
		Location spawnnuevo = new Location(world1
				, world1.getSpawnLocation().getBlockX()
				, world1.getHighestBlockYAt(world1.getSpawnLocation())
				, world1.getSpawnLocation().getBlockZ());
		
		for(Player p:getServer().getOnlinePlayers()){	
			//Teletransporte a lugares aleatorios en el chunk
			p.teleport(spawnnuevo.add(new Location(world1
					, (Math.random()-Math.random())*8, 0, (Math.random()-Math.random())*8)));
		}
		if(jug.isEmpty()){
			broadcast("There isn't players.");
			player.setExp(Exps.get(player));
			player.getInventory().setContents(Inventarios.get(player));
			player.getInventory().setArmorContents(Armaduras.get(player));
			player.teleport(origLocation.get(player));
			player.setHealth(Vida.get(player));
			player.setFoodLevel(Comida.get(player));
			return true;
		}
		getHG().startGame(player, jug, spawnnuevo, masterJuega);
		broadcast("The master is " + getHG().getMaster().getName());
		getHG().setMovementAllowed(false);
		world1.setPVP(false);
		broadcast("Starting new game in 10 seconds");
		// Tarea retrasada, el tiempo (200L) viene dado en ticks, hay 20 ticks por segundo.
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				getHG().setMovementAllowed(true);
				world1.setPVP(true);
				broadcast("Started!");
			}
		}, 200L);
    	return true;    	
    }
}

