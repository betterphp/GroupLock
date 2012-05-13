package uk.co.jacekk.bukkit.grouplock.listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import uk.co.jacekk.bukkit.grouplock.GroupLock;

public class LockablePlaceListener extends GroupLockListener {
	
	public LockablePlaceListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event){
		Block block = event.getBlock();
		Material type = block.getType();
		Player player = event.getPlayer();
		
		if (this.lockableContainers.contains(type)){
			block.setMetadata("owner", new FixedMetadataValue(plugin, player.getName()));
			block.setMetadata("allowed", new FixedMetadataValue(plugin, new ArrayList<String>()));
			
			player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Locked " + type.name().toLowerCase() + " created successfully"));
			player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "To unlock it use /lock while looking at it"));
		}
	}
	
}