package uk.co.jacekk.bukkit.grouplock.locakble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import uk.co.jacekk.bukkit.baseplugin.BaseObject;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.LockableType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LockManager extends BaseObject<GroupLock> {
	
	private Gson gson;
	
	public LockManager(GroupLock plugin){
		super(plugin);
		
		this.gson = (new GsonBuilder()).setPrettyPrinting().create();
	}
	
	public void saveLockable(LockableBlock lockable){
		BlockLocation location = lockable.getLocation();
		
		File worldDir = new File(plugin.getDataFolder() + File.separator +  "locks", location.getWorldUID().toString());
		File lockFile = new File(worldDir, location.getX() + "_" + location.getY() + "_" + location.getZ() + ".json");
		
		if (!worldDir.exists()){
			worldDir.mkdirs();
		}
		
		try{
			OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(lockFile));
			
			output.write(this.gson.toJson(lockable, LockableBlock.class));
			
			output.close();
		}catch (Exception e){
			plugin.log.warn("Failed to save lock file " + lockFile.getName());
			e.printStackTrace();
		}
	}
	
	public LockableBlock getLockedBlock(Location location){
		return this.getLockedBlock(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	public LockableBlock getLockedBlock(World world, int x, int y, int z){
		Material type = world.getBlockAt(x, y, z).getType();
		
		if (!LockableType.getLockabletypes().contains(type)){
			return null;
		}
		
		File worldDir = new File(plugin.getDataFolder() + File.separator +  "locks", world.getUID().toString());
		File lockFile = new File(worldDir, x + "_" + y + "_" + z + ".json");
		
		if (lockFile.exists()){
			try{
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(lockFile)));
				LockableBlock lockable = this.gson.fromJson(input, LockableBlock.class);
				input.close();
				
				return lockable;
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public LockableBlock addLockedBlock(BlockLocation location, String ownerName){
		String dirName = plugin.getDataFolder() + File.separator +  "locks" + File.separator + location.getWorldUID().toString();
		String fileName = location.getX() + "_" + location.getY() + "_" + location.getZ() + ".json";
		File lockFile = new File(dirName, fileName);
		
		if (lockFile.exists()){
			throw new IllegalArgumentException("Locked block already exists at this location");
		}
		
		if (plugin.server.getWorld(location.getWorldUID()) == null){
			throw new IllegalArgumentException("No world with UID " + location.getWorldUID().toString() + " loaded");
		}
		
		LockableBlock lockable = new LockableBlock(location, ownerName);
		
		this.saveLockable(lockable);
		
		return lockable;
	}
	
	public void removeLockedBlock(BlockLocation location){
		String dirName = plugin.getDataFolder() + File.separator +  "locks" + File.separator + location.getWorldUID().toString();
		String fileName = location.getX() + "_" + location.getY() + "_" + location.getZ() + ".json";
		File lockFile = new File(dirName, fileName);
		
		if (!lockFile.exists()){
			throw new IllegalArgumentException("No locked exists at this location");
		}
		
		lockFile.delete();
	}
	
}
