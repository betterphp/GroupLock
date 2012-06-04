package uk.co.jacekk.bukkit.grouplock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.co.jacekk.bukkit.baseplugin.BaseListener;
import uk.co.jacekk.bukkit.grouplock.GroupLock;

public class LockableLockListener extends BaseListener<GroupLock> {
	
	public LockableLockListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerIntereact(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		Material type = block.getType();
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && plugin.lockableContainers.contains(type)){
			String blockName = Character.toUpperCase(type.name().charAt(0)) + type.name().substring(1).toLowerCase();
			
			if (!plugin.locker.isBlockLocked(block)){
				plugin.locker.lock(block, playerName);
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + blockName + " locked"));
			}else{
				String owner = plugin.locker.getOwner(block);
				
				if (!owner.equals(playerName)){
					player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + type.name().toLowerCase() + " is locked by " + owner));
				}else{
					plugin.locker.unlock(block);
					
					player.sendMessage(plugin.formatMessage(ChatColor.GREEN + blockName + " unlocked"));
				}
			}
		}
	}
	
}
