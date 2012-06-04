package uk.co.jacekk.bukkit.grouplock;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableBreakListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableOpenListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockablePlaceListener;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStorable;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;

public class GroupLock extends BasePlugin {
	
	public List<Material> lockableContainers;
	public LockedBlockStore lockedBlocks;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.lockedBlocks = new LockedBlockStore(new File(this.baseDirPath + File.separator + "locked-blocks.bin"));
		this.lockedBlocks.load();
		
		this.lockableContainers = Arrays.asList(
			Material.CHEST,
			Material.FURNACE,
			Material.DISPENSER
		);
		
		for (LockedBlockStorable storedBlock : this.lockedBlocks.getAll()){
			Block block = storedBlock.getBlock();
			
			block.setMetadata("owner", new FixedMetadataValue(this, storedBlock.getOwner()));
			block.setMetadata("allowed", new FixedMetadataValue(this, storedBlock.getAllowed()));
		}
		
		this.pluginManager.registerEvents(new LockablePlaceListener(this), this);
		this.pluginManager.registerEvents(new LockableOpenListener(this), this);
		this.pluginManager.registerEvents(new LockableBreakListener(this), this);
	}
	
	public void onDisable(){
		this.lockedBlocks.save();
	}
	
}
