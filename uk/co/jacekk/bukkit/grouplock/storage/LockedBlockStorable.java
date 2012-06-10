package uk.co.jacekk.bukkit.grouplock.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import uk.co.jacekk.bukkit.grouplock.Locker;

public class LockedBlockStorable implements Serializable {
	
	private static final long serialVersionUID = 4050384430959509986L;
	
	private UUID worldUUID;
	private Integer x, y, z;
	private String owner;
	private ArrayList<String> allowed;
	
	public LockedBlockStorable(Block block){
		this.worldUUID = block.getWorld().getUID();
		
		this.x = block.getX();
		this.y = block.getY();
		this.z = block.getZ();
		
		this.owner = Locker.getOwner(block);
		this.allowed = Locker.getAllowedPlayers(block);
	}
	
	public boolean equals(LockedBlockStorable location){
		return (this.worldUUID == location.worldUUID && this.x == location.x && this.y == location.y && this.z == location.z);
	}
	
	public boolean equals(Location location){
		return (this.worldUUID == location.getWorld().getUID() && this.x == location.getBlockX() && this.y == location.getBlockY() && this.z == location.getBlockZ());
	}
	
	public Block getBlock(){
		return Bukkit.getWorld(this.worldUUID).getBlockAt(this.x, this.y, this.z);
	}
	
	public String getOwner(){
		return this.owner;
	}
	
	public ArrayList<String> getAllowed(){
		return this.allowed;
	}
	
}
