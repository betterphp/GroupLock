package uk.co.jacekk.bukkit.grouplock;

import net.minecraft.server.Block;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityBeacon;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.TileEntityFurnace;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableBeacon;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableChest;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableFurnace;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockable;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableBeacon;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableChest;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableFurnace;


public enum LockableBlock {
	
	CHEST(Material.CHEST, Lockable.CONTAINER, "Chest", TileEntityChest.class, TileEntityLockableChest.class, "CHEST", new BlockLockableChest()),
	BEACON(Material.BEACON, Lockable.INTERACTIVE, "Beacon", TileEntityBeacon.class, TileEntityLockableBeacon.class, "BEACON", new BlockLockableBeacon()),
	FURNACE(Material.FURNACE, Lockable.CONTAINER, "Furnace", TileEntityFurnace.class, TileEntityLockableFurnace.class, "FURNACE", new BlockLockableFurnace());
	
	private Material type;
	private Lockable lockable;
	private String tileEntityID;
	private Class<? extends TileEntity> vanillaTileEntity;
	private Class<? extends TileEntityLockable> lockableTileEntity;
	private String blockFieldName;
	private Block block;
	
	private LockableBlock(Material type, Lockable lockable, String tileEntityID, Class<? extends TileEntity> vanillaTileEntity, Class<? extends TileEntityLockable> lockableTileEntity, String blockFieldName, Block block){
		this.type = type;
		this.lockable = lockable;
		this.tileEntityID = tileEntityID;
		this.vanillaTileEntity = vanillaTileEntity;
		this.lockableTileEntity = lockableTileEntity;
		this.blockFieldName = blockFieldName;
		this.block = block;
	}
	
	public Material getType(){
		return this.type;
	}
	
	public Lockable getLockableType(){
		return this.lockable;
	}
	
	public String getTileEntityID(){
		return this.tileEntityID;
	}
	
	public Class<? extends TileEntity> getVanillaTileEntity(){
		return this.vanillaTileEntity;
	}
	
	public Class<? extends TileEntityLockable> getLockableTileEntity(){
		return this.lockableTileEntity;
	}
	
	public String getBlockFieldName(){
		return this.blockFieldName;
	}
	
	public Block getBlock(){
		return this.block;
	}
	
}
