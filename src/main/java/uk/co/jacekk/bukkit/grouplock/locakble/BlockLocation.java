package uk.co.jacekk.bukkit.grouplock.locakble;

import java.util.UUID;

import org.bukkit.Location;

public class BlockLocation {
	
	private UUID worldUID;
	private int x;
	private int y;
	private int z;
	
	public BlockLocation(UUID worldUID, int x, int y, int z){
		this.worldUID = worldUID;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BlockLocation(Location location){
		this(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	public UUID getWorldUID(){
		return this.worldUID;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getZ(){
		return this.z;
	}
	
	@Override
	public int hashCode(){
		int code = 3;
		
		code = 19 * code + this.worldUID.hashCode();
		code = 19 * code + this.x;
		code = 19 * code + this.y;
		code = 19 * code + this.z;
		
		return code;
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof BlockLocation){
			BlockLocation test = (BlockLocation) object;
			
			return (this.x == test.x && this.y == test.y && this.z == test.z && this.worldUID.equals(test.worldUID));
		}else if (object instanceof Location){
			Location test = (Location) object;
			
			return (this.x == test.getBlockX() && this.y == test.getBlockY() && this.z == test.getBlockZ() && this.worldUID.equals(test.getWorld().getUID()));
		}
		
		return false;
	}
	
}
