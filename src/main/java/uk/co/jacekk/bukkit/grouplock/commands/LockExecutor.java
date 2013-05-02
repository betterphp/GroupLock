package uk.co.jacekk.bukkit.grouplock.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.CommandHandler;
import uk.co.jacekk.bukkit.grouplock.Config;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.LockableType;
import uk.co.jacekk.bukkit.grouplock.Permission;
import uk.co.jacekk.bukkit.grouplock.locakble.BlockLocation;
import uk.co.jacekk.bukkit.grouplock.locakble.LockableBlock;

public class LockExecutor extends BaseCommandExecutor<GroupLock> {
	
	public LockExecutor(GroupLock plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"lock", "l"}, description = "Lock or unlock a block", usage = "[<add/remove> <player_name>]")
	public void lock(CommandSender sender, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "The /lock command can only be used in game"));
			return;
		}
		
		if (!Permission.LOCK.has(sender)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to use this command"));
			return;
		}
		
		Player player = (Player) sender;
		String playerName = player.getName();
		
		if (plugin.config.getStringList(Config.IGNORE_WORLDS).contains(player.getWorld().getName())){
			player.sendMessage(plugin.formatMessage(ChatColor.RED + "You cannot lock blocks in this world"));
			return;
		}
		
		Block block = player.getTargetBlock(null, 10);
		
		if (block == null){
			player.sendMessage(plugin.formatMessage(ChatColor.RED + "You must be looking at a block"));
			return;
		}
		
		Material type = block.getType();
		String blockName = type.name().toLowerCase().replace('_', ' ');
		String ucfBlockName = Character.toUpperCase(blockName.charAt(0)) + blockName.substring(1);
		
		if (!LockableType.getLockabletypes().contains(type)){
			player.sendMessage(plugin.formatMessage(ChatColor.RED + "A " + blockName + " is not a lockable block"));
			return;
		}
		
		LockableBlock lockable = plugin.lockManager.getLockedBlock(block.getLocation());
		
		if (lockable == null){
			plugin.lockManager.addLockedBlock(new BlockLocation(block.getLocation()), playerName);
			player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " locked"));
		}else{
			if (!Permission.UNLOCK_LOCKED.has(player) && !lockable.canPlayerModify(playerName)){
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "That " + blockName + " is locked by " + lockable.getOwner()));
				return;
			}
			
			if (args.length == 2){
				if (args[0].equalsIgnoreCase("add")){
					lockable.addAllowedPlayer(args[1]);
					player.sendMessage(plugin.formatMessage(ChatColor.GREEN + args[1] + " has been added to the access list"));
				}else{
					lockable.removeAllowedPlayer(args[1]);
					player.sendMessage(plugin.formatMessage(ChatColor.GREEN + args[1] + " has been removed from the access list"));
				}
				
				plugin.lockManager.saveLockable(lockable);
			}else{
				plugin.lockManager.removeLockedBlock(lockable.getLocation());
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + ucfBlockName + " unlocked"));
			}
		}
	}
	
}