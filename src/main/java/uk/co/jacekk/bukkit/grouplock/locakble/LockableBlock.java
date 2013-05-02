package uk.co.jacekk.bukkit.grouplock.locakble;

import java.util.ArrayList;
import java.util.List;

public class LockableBlock {
	
	private BlockLocation location;
	private String owner;
	private List<String> allowedPlayers;
	
	public LockableBlock(BlockLocation location, String ownerName){
		this.location = location;
		this.owner = ownerName.toLowerCase();
		this.allowedPlayers = new ArrayList<String>();
	}
	
	public BlockLocation getLocation(){
		return this.location;
	}
	
	public String getOwner(){
		return this.owner;
	}
	
	public boolean canPlayerModify(String name){
		return this.owner.equalsIgnoreCase(name);
	}
	
	public List<String> getAllowedPlayers(){
		return this.allowedPlayers;
	}
	
	public boolean canPlayerAccess(String name){
		return (this.owner.equalsIgnoreCase(name) || this.allowedPlayers.contains(name.toLowerCase()));
	}
	
	public void addAllowedPlayer(String name){
		this.allowedPlayers.add(name.toLowerCase());
	}
	
	public void removeAllowedPlayer(String name){
		this.allowedPlayers.remove(name.toLowerCase());
	}
	
}
