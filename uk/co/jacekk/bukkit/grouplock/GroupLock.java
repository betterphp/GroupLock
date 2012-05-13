package uk.co.jacekk.bukkit.grouplock;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.jacekk.bukkit.grouplock.listeners.LockableBreakListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockableOpenListener;
import uk.co.jacekk.bukkit.grouplock.listeners.LockablePlaceListener;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStorable;
import uk.co.jacekk.bukkit.grouplock.storage.LockedBlockStore;
import uk.co.jacekk.bukkit.util.PluginLogger;

public class GroupLock extends JavaPlugin {
	
	public PluginLogger log;
	
	public Server server;
	public PluginManager manager;
	
	public LockedBlockStore lockedBlocks;
	
	public void onEnable(){
		this.log = new PluginLogger(this);
		
		this.server = this.getServer();
		this.manager = this.server.getPluginManager();
		
		String pluginDir = this.getDataFolder().getAbsolutePath();
		(new File(pluginDir)).mkdirs();
		
		this.lockedBlocks = new LockedBlockStore(new File(pluginDir + File.separator + "locked-blocks.bin"));
		this.lockedBlocks.load();
		
		for (LockedBlockStorable storedBlock : this.lockedBlocks.getAll()){
			Block block = storedBlock.getBlock();
			
			block.setMetadata("owner", new FixedMetadataValue(this, storedBlock.getOwner()));
			block.setMetadata("allowed", new FixedMetadataValue(this, storedBlock.getAllowed()));
		}
		
		this.manager.registerEvents(new LockablePlaceListener(this), this);
		this.manager.registerEvents(new LockableOpenListener(this), this);
		this.manager.registerEvents(new LockableBreakListener(this), this);
	}
	
	public void onDisable(){
		this.lockedBlocks.save();
	}
	
	public String formatMessage(String message, boolean colour, boolean version){
		PluginDescriptionFile description = this.getDescription();
		StringBuilder line = new StringBuilder();
		
		if (colour){
			line.append(ChatColor.BLUE);
		}
		
		line.append("[");
		line.append(description.getName());
		
		if (version){
			line.append(" v");
			line.append(description.getVersion());
		}
		
		line.append("] ");
		line.append(message);
		
		return line.toString();
	}
	
	public String formatMessage(String message, boolean colour){
		return this.formatMessage(message, colour, !colour);
	}
	
	public String formatMessage(String message){
		return this.formatMessage(message, true, false);
	}
	
}
