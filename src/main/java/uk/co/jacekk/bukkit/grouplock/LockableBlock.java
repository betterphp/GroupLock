package uk.co.jacekk.bukkit.grouplock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.Block;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityBeacon;
import net.minecraft.server.TileEntityBrewingStand;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.TileEntityCommand;
import net.minecraft.server.TileEntityDispenser;
import net.minecraft.server.TileEntityEnchantTable;
import net.minecraft.server.TileEntityEnderChest;
import net.minecraft.server.TileEntityFurnace;
import net.minecraft.server.TileEntityNote;
import net.minecraft.server.TileEntityRecordPlayer;

import org.bukkit.Material;

import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableBeacon;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableBrewingStand;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableBurningFurnace;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableChest;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableCommand;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableDispenser;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableEnchantmentTable;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableEnderChest;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableFenceGate;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableFurnace;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableIronDoor;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableJukeBox;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableLever;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableNote;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableRedstoneRepeaterOff;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableRedstoneRepeaterOn;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableStoneButton;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableStonePressurePlate;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableTrapDoor;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableWoodButton;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableWoodDoor;
import uk.co.jacekk.bukkit.grouplock.nms.BlockLockableWoodPressurePlate;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockable;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableBeacon;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableBrewingStand;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableChest;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableCommand;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableDispenser;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableEnchantmentTable;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableEnderChest;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableFenceGate;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableFurnace;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableIronDoor;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableJukeBox;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableLever;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableNote;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableRedstoneRepeater;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableStoneButton;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableStonePressurePlate;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableTrapDoor;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableWoodButton;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableWoodDoor;
import uk.co.jacekk.bukkit.grouplock.nms.TileEntityLockableWoodPressurePlate;

public enum LockableBlock {
	
	CHEST(Material.CHEST, Lockable.CONTAINER, "Chest", TileEntityChest.class, TileEntityLockableChest.class, "CHEST", new BlockLockableChest()),
	ENDER_CHEST(Material.ENDER_CHEST, Lockable.CONTAINER, "EnderChest", TileEntityEnderChest.class, TileEntityLockableEnderChest.class, "ENDER_CHEST", new BlockLockableEnderChest()),
	FURNACE(Material.FURNACE, Lockable.CONTAINER, "Furnace", TileEntityFurnace.class, TileEntityLockableFurnace.class, "FURNACE", new BlockLockableFurnace()),
	BURNING_FURNACE(Material.BURNING_FURNACE, Lockable.CONTAINER, "Furnace", TileEntityFurnace.class, TileEntityLockableFurnace.class, "BURNING_FURNACE", new BlockLockableBurningFurnace()),
	DISPENSER(Material.DISPENSER, Lockable.CONTAINER, "Trap", TileEntityDispenser.class, TileEntityLockableDispenser.class, "DISPENSER", new BlockLockableDispenser()),
	BREWING_STAND(Material.BREWING_STAND, Lockable.CONTAINER, "Cauldron", TileEntityBrewingStand.class, TileEntityLockableBrewingStand.class, "BREWING_STAND", new BlockLockableBrewingStand()),
	JUKEBOX(Material.JUKEBOX, Lockable.CONTAINER, "RecordPlayer", TileEntityRecordPlayer.class, TileEntityLockableJukeBox.class, "JUKEBOX", new BlockLockableJukeBox()),
	
	WOOD_DOOR(Material.WOODEN_DOOR, Lockable.ENTRANCE, "WoodDoor", null, TileEntityLockableWoodDoor.class, "WOODEN_DOOR", new BlockLockableWoodDoor()),
	IRON_DOOR(Material.IRON_DOOR_BLOCK, Lockable.ENTRANCE, "IronDoor", null, TileEntityLockableIronDoor.class, "IRON_DOOR_BLOCK", new BlockLockableIronDoor()),
	TRAP_DOOR(Material.TRAP_DOOR, Lockable.ENTRANCE, "TrapDoor", null, TileEntityLockableTrapDoor.class, "TRAP_DOOR", new BlockLockableTrapDoor()),
	FENCE_GATE(Material.FENCE_GATE, Lockable.ENTRANCE, "FenceGate", null, TileEntityLockableFenceGate.class, "FENCE_GATE", new BlockLockableFenceGate()),
	
	BEACON(Material.BEACON, Lockable.INTERACTIVE, "Beacon", TileEntityBeacon.class, TileEntityLockableBeacon.class, "BEACON", new BlockLockableBeacon()),
	COMMAND_BLOCK(Material.COMMAND, Lockable.INTERACTIVE, "Control", TileEntityCommand.class, TileEntityLockableCommand.class, "COMMAND", new BlockLockableCommand()),
	ENCHANTMENT_TABLE(Material.ENCHANTMENT_TABLE, Lockable.INTERACTIVE, "EnchantTable", TileEntityEnchantTable.class, TileEntityLockableEnchantmentTable.class, "ENCHANTMENT_TABLE", new BlockLockableEnchantmentTable()),
	NOTE_BLOCK(Material.NOTE_BLOCK, Lockable.INTERACTIVE, "Music", TileEntityNote.class, TileEntityLockableNote.class, "NOTE_BLOCK", new BlockLockableNote()),
//	ANVIL(Material.ANVIL, Lockable.INTERACTIVE, "Anvil", null, TileEntityLockableAnvil.class, "ANVIL", new BlockLockableAnvil()), TODO: Anvils ?
	STONE_BUTTON(Material.STONE_BUTTON, Lockable.INTERACTIVE, "StoneButton", null, TileEntityLockableStoneButton.class, "STONE_BUTTON", new BlockLockableStoneButton()),
	WOOD_BUTTON(Material.WOOD_BUTTON, Lockable.INTERACTIVE, "WoodButton", null, TileEntityLockableWoodButton.class, "WOOD_BUTTON", new BlockLockableWoodButton()),
	STONE_PRESSURE_PLATE(Material.STONE_PLATE, Lockable.INTERACTIVE, "StonePressurePlate", null, TileEntityLockableStonePressurePlate.class, "STONE_PLATE", new BlockLockableStonePressurePlate()),
	WOOD_PRESSURE_PLATE(Material.WOOD_PLATE, Lockable.INTERACTIVE, "WoodPressurePlate", null, TileEntityLockableWoodPressurePlate.class, "WOOD_PLATE", new BlockLockableWoodPressurePlate()),
	LEVER(Material.LEVER, Lockable.INTERACTIVE, "Lever", null, TileEntityLockableLever.class, "LEVER", new BlockLockableLever()),
	REDSTONE_REPEATER_ON(Material.DIODE_BLOCK_ON, Lockable.INTERACTIVE, "RedstoneRepeater", null, TileEntityLockableRedstoneRepeater.class, "DIODE_ON", new BlockLockableRedstoneRepeaterOn()),
	REDSTONE_REPEATER_OFF(Material.DIODE_BLOCK_OFF, Lockable.INTERACTIVE, "RedstoneRepeater", null, TileEntityLockableRedstoneRepeater.class, "DIODE_ON", new BlockLockableRedstoneRepeaterOff());
	
	private Material type;
	private Lockable lockable;
	private String tileEntityID;
	private Class<? extends TileEntity> vanillaTileEntity;
	private Class<? extends TileEntityLockable> lockableTileEntity;
	private String blockFieldName;
	private Block block;
	
	private static final List<Material> lockableTypes;
	
	private LockableBlock(Material type, Lockable lockable, String tileEntityID, Class<? extends TileEntity> vanillaTileEntity, Class<? extends TileEntityLockable> lockableTileEntity, String blockFieldName, Block block){
		this.type = type;
		this.lockable = lockable;
		this.tileEntityID = tileEntityID;
		this.vanillaTileEntity = vanillaTileEntity;
		this.lockableTileEntity = lockableTileEntity;
		this.blockFieldName = blockFieldName;
		this.block = block;
	}
	
	static{
		lockableTypes = new ArrayList<Material>();
		
		for (LockableBlock lockable : values()){
			lockableTypes.add(lockable.getType());
		}
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
	
	public boolean isVanillaTileEntity(){
		return (this.vanillaTileEntity != null);
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
	
	public static List<Material> getLockabletypes(){
		return lockableTypes;
	}
	
}
