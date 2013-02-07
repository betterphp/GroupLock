package uk.co.jacekk.bukkit.grouplock.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_4_R1.TileEntity;
import net.minecraft.server.v1_4_R1.World;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.co.jacekk.bukkit.baseplugin.v9.event.BaseListener;
import uk.co.jacekk.bukkit.grouplock.Config;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.Permission;
import uk.co.jacekk.bukkit.grouplock.event.LockablePlacedEvent;
import uk.co.jacekk.bukkit.grouplock.nms.tileentity.TileEntityLockable;

public class LockableLockListener extends BaseListener<GroupLock> {
	
	private HashMap<Material, List<BlockFace>> searchLocations;
	
	public LockableLockListener(GroupLock plugin){
		super(plugin);
		
		this.searchLocations = new HashMap<Material, List<BlockFace>>();
		
		this.searchLocations.put(Material.CHEST, Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST));
	}
	
	private ArrayList<TileEntityLockable> getTileEntities(Block block){
		ArrayList<TileEntityLockable> tileEntities = new ArrayList<TileEntityLockable>(2);
		
		World world = ((CraftWorld) block.getWorld()).getHandle();
		Material type = block.getType();
		
		if (this.searchLocations.containsKey(type)){
			TileEntity tileEntity = world.getTileEntity(block.getX(), block.getY(), block.getZ());
			
			if (tileEntity != null && tileEntity instanceof TileEntityLockable){
				tileEntities.add((TileEntityLockable) tileEntity);
			}
			
			for (BlockFace face : this.searchLocations.get(type)){
				Block test = block.getRelative(face);
				
				if (test.getType() == type){
					tileEntity = world.getTileEntity(test.getX(), test.getY(), test.getZ());
					
					if (tileEntity != null && tileEntity instanceof TileEntityLockable){
						tileEntities.add((TileEntityLockable) tileEntity);
					}
				}
			}
		}
		
		return tileEntities;
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
				ArrayList<TileEntityLockable> tileEntities = this.getTileEntities(block);
				
				if (!tileEntities.isEmpty()){
					if (!tileEntities.get(0).canModify(playerName)){
						player.sendMessage(ChatColor.RED + "That " + blockName + " is locked by " + tileEntities.get(0).getOwnerName());
						return;
					}
					
					if (tileEntities.get(0).isOwnerName(playerName)){
						for (TileEntityLockable lockable : tileEntities){
							lockable.reset();
						}
						
						player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " unlocked"));
					}else{
						for (TileEntityLockable lockable : tileEntities){
							lockable.setOwnerName(playerName);
						}
						
						player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " locked"));
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onLockablePlace(LockablePlacedEvent event){
		Block block = event.getBlock();
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (Permission.LOCK.has(player) && !plugin.config.getStringList(Config.IGNORE_WORLDS).contains(block.getWorld().getName())){
			TileEntityLockable lockable = event.getlockable();
			ArrayList<TileEntityLockable> surrounding = this.getTileEntities(block);
			
			if (surrounding.size() > 1){
				for (TileEntityLockable test : surrounding){
					if (test.isOwnerName(playerName)){
						lockable.setOwnerName(playerName);
						player.sendMessage(ChatColor.LIGHT_PURPLE + "Locked block joined.");
						
						return;
					}
				}
			}else{
				lockable.setOwnerName(playerName);
				
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "Locked " + block.getType().name().toLowerCase().replace('_', ' ') + " created"));
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "To unlock it use /lock while looking at it"));
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "alternativly, you can hit it with a stick"));
			}
		}
	}
	
}
