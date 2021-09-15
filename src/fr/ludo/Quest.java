package fr.ludo;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class Quest {
	private final String label;
	private final QuestType type;
	private final String specify;
	private final int amount;
	private final String rewardcmd;
	
	public Quest(String label, String type, String specify, String amount, String rewardcmd) throws Exception{
		if(label == null) throw new NullPointerException();
		this.label = label;
		
		if(type == null) throw new NullPointerException();
		this.type = QuestType.valueOf(type);

		if(this.type != QuestType.WALK_DISTANCE && specify == null) throw new NullPointerException();
		this.specify = specify;
		
		if(amount == null) throw new NullPointerException();
		this.amount = Integer.parseInt(amount);
		
		if(rewardcmd == null) throw new NullPointerException();
		this.rewardcmd = rewardcmd;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public QuestType getType() {
		return this.type;
	}
	
	public String getStringSpecify() {
		return this.specify;
	}
	
	public Material getMaterialSpecify() {
		try {
			if(this.specify.equals("ALL")) return Material.COMMAND_BLOCK;
			return Material.valueOf(this.specify);
		}catch(Exception ex) {
			return null;
		}
	}
	
	public EntityType getEntitySpecify() {
		try {
			if(this.specify.equals("ALL")) return EntityType.LIGHTNING;
			return EntityType.valueOf(this.specify);
		}catch(Exception ex) {
			return null;
		}
	}
	
	public int getIntSpecify() {
		try {
			return Integer.parseInt(this.specify);
		}catch(Exception ex) {
			return -1;
		}
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public String getRewardCmd() {
		return this.rewardcmd;
	}
	
	public enum QuestType{
		KILL_MOBS,
		
		PLACE_BLOCK,
		BREAK_BLOCK,
		
		RUN_CMD,
		WALK_DISTANCE;
	}
}
