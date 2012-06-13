package uk.co.jacekk.bukkit.grouplock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public enum Permission {
	
	LOCK(			"grouplock.lock",				PermissionDefault.TRUE,		"Allows the player to lock/unlock blocks"),
	OPEN_LOCKED(	"grouplock.lock.openall",		PermissionDefault.OP,		"Allows the player to open all locked blocks"),
	UNLOCK_LOCKED(	"grouplock.lock.unlockall",		PermissionDefault.OP,		"Allows the player to unlock all locked blocks");
	
	protected String node;
	protected PermissionDefault defaultValue;
	protected String description;
	
	private Permission(String node, PermissionDefault defaultValue, String description){
		this.node = node;
		this.defaultValue = defaultValue;
		this.description = description;
	}
	
	public List<Player> getPlayersWith(){
		ArrayList<Player> players = new ArrayList<Player>();
		
		for (Player player : Bukkit.getServer().getOnlinePlayers()){
			if (this.hasPermission(player)){
				players.add(player);
			}
		}
		
		return players;
	}
	
	public Boolean hasPermission(CommandSender sender){
		return sender.hasPermission(this.node);
	}
	
	public String getNode(){
		return this.node;
	}
	
	public PermissionDefault getDefault(){
		return this.defaultValue;
	}
	
	public String getDescription(){
		return this.description;
	}
	
}
