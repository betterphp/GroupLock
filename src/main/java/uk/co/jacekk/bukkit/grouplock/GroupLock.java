package uk.co.jacekk.bukkit.grouplock;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.grouplock.commands.LockExecutor;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableBreakListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableLockListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableOpenListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockablePlaceListener;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;

public class GroupLock extends BasePlugin {
	
	public ArrayList<Material> lockableDoorBlocks;
	public ArrayList<Material> lockableStorageBlocks;
	
	public ArrayList<Material> lockableBlocks;
	
	public LockedBlockStore lockedBlocks;
	
	public Locker locker;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.values(), this.log);
		
		this.lockableDoorBlocks = new ArrayList<Material>();
		this.lockableStorageBlocks = new ArrayList<Material>();
		this.lockableBlocks = new ArrayList<Material>();
		
		this.lockableDoorBlocks.add(Material.TRAP_DOOR);
		this.lockableDoorBlocks.add(Material.FENCE_GATE);
		this.lockableDoorBlocks.add(Material.WOOD_DOOR);	// Are these the
		this.lockableDoorBlocks.add(Material.WOODEN_DOOR);	// the same thing :s
		this.lockableDoorBlocks.add(Material.IRON_DOOR);
		
		this.lockableStorageBlocks.add(Material.CHEST);
		this.lockableStorageBlocks.add(Material.FURNACE);
		this.lockableStorageBlocks.add(Material.BURNING_FURNACE);
		this.lockableStorageBlocks.add(Material.DISPENSER);
		this.lockableStorageBlocks.add(Material.BREWING_STAND);
		this.lockableStorageBlocks.add(Material.ENCHANTMENT_TABLE);
		this.lockableStorageBlocks.add(Material.JUKEBOX);
		this.lockableStorageBlocks.add(Material.STONE_BUTTON);
		this.lockableStorageBlocks.add(Material.STONE_PLATE);
		this.lockableStorageBlocks.add(Material.WOOD_PLATE);
		this.lockableStorageBlocks.add(Material.LEVER);
		
		this.lockableBlocks.addAll(this.lockableDoorBlocks);
		this.lockableBlocks.addAll(this.lockableStorageBlocks);
		
		this.lockedBlocks = new LockedBlockStore(new File(this.baseDirPath + File.separator + "locked-blocks.bin"));
		this.lockedBlocks.load();
		
		this.locker = new Locker(this);
		
		this.scheduler.scheduleSyncDelayedTask(this, new SetBlockMetadataTask(this), 5L);
		this.scheduler.scheduleSyncDelayedTask(this, new SimpleChestLockImportTask(this), 10L);
		
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