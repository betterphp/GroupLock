package uk.co.jacekk.bukkit.grouplock;

import org.bukkit.Material;


public enum LockableBlock {
	
	CHEST(Material.CHEST, Lockable.CONTAINER);
	
	private Material type;
	private Lockable lockable;
	
	private LockableBlock(Material type, Lockable lockable){
		this.type = type;
		this.lockable = lockable;
	}
	
	public Material getType(){
		return this.type;
	}
	
	public Lockable getLockableType(){
		return this.lockable;
	}
	
}
