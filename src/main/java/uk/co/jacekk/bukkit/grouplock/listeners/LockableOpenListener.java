package uk.co.jacekk.bukkit.grouplock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.co.jacekk.bukkit.baseplugin.BaseListener;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.Locker;

public class LockableOpenListener extends BaseListener<GroupLock> {
	
	public LockableOpenListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerIntereact(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		Material type = block.getType();
		Player player = event.getPlayer();
		
		if (plugin.lockableBlocks.contains(type) && plugin.locker.isBlockLocked(block)){
			String blockName = type.name().toLowerCase().replace('_', ' ');
			String owner = Locker.getOwner(block);
			
			if (!plugin.locker.playerCanAccess(block, player)){
				event.setCancelled(true);
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + blockName + " is locked by " + owner));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		
		Block block = event.getBlock();
		World world = block.getWorld();
		
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		
		for (int dx = -1; dx <= 1; ++dx){
			for (int dy = -2; dy <= 2; ++dy){
				for (int dz = -1; dz <= 1; ++dz){
					Block locked = world.getBlockAt(x + dx, y + dy, z + dz);
					
					if (plugin.lockableBlocks.contains(locked.getType()) && !plugin.locker.playerCanAccess(locked, player)){
						event.setCancelled(true);
						player.sendMessage(plugin.formatMessage(ChatColor.RED + "You cannot place blocks this close to a locked door"));
						return;
					}
				}
			}
		}
	}
	
}
