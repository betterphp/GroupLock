package uk.co.jacekk.bukkit.grouplock.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class LockedBlockStore {
	
	private HashMap<ChunkLocationStorable, ArrayList<LockedBlockStorable>> blocks;
	private File storageFile;
	
	private long lastSave;
	
	public LockedBlockStore(File storageFile){
		this.blocks = new HashMap<ChunkLocationStorable, ArrayList<LockedBlockStorable>>();
		this.storageFile = storageFile;
		
		this.lastSave = 0L;
		
		if (this.storageFile.exists() == false){
			try{
				this.storageFile.createNewFile();
				
				ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.storageFile));
				
				stream.writeObject(this.blocks);
				stream.flush();
				stream.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(){
		try{
			this.blocks = (HashMap<ChunkLocationStorable, ArrayList<LockedBlockStorable>>) new ObjectInputStream(new FileInputStream(this.storageFile)).readObject();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void save(){
		try{
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.storageFile));
			
			stream.writeObject(this.blocks);
			stream.flush();
			stream.close();
			
			this.lastSave = System.currentTimeMillis();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean contains(Block block){
		Location location = block.getLocation();
		
		for (Entry<ChunkLocationStorable, ArrayList<LockedBlockStorable>> entry : this.blocks.entrySet()){
			if (entry.getKey().equals(location)){
				for (LockedBlockStorable lockedBlock : entry.getValue()){
					if (lockedBlock.equals(location)){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void add(Block block){
		if (this.contains(block)){
			return;
		}
		
		ChunkLocationStorable chunkLocation = new ChunkLocationStorable(block.getChunk());
		LockedBlockStorable blockLocation = new LockedBlockStorable(block);
		
		for (Entry<ChunkLocationStorable, ArrayList<LockedBlockStorable>> entry : this.blocks.entrySet()){
			if (entry.getKey().equals(chunkLocation)){
				entry.getValue().add(blockLocation);
				return;
			}
		}
		
		this.blocks.put(chunkLocation, new ArrayList<LockedBlockStorable>(Arrays.asList(blockLocation)));
		
		if (System.currentTimeMillis() - this.lastSave > 60000L){ // 60 seconds
			this.save();
		}
	}
	
	public void remove(Block block){
		ChunkLocationStorable chunkLocation = new ChunkLocationStorable(block.getChunk());
		LockedBlockStorable blockLocation = new LockedBlockStorable(block);
		
		ArrayList<LockedBlockStorable> blocks;
		
		for (Entry<ChunkLocationStorable, ArrayList<LockedBlockStorable>> entry : this.blocks.entrySet()){
			if (entry.getKey().equals(chunkLocation)){
				blocks = entry.getValue();
				
				for (int i = 0; i < blocks.size(); ++i){
					if (blocks.get(i).equals(blockLocation)){
						blocks.remove(i);
						return;
					}
				}
			}
		}
	}
	
	public void removeAll(ArrayList<Block> blocks){
		for (Block block : blocks){
			this.remove(block);
		}
	}
	
	public void clear(){
		this.blocks.clear();
	}
	
	public Integer size(boolean deep){
		if (deep){
			Integer total = 0;
			
			for (ArrayList<LockedBlockStorable> blocks : this.blocks.values()){
				total += blocks.size();
			}
			
			return total;
		}else{
			return this.blocks.size();
		}
	}
	
	public List<LockedBlockStorable> getAll(){
		ArrayList<LockedBlockStorable> blocks = new ArrayList<LockedBlockStorable>();
		
		for (ArrayList<LockedBlockStorable> storeadBlocks : this.blocks.values()){
			blocks.addAll(storeadBlocks);
		}
		
		return blocks;
	}
	
}
