package me.helmetk.hungerGames;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;


public class hgChunkGenerator extends ChunkGenerator {
	private ChunkGenerator generator;
	
	
	public hgChunkGenerator (ChunkGenerator cg){
		super();
		generator = cg;
	}
	
	@Override
	public byte[] generate(World world, Random random, int x, int z) {
		byte[] result = generator.generate(world, random, x, z);
		int spawnx=world.getSpawnLocation().getChunk().getX();
		int spawnz=world.getSpawnLocation().getChunk().getX();
		int spawny=world.getHighestBlockYAt(spawnx, spawnz);
		//Si es el chunk del spawn modificamos
		//result[(x * 16 + z) * 128 + y]
		if( x==spawnx && spawnz==z){
			for(int x1=0;x1<16;x1++){
				for(int z1=0;x1<16;z1++){
					result[(x1 * 16 + z1) * 128 + spawny]=(byte)Material.BEDROCK.getId();
				}
			}
		}
		
		return result;
	}

}
