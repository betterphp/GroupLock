package uk.co.jacekk.bukkit.grouplock;

import java.io.File;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.grouplock.commands.GrantExecutor;
import uk.co.jacekk.bukkit.grouplock.commands.LockExecutor;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableLockListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableProtectListener;
import uk.co.jacekk.bukkit.grouplock.locakble.LockManager;

public class GroupLock extends BasePlugin {
	
	//public LockedBlockStore lockedBlocks;
	public LockManager lockManager;
	
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		this.lockManager = new LockManager();
		
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