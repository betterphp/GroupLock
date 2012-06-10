package uk.co.jacekk.bukkit.grouplock;

import uk.co.jacekk.bukkit.baseplugin.BaseTask;

public class TestTask extends BaseTask<GroupLock> {
	
	public TestTask(GroupLock plugin){
		super(plugin);
	}
	
	public void run(){
		System.out.println(plugin.lockedBlocks.size(true));
	}
	
}
