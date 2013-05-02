package uk.co.jacekk.bukkit.grouplock.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.grouplock.Config;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.LockableType;
import uk.co.jacekk.bukkit.grouplock.Permission;
import uk.co.jacekk.bukkit.grouplock.locakble.BlockLocation;
import uk.co.jacekk.bukkit.grouplock.locakble.LockableBlock;

public class LockableLockListener extends BaseListener<GroupLock> {
	
	private HashMap<Material, List<BlockFace>> searchLocations;
	
	public LockableLockListener(GroupLock plugin){
		super(plugin);
		
		this.searchLocations = new HashMap<Material, List<BlockFace>>();
		
		this.searchLocations.put(Material.CHEST, Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST));
		this.searchLocations.put(Material.TRAPPED_CHEST, Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST));
	}
	
	private ArrayList<LockableBlock> getLockables(Block block){
		ArrayList<LockableBlock> lockables = new ArrayList<LockableBlock>(2);
		
		Material type = block.getType();
		
		LockableBlock lockable = plugin.lockManager.getLockedBlock(block.getLocation());
		
		if (lockable != null){
			lockables.add(lockable);
		}
		
		if (this.searchLocations.containsKey(type)){
			for (BlockFace face : this.searchLocations.get(type)){
				Block test = block.getRelative(face);
				
				if (test.getType() == type){
					LockableBlock testLockable = plugin.lockManager.getLockedBlock(block.getLocation());
					
					if (testLockable != null){
						lockables.add(testLockable);
					}
				}
			}
		}
		
		return lockables;
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerIntereact(PlayerInteractEvent event){
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && !plugin.config.getStringList(Config.IGNORE_WORLDS).contains(player.getWorld().getName())){
			Block block = event.getClickedBlock();
			Material type = block.getType();
			Material clickedWith = player.getItemInHand().getType();
			
			String blockName = type.name().toLowerCase().replace('_', ' ');
			String ucfBlockName = Character.toUpperCase(blockName.charAt(0)) + blockName.substring(1);
			String playerName = player.getName();
			
			if (clickedWith == Material.STICK && Permission.LOCK.has(player)){
				ArrayList<LockableBlock> lockables = this.getLockables(block);
				
				if (!lockables.isEmpty()){
					if (!lockables.get(0).canPlayerModify(playerName)){
						player.sendMessage(ChatColor.RED + "That " + blockName + " is locked by " + lockables.get(0).getOwner());
						return;
					}
					
					if (lockables.get(0).canPlayerModify(playerName)){
						for (LockableBlock lockable : lockables){
							plugin.lockManager.removeLockedBlock(lockable.getLocation());
						}
						
						player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " unlocked"));
					}else{
						for (LockableBlock lockable : lockables){
							plugin.lockManager.addLockedBlock(lockable.getLocation(), playerName);
						}
						
						player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " locked"));
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onLockablePlace(BlockPlaceEvent event){
		Block block = event.getBlock();
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (LockableType.getLockabletypes().contains(block.getType()) && Permission.LOCK.has(player) && !plugin.config.getStringList(Config.IGNORE_WORLDS).contains(block.getWorld().getName())){
			ArrayList<LockableBlock> surrounding = this.getLockables(block);
			
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
