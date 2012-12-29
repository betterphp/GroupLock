package uk.co.jacekk.bukkit.grouplock.nms.block;

import org.bukkit.Bukkit;

import uk.co.jacekk.bukkit.grouplock.LockableBlock;
import uk.co.jacekk.bukkit.grouplock.event.LockableOpenEvent;
import uk.co.jacekk.bukkit.grouplock.event.LockablePlacedEvent;
import uk.co.jacekk.bukkit.grouplock.nms.tileentity.TileEntityLockable;
import uk.co.jacekk.bukkit.grouplock.nms.tileentity.TileEntityLockableFurnace;

import net.minecraft.server.v1_4_6.Block;
import net.minecraft.server.v1_4_6.BlockFurnace;
import net.minecraft.server.v1_4_6.CreativeModeTab;
import net.minecraft.server.v1_4_6.EntityHuman;
import net.minecraft.server.v1_4_6.EntityLiving;
import net.minecraft.server.v1_4_6.TileEntity;
import net.minecraft.server.v1_4_6.World;

public class BlockLockableFurnace extends BlockFurnace {
	
	public BlockLockableFurnace(){
		super(LockableBlock.FURNACE.getType().getId(), false);
		
		this.c(3.5F);
		this.a(Block.h);
		this.b("furnace");
		this.r();
		this.a(CreativeModeTab.c);
	}
	
	@Override
	public TileEntity a(World world){
		return new TileEntityLockableFurnace();
	}
	
	@Override
	public void postPlace(World world, int x, int y, int z, EntityLiving entity){
		super.postPlace(world, x, y, z, entity);
		
		org.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
		org.bukkit.entity.Player player = (org.bukkit.entity.Player) entity.getBukkitEntity();
		TileEntityLockable lockable = (TileEntityLockable) world.getTileEntity(x, y, z);
		
		Bukkit.getPluginManager().callEvent(new LockablePlacedEvent(block, player, lockable, LockableBlock.FURNACE));
	}
	
	@Override
	public boolean interact(World world, int x, int y, int z, EntityHuman entity, int i1, float f1, float f2, float f3){
		org.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
		org.bukkit.entity.Player player = (org.bukkit.entity.Player) entity.getBukkitEntity();
		TileEntityLockable lockable = (TileEntityLockable) world.getTileEntity(x, y, z);
		
		LockableOpenEvent event = new LockableOpenEvent(block, player, lockable, LockableBlock.FURNACE);
		Bukkit.getPluginManager().callEvent(event);
		
		if (event.isCancelled()){
			return false;
		}
		
		return super.interact(world, x, y, z, entity, i1, f1, f2, f3);
	}
	
}
