package uk.co.jacekk.bukkit.grouplock.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import uk.co.jacekk.bukkit.grouplock.GroupLock;

public abstract class GroupLockListener implements Listener {
	
	protected GroupLock plugin;
	protected List<Material> lockableContainers;
	
	public GroupLockListener(GroupLock plugin){
		this.plugin = plugin;
		
		this.lockableContainers = Arrays.asList(
			Material.CHEST,
			Material.FURNACE,
			Material.DISPENSER
		);
	}
	
}
