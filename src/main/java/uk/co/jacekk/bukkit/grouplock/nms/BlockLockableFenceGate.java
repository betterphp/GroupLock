package uk.co.jacekk.bukkit.grouplock.nms;

import org.bukkit.Bukkit;

import uk.co.jacekk.bukkit.grouplock.LockableBlock;
import uk.co.jacekk.bukkit.grouplock.event.LockableOpenEvent;
import uk.co.jacekk.bukkit.grouplock.event.LockablePlacedEvent;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFenceGate;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.World;

public class BlockLockableFenceGate extends BlockFenceGate {
	
	public BlockLockableFenceGate(){
		super(LockableBlock.FENCE_GATE.getType().getId(), 4);
		
		this.c(2.0f);
		this.b(5.0f);
		this.a(Block.e);
		this.b("fenceGate");
		this.r();
	}
	
	@Override
	public void postPlace(World world, int x, int y, int z, EntityLiving entity){
		super.postPlace(world, x, y, z, entity);
		
		org.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
		org.bukkit.entity.Player player = (org.bukkit.entity.Player) entity.getBukkitEntity();
		TileEntityLockable lockable = (TileEntityLockable) world.getTileEntity(x, y, z);
		
		Bukkit.getPluginManager().callEvent(new LockablePlacedEvent(block, player, lockable, LockableBlock.FENCE_GATE));
	}
	
	@Override
	public void onPlace(World world, int x, int y, int z){
		super.onPlace(world, x, y, z);
		
		world.setTileEntity(x, y, z, new TileEntityLockableWoodDoor());
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
		
		LockableOpenEvent event = new LockableOpenEvent(block, player, lockable, LockableBlock.FENCE_GATE);
		Bukkit.getPluginManager().callEvent(event);
		
		if (event.isCancelled()){
			return false;
		}
		
		return super.interact(world, x, y, z, entity, i1, f1, f2, f3);
	}
	
}
