package uk.co.jacekk.bukkit.grouplock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import uk.co.jacekk.bukkit.baseplugin.BaseListener;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.Locker;

public class LockableBreakListener extends BaseListener<GroupLock> {
	
	public LockableBreakListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		Block block = event.getBlock();
		Material type = block.getType();
		Player player = event.getPlayer();
		
		if (plugin.lockableBlocks.contains(type) && plugin.locker.isBlockLocked(block)){
			String blockName = type.name().toLowerCase().replace('_', ' ');
			
			if (!Locker.getOwner(block).equals(player.getName())){
				event.setCancelled(true);
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + blockName + " is locked"));
			}else{
				plugin.locker.unlock(block);
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
