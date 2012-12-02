package uk.co.jacekk.bukkit.grouplock.nms;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import uk.co.jacekk.bukkit.grouplock.event.LockablePlacedEvent;

import net.minecraft.server.Block;
import net.minecraft.server.BlockChest;
import net.minecraft.server.EntityLiving;
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
		return new TileEntityLockableChest();
	}
	
	@Override
	public void postPlace(World world, int x, int y, int z, EntityLiving entity){
		super.postPlace(world, x, y, z, entity);
		
		org.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
		org.bukkit.entity.Player player = (org.bukkit.entity.Player) entity.getBukkitEntity();
		TileEntityLockable lockable = (TileEntityLockable) world.getTileEntity(x, y, z);
		
		Bukkit.getPluginManager().callEvent(new LockablePlacedEvent(block, player, lockable));
	}
	
}
