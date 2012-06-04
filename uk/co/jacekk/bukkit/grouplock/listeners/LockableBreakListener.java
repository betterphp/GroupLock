package uk.co.jacekk.bukkit.grouplock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import uk.co.jacekk.bukkit.baseplugin.BaseListener;
import uk.co.jacekk.bukkit.grouplock.GroupLock;

public class LockableBreakListener extends BaseListener<GroupLock> {
	
	public LockableBreakListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		Block block = event.getBlock();
		Material type = block.getType();
		Player player = event.getPlayer();
		
		if (plugin.lockableContainers.contains(type) && block.hasMetadata("owner")){
			String owner = (String) block.getMetadata("owner").get(0).value();
			
			if (!owner.equals(player.getName())){
				event.setCancelled(true);
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + type.name().toLowerCase() + " is locked by " + owner));
			}
		}
	}
	
}
