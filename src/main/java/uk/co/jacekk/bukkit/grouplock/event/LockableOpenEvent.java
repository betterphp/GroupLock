package uk.co.jacekk.bukkit.grouplock.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.jacekk.bukkit.grouplock.LockableType;

public class LockableOpenEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	private boolean canceled;
	
	private Block block;
	private Player player;
	private LockableType lockable;
	private LockableType lockableBlock;
	
	public LockableOpenEvent(Block block, Player player, LockableType lockable, LockableType lockableBlock){
		this.canceled = false;
		
		this.block = block;
		this.player = player;
		this.lockable = lockable;
		this.lockableBlock = lockableBlock;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public boolean isCancelled(){
		return this.canceled;
	}
	
	public void setCancelled(boolean cancel){
		this.canceled = cancel;
	}
	
	public Block getBlock(){
		return this.block;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public LockableType getlockable(){
		return this.lockable;
	}
	
	public LockableType getLockableBlock(){
		return this.lockableBlock;
	}
	
}
