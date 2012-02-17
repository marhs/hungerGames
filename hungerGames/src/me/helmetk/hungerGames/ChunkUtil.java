package me.helmetk.hungerGames;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class ChunkUtil {

    public static void ChunkInicio(Chunk source,int spawny,String direc){
    	for(int x1=0;x1<16;x1++){
			limpiarChunkAtX(source, spawny, x1,16,0);
		}
    	
    	if(direc == "Inicio"){
    		//TODO Estructura Inicial del spawn
    	}
    	//Chunk 1
    	if(direc == "N"){
    		Chunk c1=source.getWorld().getChunkAt(source.getX(), source.getZ()+1);
    		limpiarChunkNorte(c1, spawny);
    	}
    	//Chunk 2
    	if(direc == "E"){
    		Chunk c2=source.getWorld().getChunkAt(source.getX()+1, source.getZ());
    		limpiarChunkEste(c2, spawny);
    	}
    	//Chunk 3
    	if(direc == "S"){
    		Chunk c3=source.getWorld().getChunkAt(source.getX(), source.getZ()-1);
    		limpiarChunkSur(c3, spawny);
    	}
    	//Chunk 4
    	if(direc == "O"){
    		Chunk c4=source.getWorld().getChunkAt(source.getX()-1, source.getZ());
    		limpiarChunkOeste(c4, spawny);
    	}
    }
    
    public static void limpiarChunkEste(Chunk source,int spawny){
    	//int y=source.getWorld().getHighestBlockYAt(source.getBlock(15, spawny, 7).getLocation());
    	//Limpiar en forma de escalera
    	for(int x=0;x<16;x++){
    		boolean needed=false;
    		for(int p=0;p<16;p++){
    			Block pase= source.getBlock(x, spawny-1, p);
    			if(pase.getType() == Material.AIR || pase.getType() == Material.LEAVES || pase.getType() == Material.WOOD){
    				needed=true;break;    			
    			}
    		}
    		if(needed){
    			limpiarChunkAtX(source, spawny-1, x,16,0);
    		}
    		spawny--;
    	}
    }
    
    public static void limpiarChunkNorte(Chunk source,int spawny){
    	for(int z=0;z<16;z++){
    		boolean needed=false;
    		for(int p=0;p<16;p++){
    			//pase=source.getWorld().getHighestBlockYAt(source.getBlock(p, spawny, z).getLocation())-spawny;
    			Block pase= source.getBlock(p, spawny-1, z);
    			if(pase.getType() == Material.AIR || pase.getType() == Material.LEAVES || pase.getType() == Material.WOOD){
    				needed=true;break;    			
    			}
    		}
    		if(needed){
    			limpiarChunkAtZ(source, spawny-1, z,16,0);
    		}
    		spawny--;
    	}
    }

    public static void limpiarChunkOeste(Chunk source,int spawny){
    	for(int x=15;x>=0;x--){
    		boolean needed=false;
    		for(int p=0;p<16;p++){
    			Block pase= source.getBlock(x, spawny-1, p);
    			if(pase.getType() == Material.AIR || pase.getType() == Material.LEAVES || pase.getType() == Material.WOOD){
    				needed=true;break;    			
    			}
    		}
    		if(needed){
    			limpiarChunkAtX(source, spawny-1, x,16,0);
    		}
    		spawny--;
    	}
    }

    public static  void limpiarChunkSur(Chunk source,int spawny){
    	for(int z=15;z>=0;z--){
    		boolean needed=false;
    		for(int p=0;p<16;p++){
    			Block pase= source.getBlock(p, spawny-1, z);
    			if(pase.getType() == Material.AIR || pase.getType() == Material.LEAVES || pase.getType() == Material.WOOD){
    				needed=true;break;    			
    			}
    		}
    		if(needed){
    				limpiarChunkAtZ(source, spawny-1, z,16,0);
    			}
    		spawny--;
    	}
    }
    
    public static void limpiarChunkSemicirculo (Chunk source,Integer spawny, String direc){
    	Integer[] puntos=semicirculo(15, 16);
    	if(direc == "NE"){
    		for(Integer p=0;p<16;p++){
    			limpiarChunkAtX(source, spawny, p, puntos[p]+1,0);
    		}
    	}
    	if(direc == "NO"){
    		for(Integer p=0;p<16;p++){
    			limpiarChunkAtX(source, spawny, 15-p, puntos[p]+1, 0);
    		}
    	}
    	if(direc == "SE"){
    		for(Integer p=0;p<16;p++){
    			limpiarChunkAtX(source, spawny, p, 16, 15-puntos[p]);
    		}
    	}
    	if(direc == "SO"){
    		for(Integer p=0;p<16;p++){
    			limpiarChunkAtX(source, spawny, 15-p, 16, 15-puntos[p]);
    		}
    	}
    }
    
    public static void CrearMundoCirculo (Chunk source,Integer desdeY,Integer hastaY,Integer chunks){
    	Integer[] puntos=semicirculo(chunks, chunks);
    	World world=source.getWorld();
    	Chunk no,ne,so,se;
    	
    	for(Integer p=0;p<chunks;p++){
    		//NOROESTE
    		no=world.getChunkAt(source.getX()-p, source.getZ()+puntos[p]);
    		//NORESTE
    		ne=world.getChunkAt(source.getX()+p, source.getZ()+puntos[p]);
    		//SUROESTE
    		so=world.getChunkAt(source.getX()-p, source.getZ()-puntos[p]);
    		//SURESTE
    		se=world.getChunkAt(source.getX()+p, source.getZ()-puntos[p]);
    		//RELLENAR CHUNK ENTERO
    		for(Integer x=0;x<16;x++){
    			if(p >= chunks/2 && puntos[p-1]-puntos[p] > 1)
    				for(Integer z1=puntos[p]+1;z1<puntos[p-1];z1++){
    					rellenarChunkAtX(world.getChunkAt(no.getX()+1, source.getZ()+z1) , x, 16, 0, hastaY, desdeY);
    					rellenarChunkAtX(world.getChunkAt(ne.getX()-1, source.getZ()+z1) , x, 16, 0, hastaY, desdeY);
    					rellenarChunkAtX(world.getChunkAt(so.getX()+1, source.getZ()-z1) , x, 16, 0, hastaY, desdeY);
    					rellenarChunkAtX(world.getChunkAt(se.getX()-1, source.getZ()-z1) , x, 16, 0, hastaY, desdeY);
    				}
    			rellenarChunkAtX(no , x, 16, 0, hastaY, desdeY);
    			rellenarChunkAtX(ne , x, 16, 0, hastaY, desdeY);
    			rellenarChunkAtX(so , x, 16, 0, hastaY, desdeY);
    			rellenarChunkAtX(se , x, 16, 0, hastaY, desdeY);
    		}
    	}
    }
    
    private static Integer[] semicirculo (Integer radio, Integer ancho){
    	// Devuelve la coordenada [x]=z
    	Integer[] result = new Integer[ancho];
    	for(Integer x=ancho;x >0;x--){
    		Double a = Math.sqrt(Math.pow(radio, 2) - Math.pow(x, 2));
    		if(radio < x){
    			a=-1.00;
    		}
    		result[x-1]=(int) Math.floor(a);
    	}
    	return result;
    }

    public static  void limpiarChunkAtX(Chunk source,Integer spawny,Integer X,Integer hasta,Integer desde){
    	for(Integer z1=desde;z1<hasta;z1++){
			//result[(x1 * 16 + z1) * 128 + spawny]=(byte)Material.BEDROCK.getId();
			Block bloque=source.getBlock(X, spawny, z1);
			bloque.setType(Material.BEDROCK);
			for(int y=spawny+1;y<128;y++)source.getBlock(X, y,z1).setType(Material.AIR);
		}
    }
    
    public static  void limpiarChunkAtZ(Chunk source,Integer spawny,Integer Z,Integer hasta,Integer desde){
    	for(Integer z1=desde;z1<hasta;z1++){
			//result[(x1 * 16 + z1) * 128 + spawny]=(byte)Material.BEDROCK.getId();
			Block bloque=source.getBlock(z1, spawny, Z);
			bloque.setType(Material.BEDROCK);
			for(int y=spawny+1;y<128;y++)source.getBlock(z1, y,Z).setType(Material.AIR);
		}
    }
    
    public static  void rellenarChunkAtX(Chunk source,Integer X,Integer hasta,Integer desde,Integer hastaY,Integer desdeY){
    	for(Integer z1=desde;z1<hasta;z1++){
			//result[(x1 * 16 + z1) * 128 + spawny]=(byte)Material.BEDROCK.getId();
			for(int y=desdeY;y<hastaY;y++)source.getBlock(X, y,z1).setType(Material.BEDROCK);
		}
    }
    
    public static  void rellenarChunkAtZ(Chunk source,Integer Z,Integer hasta,Integer desde,Integer hastaY,Integer desdeY){
    	for(Integer z1=desde;z1<hasta;z1++){
			//result[(x1 * 16 + z1) * 128 + spawny]=(byte)Material.BEDROCK.getId();
			for(int y=desdeY;y<hastaY;y++)source.getBlock(z1, y,Z).setType(Material.BEDROCK);
		}
    }
}
