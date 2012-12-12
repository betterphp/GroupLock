package uk.co.jacekk.bukkit.grouplock;

import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import uk.co.jacekk.bukkit.baseplugin.v5.scheduler.BaseTask;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStorable;

public class SetBlockMetadataTask extends BaseTask<GroupLock> {
	
	public SetBlockMetadataTask(GroupLock plugin){
		super(plugin);
	}
	
	public void run(){
		for (LockedBlockStorable storedBlock : plugin.lockedBlocks.getAll()){
			Block block = storedBlock.getBlock();
			
			if (block != null){
				if (!LockableBlock.getLockabletypes().contains(block.getType()) || plugin.config.getStringList(Config.IGNORE_WORLDS).contains(block.getWorld().getName())){
					plugin.lockedBlocks.remove(block);
				}else{
					block.setMetadata("owner", new FixedMetadataValue(plugin, storedBlock.getOwner()));
					block.setMetadata("allowed", new FixedMetadataValue(plugin, storedBlock.getAllowed()));
				}
			}
		}
		
		plugin.log.info("Loaded " + plugin.lockedBlocks.size(true) + " locked blocks");
	}
	
}
