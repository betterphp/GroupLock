package uk.co.jacekk.bukkit.grouplock;

import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import uk.co.jacekk.bukkit.baseplugin.BaseTask;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStorable;

public class SetBlockMetadataTask extends BaseTask<GroupLock> {
	
	public SetBlockMetadataTask(GroupLock plugin){
		super(plugin);
	}
	
	public void run(){
		for (LockedBlockStorable storedBlock : plugin.lockedBlocks.getAll()){
			Block block = storedBlock.getBlock();
			
			if (block != null){
				if (!plugin.lockableBlocks.contains(block.getType())){
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
