package uk.co.jacekk.bukkit.grouplock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.World;
import org.bukkit.block.Block;

import uk.co.jacekk.bukkit.baseplugin.BaseTask;

public class SimpleChestLockImportTask extends BaseTask<GroupLock> {
	
	public SimpleChestLockImportTask(GroupLock plugin){
		super(plugin);
	}
	
	public void run(){
		File chestFile = new File("plugins" + File.separator + "SimpleChestLock" + File.separator + "Chests.txt");
		
		if (chestFile.exists()){
			plugin.log.info("Importing locks from " + chestFile.getAbsolutePath());
			
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(chestFile)));
				String line;
				
				while ((line = reader.readLine()) != null){
					String[] parts = line.trim().split(",");
					
					String playerName = parts[0];
					String worldName = parts[1];
					
					int x = Integer.parseInt(parts[2]);
					int y = Integer.parseInt(parts[3]);
					int z = Integer.parseInt(parts[4]);
					
					World world = plugin.server.getWorld(worldName);
					
					if (world != null){
						Block block = world.getBlockAt(x, y, z);
						
						if (plugin.lockableBlocks.contains(block.getType())){
							plugin.locker.lock(block, playerName);
						}
					}
				}
				
				reader.close();
			}catch (FileNotFoundException e){
				// ummm we checked .exists()
			}catch (IOException e){
				e.printStackTrace();
			}
			
			plugin.log.info("Done. " + plugin.lockedBlocks.size(true) + " blocks have been imported, you should now delete the SimpleChestLock folder");
		}
	}
	
}
