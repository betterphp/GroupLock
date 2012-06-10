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
import uk.co.jacekk.bukkit.grouplock.Permission;

public class LockableOpenListener extends BaseListener<GroupLock> {
	
	public LockableOpenListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerIntereact(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		Material type = block.getType();
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && plugin.lockableContainers.contains(type) && plugin.locker.isBlockLocked(block)){
			if (!Permission.OPEN_LOCKED.hasPermission(player) && !plugin.locker.playerCanAccess(block, playerName)){
				event.setCancelled(true);
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + type.name().toLowerCase() + " is locked"));
			}else{
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Access granted"));
			}
		}
	}
	
}
