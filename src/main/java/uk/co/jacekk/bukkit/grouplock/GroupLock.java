package uk.co.jacekk.bukkit.grouplock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.grouplock.commands.LockExecutor;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableLockListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableProtectListener;
import uk.co.jacekk.bukkit.grouplock.locakble.BlockLocation;
import uk.co.jacekk.bukkit.grouplock.locakble.LockManager;
import uk.co.jacekk.bukkit.grouplock.locakble.LockableBlock;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStorable;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;

public class GroupLock extends BasePlugin {
	
	private HashMap<Material, List<BlockFace>> searchLocations;
	public LockManager lockManager;
	
	@Override
	public void onEnable(){
		super.onEnable(true);
		
		this.searchLocations = new HashMap<Material, List<BlockFace>>();
		
		this.searchLocations.put(Material.CHEST, Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST));
		this.searchLocations.put(Material.TRAPPED_CHEST, Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST));
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		this.lockManager = new LockManager(this);
		
		this.scheduler.runTask(this, new Runnable(){
			
			@Override
			public void run(){
				File oldLockFile = new File(baseDirPath + File.separator + "locked-blocks.bin");
				
				if (oldLockFile.exists()){
					log.info("Converting lock storage format, this may take some time ...");
					
					long startTime = System.currentTimeMillis();
					
					LockedBlockStore oldLocks = new LockedBlockStore(oldLockFile);
					oldLocks.load();
					
					for (LockedBlockStorable oldLock : oldLocks.getAll()){
						BlockLocation location = oldLock.getLocation();
						
						if (location != null){
							try{
								LockableBlock lockable = lockManager.addLockedBlock(location, oldLock.getOwner());
								
								for (String allowed : oldLock.getAllowed()){
									lockable.addAllowedPlayer(allowed);
								}
								
								lockManager.saveLockable(lockable);
							}catch (IllegalArgumentException e){
								log.info("Failed to convert lock: " + e.getMessage());
							}
						}
					}
					
					long timeTaken = System.currentTimeMillis() - startTime;
					
					log.info("Converted " + oldLocks.size(true) + " locked blocks in " + timeTaken + " ms");
					
					oldLockFile.delete();
				}
			}
			
		});
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockableProtectListener(this), this);
		
		this.commandManager.registerCommandExecutor(new LockExecutor(this));
		
		this.permissionManager.registerPermissions(Permission.class);
	}
	
	public ArrayList<Block> getLockableBlocks(Block source){
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		blocks.add(source);
		
		if (this.searchLocations.containsKey(source.getType())){
			for (BlockFace face : this.searchLocations.get(source.getType())){
				Block test = source.getRelative(face);
				
				if (test.getType() == source.getType()){
					blocks.add(test);
				}
			}
		}
		
		return blocks;
	}

	public ArrayList<LockableBlock> getLockables(Block source){
		ArrayList<LockableBlock> lockables = new ArrayList<LockableBlock>(2);
		
		for (Block block : this.getLockableBlocks(source)){
			LockableBlock lockable = this.lockManager.getLockedBlock(block.getLocation());
			
			if (lockable != null){
				lockables.add(lockable);
			}
		}
		
		return lockables;
	}
	
}