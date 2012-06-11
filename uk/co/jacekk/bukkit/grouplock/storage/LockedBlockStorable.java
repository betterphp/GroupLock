package uk.co.jacekk.bukkit.grouplock.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
		return (this.worldUUID.equals(location.worldUUID) && this.x.equals(location.x) && this.y.equals(location.y) && this.z.equals(location.z));
	}
	
	public boolean equals(Location location){
		return (this.worldUUID.equals(location.getWorld().getUID()) && this.x.equals(location.getBlockX()) && this.y.equals(location.getBlockY()) && this.z.equals(location.getBlockZ()));
	}
	
	public Block getBlock(){
		World world = Bukkit.getWorld(this.worldUUID);
		
		if (world == null){
			return null;
		}
		
		return world.getBlockAt(this.x, this.y, this.z);
	}
	
	public String getOwner(){
		return this.owner;
	}
	
	public ArrayList<String> getAllowed(){
		return this.allowed;
	}
	
}
