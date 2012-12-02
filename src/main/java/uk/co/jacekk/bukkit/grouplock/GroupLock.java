package uk.co.jacekk.bukkit.grouplock;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.server.Block;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityChest;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.baseplugin.v5.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.v5.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.v5.util.ReflectionUtils;
import uk.co.jacekk.bukkit.grouplock.commands.LockExecutor;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableLockListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableOpenListener;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableChest;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableChest;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;

public class GroupLock extends BasePlugin {
	
	public ArrayList<Material> lockableDoorBlocks;
	public ArrayList<Material> lockableStorageBlocks;
	
	public ArrayList<Material> lockableBlocks;
	
	public LockedBlockStore lockedBlocks;
	
	public Locker locker;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		try{
			HashMap<String, Class<?>> a = ReflectionUtils.getFieldValue(TileEntity.class, "a", HashMap.class, null);
			HashMap<Class<?>, String> b = ReflectionUtils.getFieldValue(TileEntity.class, "b", HashMap.class, null);
			
			a.put("Chest", TileEntityLockableChest.class);
			b.remove(TileEntityChest.class);
			b.put(TileEntityLockableChest.class, "Chest");
			
			ReflectionUtils.setFieldValue(TileEntity.class, "a", null, a);
			ReflectionUtils.setFieldValue(TileEntity.class, "b", null, b);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		Block.byId[Material.CHEST.getId()] = null;
		Block.byId[Material.CHEST.getId()] = new BlockLockableChest();
		
		ReflectionUtils.setFieldValue(Block.class, "CHEST", null, Block.byId[Material.CHEST.getId()]);
		
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
		this.lockableStorageBlocks.add(Material.ANVIL);
		this.lockableStorageBlocks.add(Material.COMMAND);
		this.lockableStorageBlocks.add(Material.DISPENSER);
		this.lockableStorageBlocks.add(Material.BREWING_STAND);
		this.lockableStorageBlocks.add(Material.ENCHANTMENT_TABLE);
		this.lockableStorageBlocks.add(Material.JUKEBOX);
		this.lockableStorageBlocks.add(Material.STONE_BUTTON);
		this.lockableStorageBlocks.add(Material.WOOD_BUTTON);
		this.lockableStorageBlocks.add(Material.STONE_PLATE);
		this.lockableStorageBlocks.add(Material.WOOD_PLATE);
		this.lockableStorageBlocks.add(Material.LEVER);
		
		this.lockableBlocks.addAll(this.lockableDoorBlocks);
		this.lockableBlocks.addAll(this.lockableStorageBlocks);
		
		/*
		this.lockedBlocks = new LockedBlockStore(new File(this.baseDirPath + File.separator + "locked-blocks.bin"));
		this.lockedBlocks.load();
		
		this.locker = new Locker(this);
		
		this.scheduler.scheduleSyncDelayedTask(this, new SetBlockMetadataTask(this), 5L);
		this.scheduler.scheduleSyncDelayedTask(this, new SimpleChestLockImportTask(this), 10L);
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockablePlaceListener(this), this);
		this.pluginManager.registerEvents(new LockableOpenListener(this), this);
		this.pluginManager.registerEvents(new LockableBreakListener(this), this);
		*/
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockableOpenListener(this), this);
		
		this.commandManager.registerCommandExecutor(new LockExecutor(this));
		
		this.permissionManager.registerPermissions(Permission.class);
	}
	
	public void onDisable(){
		this.lockedBlocks.save();
	}
	
}