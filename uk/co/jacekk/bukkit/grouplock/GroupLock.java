package uk.co.jacekk.bukkit.grouplock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.grouplock.commands.LockExecutor;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableBreakListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableLockListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableOpenListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockablePlaceListener;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;

public class GroupLock extends BasePlugin {
	
	public List<Material> lockableBlocks;
	public LockedBlockStore lockedBlocks;
	
	public Locker locker;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.lockableBlocks = Arrays.asList(
			Material.CHEST,
			Material.FURNACE,
			Material.BURNING_FURNACE,
			Material.DISPENSER,
			Material.BREWING_STAND,
			Material.ENCHANTMENT_TABLE,
			Material.JUKEBOX,
			Material.TRAP_DOOR,
			Material.FENCE_GATE,
			Material.WOOD_DOOR,		// Are these the
			Material.WOODEN_DOOR,	// the same thing :s
			Material.IRON_DOOR,
			Material.STONE_BUTTON,
			Material.STONE_PLATE,
			Material.WOOD_PLATE,
			Material.LEVER
		);
		
		this.lockedBlocks = new LockedBlockStore(new File(this.baseDirPath + File.separator + "locked-blocks.bin"));
		this.lockedBlocks.load();
		
		this.locker = new Locker(this);
		
		this.scheduler.scheduleSyncDelayedTask(this, new SetBlockMetadataTask(this), 10L);
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockablePlaceListener(this), this);
		this.pluginManager.registerEvents(new LockableOpenListener(this), this);
		this.pluginManager.registerEvents(new LockableBreakListener(this), this);
		
		this.getCommand("lock").setExecutor(new LockExecutor(this));
		
		for (Permission permission : Permission.values()){
			this.pluginManager.addPermission(new org.bukkit.permissions.Permission(permission.getNode(), permission.getDescription(), permission.getDefault()));
		}
		
		File chestFile = new File(this.baseDir.getParentFile().getAbsolutePath() + File.separator + "SimpleChestLock" + File.separator + "Chests.txt");
		
		if (chestFile.exists()){
			this.log.info("Importing locks from " + chestFile.getAbsolutePath());
			
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
					
					World world = this.server.getWorld(worldName);
					
					if (world != null){
						Block block = world.getBlockAt(x, y, z);
						
						if (this.lockableBlocks.contains(block.getType())){
							this.locker.lock(block, playerName);
						}
					}
					
					System.out.println(Arrays.asList(parts).toString());
				}
				
				reader.close();
			}catch (FileNotFoundException e){
				// ummm we checked .exists()
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void onDisable(){
		this.lockedBlocks.save();
	}
	
}
