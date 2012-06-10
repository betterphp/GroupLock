package uk.co.jacekk.bukkit.grouplock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

import uk.co.jacekk.bukkit.baseplugin.BaseListener;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.Permission;

public class LockablePlaceListener extends BaseListener<GroupLock> {
	
	public LockablePlaceListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event){
		Block block = event.getBlock();
		Material type = block.getType();
		Player player = event.getPlayer();
		
		if (Permission.LOCK.hasPermission(player) && plugin.lockableContainers.contains(type)){
			String blockName = type.name().toLowerCase().replace('_', ' ');
			
			plugin.locker.lock(block, player.getName());
			
			player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Locked " + blockName + " created"));
			player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "To unlock it use /lock while looking at it"));
			player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "alternativly, you can hit it with a stick"));
		}
	}
	
}