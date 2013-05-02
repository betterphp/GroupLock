package uk.co.jacekk.bukkit.grouplock.listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.grouplock.Config;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.LockableType;
import uk.co.jacekk.bukkit.grouplock.Permission;
import uk.co.jacekk.bukkit.grouplock.locakble.BlockLocation;
import uk.co.jacekk.bukkit.grouplock.locakble.LockableBlock;

public class LockableLockListener extends BaseListener<GroupLock> {
	
	public LockableLockListener(GroupLock plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onLockablePlace(BlockPlaceEvent event){
		Block block = event.getBlock();
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (LockableType.getLockabletypes().contains(block.getType()) && Permission.LOCK.has(player) && !plugin.config.getStringList(Config.IGNORE_WORLDS).contains(block.getWorld().getName())){
			ArrayList<LockableBlock> surrounding = plugin.getLockables(block);
			
			if (surrounding.size() > 1){
				for (LockableBlock test : surrounding){
					if (test.canPlayerModify(playerName)){
						plugin.lockManager.addLockedBlock(new BlockLocation(block.getLocation()), playerName);
						player.sendMessage(ChatColor.LIGHT_PURPLE + "Locked block joined.");
						return;
					}
				}
			}else{
				plugin.lockManager.addLockedBlock(new BlockLocation(block.getLocation()), playerName);
				
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Locked " + block.getType().name().toLowerCase().replace('_', ' ') + " created"));
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "To unlock it use /lock while looking at it"));
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "alternativly, you can hit it with a stick"));
			}
		}
	}
	
}
