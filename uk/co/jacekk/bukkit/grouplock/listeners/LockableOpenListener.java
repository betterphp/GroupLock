package uk.co.jacekk.bukkit.grouplock.listeners;

import java.util.ArrayList;

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

public class LockableOpenListener extends BaseListener<GroupLock> {
	
	public LockableOpenListener(GroupLock plugin){
		super(plugin);
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerIntereact(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		Material type = block.getType();
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && plugin.lockableContainers.contains(type) && block.hasMetadata("owner")){
			String owner = (String) block.getMetadata("owner").get(0).value();
			ArrayList<String> allowed = (ArrayList<String>) block.getMetadata("allowed").get(0).value();
			
			if (!owner.equals(playerName) && !allowed.contains(playerName)){
				event.setCancelled(true);
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + type.name().toLowerCase() + " is locked by " + owner));
			}else{
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Access to " + type.name().toLowerCase() + " granted"));
			}
		}
	}
	
}
