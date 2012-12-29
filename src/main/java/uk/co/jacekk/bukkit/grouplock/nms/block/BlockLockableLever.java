package uk.co.jacekk.bukkit.grouplock.nms.block;

import org.bukkit.Bukkit;

import uk.co.jacekk.bukkit.grouplock.LockableBlock;
import uk.co.jacekk.bukkit.grouplock.event.LockableOpenEvent;
import uk.co.jacekk.bukkit.grouplock.event.LockablePlacedEvent;
import uk.co.jacekk.bukkit.grouplock.nms.tileentity.TileEntityLockable;
import uk.co.jacekk.bukkit.grouplock.nms.tileentity.TileEntityLockableLever;

import net.minecraft.server.v1_4_6.Block;
import net.minecraft.server.v1_4_6.BlockLever;
import net.minecraft.server.v1_4_6.EntityHuman;
import net.minecraft.server.v1_4_6.EntityLiving;
import net.minecraft.server.v1_4_6.World;

public class BlockLockableLever extends BlockLever {
	
	public BlockLockableLever(){
		super(LockableBlock.LEVER.getType().getId(), 96);
		
		this.c(0.5f);
		this.a(Block.e);
		this.b("lever");
		this.r();
	}
	
	@Override
	public void postPlace(World world, int x, int y, int z, EntityLiving entity){
		super.postPlace(world, x, y, z, entity);
		
		org.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
		org.bukkit.entity.Player player = (org.bukkit.entity.Player) entity.getBukkitEntity();
		TileEntityLockable lockable = (TileEntityLockable) world.getTileEntity(x, y, z);
		
		Bukkit.getPluginManager().callEvent(new LockablePlacedEvent(block, player, lockable, LockableBlock.LEVER));
	}
	
	@Override
	public void onPlace(World world, int x, int y, int z){
		super.onPlace(world, x, y, z);
		
		world.setTileEntity(x, y, z, new TileEntityLockableLever());
	}
	
	@Override
	public void remove(World world, int x, int y, int z, int i, int j){
		super.remove(world, x, y, z, i, j);
		
		world.r(x, y, z);
	}
	
	@Override
	public boolean interact(World world, int x, int y, int z, EntityHuman entity, int i1, float f1, float f2, float f3){
		org.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
		org.bukkit.entity.Player player = (org.bukkit.entity.Player) entity.getBukkitEntity();
		TileEntityLockable lockable = (TileEntityLockable) world.getTileEntity(x, y, z);
		
		LockableOpenEvent event = new LockableOpenEvent(block, player, lockable, LockableBlock.LEVER);
		Bukkit.getPluginManager().callEvent(event);
		
		if (event.isCancelled()){
			return false;
		}
		
		return super.interact(world, x, y, z, entity, i1, f1, f2, f3);
	}
	
}
