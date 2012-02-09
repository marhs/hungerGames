package me.helmetk.hungerGames;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class hgBlockPopulator extends BlockPopulator {
	private boolean inicio = true;
	private Server server;
	
	public hgBlockPopulator(Server server){
		this.server = server;
	}
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		Chunk Chunkspawn = world.getSpawnLocation().getChunk();
		modificacionInicial(Chunkspawn);
		server.getLogger().info("Entra en BlockPopulator");
		
	}
	
	private void modificacionInicial (Chunk source){
		if(inicio == false){
			int spawnx = source.getX();
			int spawnz = source.getZ();
			int spawny = source.getWorld().getHighestBlockYAt(spawnx, spawnz);
			//Si es el chunk del spawn modificamos
			//result[(x * 16 + z) * 128 + y]
			for(int x1=0;x1<16;x1++){
				for(int z1=0;x1<16;z1++){
					//result[(x1 * 16 + z1) * 128 + spawny]=(byte)Material.BEDROCK.getId();
					source.getBlock(x1, spawny, z1).setTypeId(Material.BEDROCK.getId());
					
				}
			}
			inicio = true;
		}
	}

}
