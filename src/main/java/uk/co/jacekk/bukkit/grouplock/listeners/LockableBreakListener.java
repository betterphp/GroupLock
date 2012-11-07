package uk.co.jacekk.bukkit.grouplock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import uk.co.jacekk.bukkit.baseplugin.v4.event.BaseListener;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.Locker;

public class LockableBreakListener extends BaseListener<GroupLock> {
	
	public LockableBreakListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		Block block = event.getBlock();
		World world = block.getWorld();
		Material type = block.getType();
		
		Player player = event.getPlayer();
		
		if (plugin.lockableBlocks.contains(type) && plugin.locker.isBlockLocked(block)){
			String blockName = type.name().toLowerCase().replace('_', ' ');
			String owner = Locker.getOwner(block);
			
			if (!plugin.locker.playerCanAccess(block, player)){
				event.setCancelled(true);
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + blockName + " is locked by " + owner));
			}else{
				plugin.locker.unlock(block);
			}
		}else{
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			for (int dx = -1; dx <= 1; ++dx){
				for (int dy = -2; dy <= 2; ++dy){
					for (int dz = -1; dz <= 1; ++dz){
						Block locked = world.getBlockAt(x + dx, y + dy, z + dz);
						
						if (plugin.lockableDoorBlocks.contains(locked.getType()) && !plugin.locker.playerCanAccess(locked, player)){
							event.setCancelled(true);
							player.sendMessage(plugin.formatMessage(ChatColor.RED + "You cannot break blocks this close to a locked door"));
							return;
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event){
		for (Block block : event.blockList()){
			if (plugin.locker.isBlockLocked(block)){
				event.setCancelled(true);
				return;
			}
		}
	}
	
}
