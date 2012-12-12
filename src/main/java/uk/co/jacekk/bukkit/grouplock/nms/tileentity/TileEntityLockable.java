package uk.co.jacekk.bukkit.grouplock.nms.tileentity;

import java.util.ArrayList;

public interface TileEntityLockable {
	
	public boolean hasOwnerName();
	
	public boolean isOwnerName(String playerName);
	
	public boolean canAccess(String playerName);
	
	public boolean canModify(String playerName);
	
	public String getOwnerName();
	
	public void setOwnerName(String ownerName);
	
	public boolean isLocked();
	
	public void setLocked(boolean lock);
	
	public ArrayList<String> getAllowedPlayers();
	
	public void addAllowedPlayer(String playerName);
	
	public void removeAllowedPlayer(String playerName);
	
	public void reset();
	
}
