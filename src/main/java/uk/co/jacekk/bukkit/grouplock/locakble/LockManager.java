package uk.co.jacekk.bukkit.grouplock.locakble;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LockManager {
	
	private HashMap<BlockLocation, LockableBlock> lockedBlocks;
	
	public LockManager(){
		this.lockedBlocks = new HashMap<BlockLocation, LockableBlock>();
	}
	
	public LockableBlock getLockedBlock(BlockLocation location){
		return this.lockedBlocks.get(location);
	}
	
	public LockableBlock getLockedBlock(Location location){
		for (LockableBlock lockable : this.lockedBlocks.values()){
			if (lockable.getLocation().equals(location)){
				return lockable;
			}
		}
		
		return null;
	}
	
	public LockableBlock getLockedBlock(World world, int x, int y, int z){
		for (LockableBlock lockable : this.lockedBlocks.values()){
			BlockLocation location = lockable.getLocation();
			
			if (location.getX() == x && location.getY() == y && location.getZ() == z && location.getWorldUID().equals(world.getUID())){
				return lockable;
			}
		}
		
		return null;
	}
	
	public void addLockedBlock(BlockLocation location, Player owner){
		if (this.lockedBlocks.containsKey(location)){
			throw new IllegalArgumentException("Locked block already exists at this location");
		}
		
		this.lockedBlocks.put(location, new LockableBlock(location, owner));
	}
	
	public void removeLockedBlock(BlockLocation location){
		if (!this.lockedBlocks.containsKey(location)){
			throw new IllegalArgumentException("No locked exists at this location");
		}
		
		this.lockedBlocks.remove(location);
	}
	
}
