package uk.co.jacekk.bukkit.grouplock.locakble;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class LockableBlock {
	
	private BlockLocation location;
	private String owner;
	private List<String> alloweds;
	
	public LockableBlock(BlockLocation location, Player owner){
		this.location = location;
		this.owner = owner.getName();
		this.alloweds = new ArrayList<String>();
	}
	
	public BlockLocation getLocation(){
		return this.location;
	}
	
	public String getOwner(){
		return this.owner;
	}
	
	public boolean canPlayerModify(String name){
		return this.owner.equals(name);
	}
	
	public List<String> getAllowed(){
		return this.alloweds;
	}
	
	public boolean canPlayerAccess(String name){
		return (this.owner.equals(name) || this.alloweds.contains(name));
	}
	
	public void addAllowedPlayer(String name){
		this.alloweds.add(name);
	}
	
	public void removeAllowedPlayer(String name){
		this.alloweds.remove(name);
	}
	
}
