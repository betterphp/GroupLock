package uk.co.jacekk.bukkit.grouplock.nms;

import org.bukkit.Material;

import net.minecraft.server.BlockCommand;

public class BlockLockableCommand extends BlockCommand {
	
	public BlockLockableCommand(){
		super(Material.COMMAND.getId());
		
		this.b("commandBlock");
	}
	
}
