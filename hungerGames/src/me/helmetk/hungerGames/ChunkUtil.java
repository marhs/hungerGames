package me.helmetk.hungerGames;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ChunkUtil {

    public static void ChunkInicio(Chunk source,int spawny){
    	for(int x1=0;x1<16;x1++){
			limpiarChunkAtX(source, spawny, x1);
		}
    	//Chunk 1
    	Chunk c1=source.getWorld().getChunkAt(source.getX(), source.getZ()+1);
    	limpiarChunkNorte(c1, spawny);
    	//Chunk 2
    	Chunk c2=source.getWorld().getChunkAt(source.getX()+1, source.getZ());
    	limpiarChunkEste(c2, spawny);
    	//Chunk 3
    	Chunk c3=source.getWorld().getChunkAt(source.getX(), source.getZ()-1);
    	limpiarChunkSur(c3, spawny);
    	//Chunk 4
    	Chunk c4=source.getWorld().getChunkAt(source.getX()-1, source.getZ());
    	limpiarChunkOeste(c4, spawny);
    	
    }
    
    public static void limpiarChunkEste(Chunk source,int spawny){
    	//int y=source.getWorld().getHighestBlockYAt(source.getBlock(15, spawny, 7).getLocation());
    	//Limpiar en forma de escalera
    	for(int x=0;x<16;x++){
    		int pase;boolean needed=false;
    		for(int p=0;p<16;p++){
    			pase=source.getWorld().getHighestBlockYAt(source.getBlock(x, spawny, p).getLocation())-spawny;
    			if(pase > 2){
    				needed=true;break;
    			}
    		}
    		if(needed){
    			limpiarChunkAtX(source, spawny+1, x);
    		}
    		spawny++;
    	}
    }
    
    public static void limpiarChunkNorte(Chunk source,int spawny){
    	for(int z=0;z<16;z++){
    		int pase;boolean needed=false;
    		for(int p=0;p<16;p++){
    			pase=source.getWorld().getHighestBlockYAt(source.getBlock(p, spawny, z).getLocation())-spawny;
    			if(pase > 2){
    				needed=true;break;
    			}
    		}
    		if(needed){
    			limpiarChunkAtZ(source, spawny+1, z);
    		}
    		spawny++;
    	}
    }

    public static void limpiarChunkOeste(Chunk source,int spawny){
    	for(int x=15;x>=0;x--){
    		int pase;boolean needed=false;
    		for(int p=0;p<16;p++){
    			pase=source.getWorld().getHighestBlockYAt(source.getBlock(x, spawny, p).getLocation())-spawny;
    			if(pase > 2){
    				needed=true;break;
    			}
    		}
    		if(needed){
    			limpiarChunkAtX(source, spawny+1, x);
    		}
    		spawny++;
    	}
    }

    public static  void limpiarChunkSur(Chunk source,int spawny){
    	for(int z=15;z>=0;z--){
    		int pase;boolean needed=false;
    		for(int p=0;p<16;p++){
    			pase=source.getWorld().getHighestBlockYAt(source.getBlock(p, spawny, z).getLocation())-spawny;
    			if(pase > 2){
    				needed=true;break;
    			}
    		}
    		if(needed){
    				limpiarChunkAtZ(source, spawny+1, z);
    			}
    		spawny++;
    	}
    }

    public static  void limpiarChunkAtX(Chunk source,int spawny,int X){
    	for(int z1=0;z1<16;z1++){
			//result[(x1 * 16 + z1) * 128 + spawny]=(byte)Material.BEDROCK.getId();
			Block bloque=source.getBlock(X, spawny, z1);
			bloque.setType(Material.BEDROCK);
			for(int y=spawny+1;y<128;y++)source.getBlock(X, y,z1).setType(Material.AIR);
		}
    }
    
    public static  void limpiarChunkAtZ(Chunk source,int spawny,int Z){
    	for(int z1=0;z1<16;z1++){
			//result[(x1 * 16 + z1) * 128 + spawny]=(byte)Material.BEDROCK.getId();
			Block bloque=source.getBlock(z1, spawny, Z);
			bloque.setType(Material.BEDROCK);
			for(int y=spawny+1;y<128;y++)source.getBlock(z1, y,Z).setType(Material.AIR);
		}
    }
}
