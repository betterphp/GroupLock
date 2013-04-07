package uk.co.jacekk.bukkit.grouplock;

import java.io.File;
import java.util.HashMap;

import net.minecraft.server.v1_4_R1.Block;
import net.minecraft.server.v1_4_R1.TileEntity;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;
import uk.co.jacekk.bukkit.grouplock.commands.GrantExecutor;
import uk.co.jacekk.bukkit.grouplock.commands.LockExecutor;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableLockListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableProtectListener;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;

public class GroupLock extends BasePlugin {
	
	public LockedBlockStore lockedBlocks;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		try{
			HashMap<String, Class<?>> a = ReflectionUtils.getFieldValue(TileEntity.class, "a", HashMap.class, null);
			HashMap<Class<?>, String> b = ReflectionUtils.getFieldValue(TileEntity.class, "b", HashMap.class, null);
			
			for (LockableBlock lockable : LockableBlock.values()){
				if (lockable.isVanillaTileEntity()){
					b.remove(lockable.getVanillaTileEntity());
				}
				
				a.put(lockable.getTileEntityID(), lockable.getLockableTileEntity());
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
		
		/*
		this.lockedBlocks = new LockedBlockStore(new File(this.baseDirPath + File.separator + "locked-blocks.bin"));
		this.lockedBlocks.load();
		
		this.locker = new Locker(this);
		
		this.scheduler.scheduleSyncDelayedTask(this, new SetBlockMetadataTask(this), 5L);
		*/
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockableProtectListener(this), this);
		
		this.commandManager.registerCommandExecutor(new GrantExecutor(this));
		this.commandManager.registerCommandExecutor(new LockExecutor(this));
		
		this.permissionManager.registerPermissions(Permission.class);
	}
	
	public void onDisable(){
		//this.lockedBlocks.save();
	}
	
}