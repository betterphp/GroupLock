package uk.co.jacekk.bukkit.grouplock;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Locker {
	
	private GroupLock plugin;
	
	public Locker(GroupLock plugin){
		this.plugin = plugin;
	}
	
	private static Block getDataBlock(Block block){
		Material type = block.getType();
		
		if (type == Material.WOODEN_DOOR || type == Material.IRON_DOOR){
			Block below = block.getRelative(BlockFace.DOWN);
			
			if (below.getType() == type){
				return below;
			}
		}else if (type == Material.CHEST){
			Block north = block.getRelative(BlockFace.NORTH);
			Block east = block.getRelative(BlockFace.EAST);
			
			if (north.getType() == type){
				return north;
			}else if (east.getType() == type){
				return east;
			}
		}
		
		return block;
	}
	
	public void lock(Block block, String playerName){
		block = Locker.getDataBlock(block);
		
		if (!this.isBlockLocked(block)){
			block.setMetadata("owner", new FixedMetadataValue(plugin, playerName));
			block.setMetadata("allowed", new FixedMetadataValue(plugin, new ArrayList<String>()));
			
			plugin.lockedBlocks.add(block);
		}
	}
	
	public void unlock(Block block){
		block = Locker.getDataBlock(block);
		
		if (this.isBlockLocked(block)){
			block.removeMetadata("owner", plugin);
			block.removeMetadata("allowed", plugin);
		}
	}
	
	public static String getOwner(Block block){
		block = Locker.getDataBlock(block);
		
		for (MetadataValue meta : block.getMetadata("owner")){
			if (meta.getOwningPlugin() instanceof GroupLock){
				return (String) meta.value();
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getAllowedPlayers(Block block){
		block = Locker.getDataBlock(block);
		
		for (MetadataValue meta : block.getMetadata("allowed")){
			if (meta.getOwningPlugin() instanceof GroupLock){
				return (ArrayList<String>) meta.value();
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void addAllowedPlayer(Block block, String playerName){
		block = Locker.getDataBlock(block);
		
		for (MetadataValue meta : block.getMetadata("allowed")){
			if (meta.getOwningPlugin() instanceof GroupLock){
				((ArrayList<String>) meta.value()).add(playerName);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeAllowedPlayer(Block block, String playerName){
		block = Locker.getDataBlock(block);
		
		for (MetadataValue meta : block.getMetadata("allowed")){
			if (meta.getOwningPlugin() instanceof GroupLock){
				((ArrayList<String>) meta.value()).remove(playerName);
			}
		}
	}
	
	public boolean isBlockLocked(Block block){
		block = Locker.getDataBlock(block);
		
		return (block.hasMetadata("owner") && block.hasMetadata("allowed"));
	}
	
	public boolean playerCanAccess(Block block, Player player){
		String playerName = player.getName();
		block = Locker.getDataBlock(block);
		
		if (!this.isBlockLocked(block) || Permission.OPEN_LOCKED.hasPermission(player)){
			return true;
		}
		
		return (Locker.getOwner(block).equals(playerName) || Locker.getAllowedPlayers(block).contains(playerName));
	}
	
}
