package uk.co.jacekk.bukkit.grouplock;

import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.v5.permissions.PluginPermission;

public class Permission {
	
	public static final PluginPermission LOCK				= new PluginPermission("grouplock.lock", 			PermissionDefault.TRUE, "Allows the player to lock/unlock blocks");
	public static final PluginPermission OPEN_LOCKED		= new PluginPermission("grouplock.lock.openall",	PermissionDefault.OP,	"Allows the player to open all locked blocks");
	public static final PluginPermission UNLOCK_LOCKED	= new PluginPermission("grouplock.lock.unlockall",	PermissionDefault.OP,	"Allows the player to unlock all locked blocks");
	
}
