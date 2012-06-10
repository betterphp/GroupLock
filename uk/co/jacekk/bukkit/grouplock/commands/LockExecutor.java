package uk.co.jacekk.bukkit.grouplock.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.BaseCommandExecutor;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.Permission;

public class LockExecutor extends BaseCommandExecutor<GroupLock> {
	
	public LockExecutor(GroupLock plugin){
		super(plugin);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "The /lock command can only be used in game"));
			return true;
		}
		
		if (!Permission.LOCK.hasPermission(sender)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to use this command"));
			return true;
		}
		
		Player player = (Player) sender;
		String playerName = player.getName();
		Block block = player.getTargetBlock(null, 20);
		Material type = block.getType();
		
		String blockName = type.name().toLowerCase();
		String ucfBlockName = Character.toUpperCase(type.name().charAt(0)) + blockName.substring(1);
		
		if (!plugin.lockableContainers.contains(block.getType())){
			player.sendMessage(plugin.formatMessage(ChatColor.RED + ucfBlockName + " is not a lockable block"));
			return true;
		}
		
		if (!plugin.locker.isBlockLocked(block)){
			plugin.locker.lock(block, playerName);
			player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " locked"));
		}else{
			String owner = plugin.locker.getOwner(block);
			
			if (!Permission.UNLOCK_LOCKED.hasPermission(player) && !owner.equals(playerName)){
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + blockName + " is locked by " + owner));
			}else{
				if (args.length == 2){
					if (args[0].equalsIgnoreCase("add")){
						plugin.locker.addAllowedPlayers(block, args[1]);
						player.sendMessage(plugin.formatMessage(ChatColor.GREEN + args[1] + " has been added to the access list"));
					}else{
						plugin.locker.removeAllowedPlayers(block, args[1]);
						player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " unlocked"));
						player.sendMessage(plugin.formatMessage(ChatColor.GREEN + args[1] + " has been removed from the access list"));
					}
				}else{
					plugin.locker.unlock(block);
					player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " unlocked"));
				}
			}
		}
		
		return true;
	}
	
}