package uk.co.jacekk.bukkit.grouplock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.server.Block;
import net.minecraft.server.TileEntity;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.baseplugin.v5.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.v5.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.v5.util.ReflectionUtils;
import uk.co.jacekk.bukkit.grouplock.commands.LockExecutor;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableLockListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableProtectListener;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableBeacon;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableChest;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableFurnace;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;

public class GroupLock extends BasePlugin {
	
	public ArrayList<Material> lockableDoorBlocks;
	public ArrayList<Material> lockableStorageBlocks;
	
	public ArrayList<Material> lockableBlocks;
	
	public LockedBlockStore lockedBlocks;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		try{
			HashMap<String, Class<?>> a = ReflectionUtils.getFieldValue(TileEntity.class, "a", HashMap.class, null);
			HashMap<Class<?>, String> b = ReflectionUtils.getFieldValue(TileEntity.class, "b", HashMap.class, null);
			
			for (LockableBlock lockable : LockableBlock.values()){
				a.put(lockable.getTileEntityID(), lockable.getLockableTileEntity());
				b.remove(lockable.getVanillaTileEntity());
				b.put(lockable.getLockableTileEntity(), lockable.getTileEntityID());
				
				Block.byId[lockable.getType().getId()] = null;
				Block.byId[lockable.getType().getId()] = lockable.getBlock();
				
				ReflectionUtils.setFieldValue(Block.class, lockable.getBlockFieldName(), null, lockable.getBlock());
			}
			
			ReflectionUtils.setFieldValue(TileEntity.class, "a", null, a);
			ReflectionUtils.setFieldValue(TileEntity.class, "b", null, b);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		this.lockableDoorBlocks = new ArrayList<Material>();
		this.lockableStorageBlocks = new ArrayList<Material>();
		this.lockableBlocks = new ArrayList<Material>();
		
		this.lockableDoorBlocks.add(Material.TRAP_DOOR);
		this.lockableDoorBlocks.add(Material.FENCE_GATE);
		this.lockableDoorBlocks.add(Material.WOOD_DOOR);	// Are these the
		this.lockableDoorBlocks.add(Material.WOODEN_DOOR);	// the same thing :s
		this.lockableDoorBlocks.add(Material.IRON_DOOR);
		
		//this.lockableStorageBlocks.add(Material.BURNING_FURNACE); //TODO: need this too ?
		
		this.lockableStorageBlocks.add(Material.ANVIL);
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
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockablePlaceListener(this), this);
		this.pluginManager.registerEvents(new LockableOpenListener(this), this);
		this.pluginManager.registerEvents(new LockableBreakListener(this), this);
		*/
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockableProtectListener(this), this);
		
		this.commandManager.registerCommandExecutor(new LockExecutor(this));
		
		this.permissionManager.registerPermissions(Permission.class);
	}
	
	public void onDisable(){
		this.lockedBlocks.save();
	}
	
}