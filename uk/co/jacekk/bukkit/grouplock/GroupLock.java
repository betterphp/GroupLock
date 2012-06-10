package uk.co.jacekk.bukkit.grouplock;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.grouplock.commands.LockExecutor;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableBreakListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableLockListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableOpenListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockablePlaceListener;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;

public class GroupLock extends BasePlugin {
	
	public List<Material> lockableContainers;
	public LockedBlockStore lockedBlocks;
	
	public Locker locker;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.lockableContainers = Arrays.asList(
			Material.CHEST,
			Material.FURNACE,
			Material.BURNING_FURNACE,
			Material.DISPENSER
		);
		
		this.lockedBlocks = new LockedBlockStore(new File(this.baseDirPath + File.separator + "locked-blocks.bin"));
		this.lockedBlocks.load();
		
		this.locker = new Locker(this);
		
		this.scheduler.scheduleSyncDelayedTask(this, new SetBlockMetadataTask(this), 10L);
		
		this.scheduler.scheduleSyncRepeatingTask(this, new TestTask(this), 20L, 20L);
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockablePlaceListener(this), this);
		this.pluginManager.registerEvents(new LockableOpenListener(this), this);
		this.pluginManager.registerEvents(new LockableBreakListener(this), this);
		
		this.getCommand("lock").setExecutor(new LockExecutor(this));
		
		for (Permission permission : Permission.values()){
			this.pluginManager.addPermission(new org.bukkit.permissions.Permission(permission.getNode(), permission.getDescription(), permission.getDefault()));
		}
	}
	
	public void onDisable(){
		this.lockedBlocks.save();
	}
	
}
