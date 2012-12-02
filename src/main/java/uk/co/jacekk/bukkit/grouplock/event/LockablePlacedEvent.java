package uk.co.jacekk.bukkit.grouplock.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.jacekk.bukkit.grouplock.LockableBlock;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockable;

public class LockablePlacedEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Block block;
	private Player player;
	private TileEntityLockable lockable;
	private LockableBlock lockableBlock;
	
	public LockablePlacedEvent(Block block, Player player, TileEntityLockable lockable, LockableBlock lockableBlock){
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
	
	public TileEntityLockable getlockable(){
		return this.lockable;
	}
	
	public LockableBlock getLockableBlock(){
		return this.lockableBlock;
	}
	
}
