package uk.co.jacekk.bukkit.grouplock;

import java.io.File;

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
	
	public LockManager lockManager;
	
	@Override
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		this.lockManager = new LockManager(this);
		this.lockManager.load();
		
		File oldLockFile = new File(this.baseDirPath + File.separator + "locked-blocks.bin");
		
		if (oldLockFile.exists()){
			this.log.info("Converting lock storage format, this may take some time ...");
			
			long startTime = System.currentTimeMillis();
			
			LockedBlockStore oldLocks = new LockedBlockStore(oldLockFile);
			oldLocks.load();
			
			for (LockedBlockStorable oldLock : oldLocks.getAll()){
				BlockLocation location = oldLock.getLocation();
				
				if (location != null){
					try{
						LockableBlock lockable = this.lockManager.addLockedBlock(location, oldLock.getOwner());
						
						for (String allowed : oldLock.getAllowed()){
							lockable.addAllowedPlayer(allowed);
						}
						
						this.lockManager.saveLockable(lockable);
					}catch (IllegalArgumentException e){
						this.log.info("Failed to convert lock: " + e.getMessage());
					}
				}
			}
			
			long timeTaken = System.currentTimeMillis() - startTime;
			
			this.log.info("Converted " + oldLocks.size(true) + " locked blocks in " + timeTaken + " ms");
			
			//oldLockFile.delete();
		}
		
		this.pluginManager.registerEvents(new LockableLockListener(this), this);
		this.pluginManager.registerEvents(new LockableProtectListener(this), this);
		
		this.commandManager.registerCommandExecutor(new LockExecutor(this));
		
		this.permissionManager.registerPermissions(Permission.class);
	}
	
}