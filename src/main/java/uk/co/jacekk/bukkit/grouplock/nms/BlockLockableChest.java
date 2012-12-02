package uk.co.jacekk.bukkit.grouplock.nms;

import org.bukkit.Material;

import net.minecraft.server.Block;
import net.minecraft.server.BlockChest;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class BlockLockableChest extends BlockChest {
	
	public BlockLockableChest(){
		super(Material.CHEST.getId());
		
		this.c(2.5F);
		this.a(Block.e);
		this.b("chest");
		this.r();
	}
	
	@Override
	public TileEntity a(World world){
		(new Exception()).printStackTrace();
		
		return new TileEntityLockableChest();
	}
	
}
