package uk.co.jacekk.bukkit.grouplock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum LockableType {
	
	CHEST(Material.CHEST, LockType.CONTAINER),
	ENDER_CHEST(Material.ENDER_CHEST, LockType.CONTAINER),
	FURNACE(Material.FURNACE, LockType.CONTAINER),
	DISPENSER(Material.DISPENSER, LockType.CONTAINER),
	BREWING_STAND(Material.BREWING_STAND, LockType.CONTAINER),
	JUKEBOX(Material.JUKEBOX, LockType.CONTAINER),
	
	WOOD_DOOR(Material.WOODEN_DOOR, LockType.ENTRANCE),
	IRON_DOOR(Material.IRON_DOOR_BLOCK, LockType.ENTRANCE),
	TRAP_DOOR(Material.TRAP_DOOR, LockType.ENTRANCE),
	FENCE_GATE(Material.FENCE_GATE, LockType.ENTRANCE),
	
	BEACON(Material.BEACON, LockType.INTERACTIVE),
	COMMAND_BLOCK(Material.COMMAND, LockType.INTERACTIVE),
	ENCHANTMENT_TABLE(Material.ENCHANTMENT_TABLE, LockType.INTERACTIVE),
	NOTE_BLOCK(Material.NOTE_BLOCK, LockType.INTERACTIVE),
	ANVIL(Material.ANVIL, LockType.INTERACTIVE),
	STONE_BUTTON(Material.STONE_BUTTON, LockType.INTERACTIVE),
	WOOD_BUTTON(Material.WOOD_BUTTON, LockType.INTERACTIVE),
	STONE_PRESSURE_PLATE(Material.STONE_PLATE, LockType.INTERACTIVE),
	WOOD_PRESSURE_PLATE(Material.WOOD_PLATE, LockType.INTERACTIVE),
	LEVER(Material.LEVER, LockType.INTERACTIVE),
	REDSTONE_REPEATER_ON(Material.DIODE_BLOCK_ON, LockType.INTERACTIVE),
	REDSTONE_REPEATER_OFF(Material.DIODE_BLOCK_OFF, LockType.INTERACTIVE);
	
	private Material type;
	private LockType lockable;
	
	private static final List<Material> lockableTypes;
	
	private LockableType(Material type, LockType lockable){
		this.type = type;
		this.lockable = lockable;
	}
	
	static{
		lockableTypes = new ArrayList<Material>();
		
		for (LockableType lockable : values()){
			lockableTypes.add(lockable.getType());
		}
	}
	
	public Material getType(){
		return this.type;
	}
	
	public LockType getLockableType(){
		return this.lockable;
	}
	
	public static List<Material> getLockabletypes(){
		return lockableTypes;
	}
	
}
