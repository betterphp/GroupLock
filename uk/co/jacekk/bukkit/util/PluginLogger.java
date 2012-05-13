package uk.co.jacekk.bukkit.util;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class PluginLogger {
	
	private Logger logger;
	private PluginDescriptionFile description;
	
	public PluginLogger(Plugin plugin){
		this.logger = Logger.getLogger("Minecraft");
		this.description = plugin.getDescription();
	}
	
	private String formatMessage(String message){
		return "[" + this.description.getName() + " v" + this.description.getVersion() + "]: " + message;
	}
	
	public void info(String msg){
		this.logger.info(this.formatMessage(msg));
	}
	
	public void warn(String msg){
		this.logger.warning(this.formatMessage(msg));
	}
	
	public void fatal(String msg){
		this.logger.severe(this.formatMessage(msg));
	}
	
}
