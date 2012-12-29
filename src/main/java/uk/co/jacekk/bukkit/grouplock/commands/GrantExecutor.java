package uk.co.jacekk.bukkit.grouplock.commands;

import org.bukkit.command.CommandSender;

import uk.co.jacekk.bukkit.baseplugin.v7.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v7.command.CommandHandler;
import uk.co.jacekk.bukkit.grouplock.GroupLock;

public class GrantExecutor extends BaseCommandExecutor<GroupLock> {
	
	public GrantExecutor(GroupLock plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"grant", "g"}, description = "Allows a player or group access to a block you own", usage = "[player/group] <name>]")
	public void grant(CommandSender sender, String label, String[] args){
		
	}
	
	@CommandHandler(names = {"ungrant", "ug"}, description = "Removes a player or group access to a block you own", usage = "[player/group] <name>]")
	public void ungrant(CommandSender sender, String label, String[] args){
		
	}
	
}
