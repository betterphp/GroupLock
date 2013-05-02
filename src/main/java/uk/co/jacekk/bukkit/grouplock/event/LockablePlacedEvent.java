package uk.co.jacekk.bukkit.grouplock.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.jacekk.bukkit.grouplock.LockableType;

public class LockablePlacedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Block block;
	private Player player;
	private LockableType lockable;
	private LockableType lockableBlock;
	
	public LockablePlacedEvent(Block block, Player player, LockableType lockable, LockableType lockableBlock){
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
