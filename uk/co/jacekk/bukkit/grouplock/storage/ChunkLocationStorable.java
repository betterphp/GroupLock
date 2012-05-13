package uk.co.jacekk.bukkit.grouplock.storage;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Chunk;

public class ChunkLocationStorable implements Serializable {
	
	private static final long serialVersionUID = -4300562242289782992L;
	
	private UUID worldUUID;
	private Integer x;
	private Integer z;
	
	public ChunkLocationStorable(Chunk chunk){
		this.worldUUID = chunk.getWorld().getUID();
		
		this.x = chunk.getX();
		this.z = chunk.getZ();
	}
	
	public boolean equals(ChunkLocationStorable location){
		return (this.worldUUID == location.worldUUID && this.x == location.x && this.z == location.z);
	}
	
	public UUID getWorldUUID(){
		return this.worldUUID;
	}
	
	public Integer getX(){
		return this.x;
	}
	
	public Integer getZ(){
		return this.z;
	}
	
}
