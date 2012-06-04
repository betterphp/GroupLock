package uk.co.jacekk.bukkit.grouplock;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Locker {
	
	private GroupLock plugin;
	
	public Locker(GroupLock plugin){
		this.plugin = plugin;
	}
	
	public void lock(Block block, String playerName){
		block.setMetadata("owner", new FixedMetadataValue(plugin, playerName));
		block.setMetadata("allowed", new FixedMetadataValue(plugin, new ArrayList<String>()));
		
		plugin.lockedBlocks.add(block);
	}
	
	public void unlock(Block block){
		block.removeMetadata("owner", plugin);
		block.removeMetadata("allowed", plugin);
		
		plugin.lockedBlocks.remove(block);
	}
	
	public String getOwner(Block block){
		for (MetadataValue meta : block.getMetadata("owner")){
			if (meta.getOwningPlugin() instanceof GroupLock){
				return (String) meta.value();
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getAllowedPlayers(Block block){
		for (MetadataValue meta : block.getMetadata("allowed")){
			if (meta.getOwningPlugin() instanceof GroupLock){
				return (ArrayList<String>) meta.value();
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void addAllowedPlayers(Block block, String playerName){
		for (MetadataValue meta : block.getMetadata("allowed")){
			if (meta.getOwningPlugin() instanceof GroupLock){
				((ArrayList<String>) meta.value()).add(playerName);
			}
		}
	}
	
	public boolean isBlockLocked(Block block){
		return (block.hasMetadata("owner") && block.hasMetadata("allowed"));
	}
	
	public boolean playerCanAccess(Block block, String playerName){
		if (!this.isBlockLocked(block)){
			return true;
		}
		
		return (this.getOwner(block).equals(playerName) || this.getAllowedPlayers(block).contains(playerName));
	}
	
}
