package fr.ludo;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
	private final FileConfiguration fileConfig;
	
	public final Quest[] quests;
	
	public ConfigManager(FileConfiguration fileConfig) {
		this.fileConfig = fileConfig;
		this.quests = this.getQuests();
	}
	
	public Quest[] getQuests() {
		if(this.quests != null) return this.quests;
		
		final ArrayList<Quest> quests = new ArrayList<Quest>();
		
		for(String key : this.getFileConfig().getConfigurationSection("quests").getKeys(false))
			quests.add(this.getQuest(key, this.getQuestSection(key)));
		
		return quests.toArray(new Quest[0]);
	}
	
	private ConfigurationSection getQuestSection(String quest) {
		return this.getFileConfig().getConfigurationSection("quests." + quest);
	}
	
	public Quest getQuest(String label, ConfigurationSection section) {
		try {
			return new Quest(
				label,
				section.getString("type"), 
				section.getString("specify"),
				section.getString("amount"),
				section.getString("rewardcmd")
			);
		} catch (Exception e) {
			System.out.println("Exception with key=='" + label + "'");
			e.printStackTrace();
			return null;
		}
	}
	
	private FileConfiguration getFileConfig() {
		return this.fileConfig;
	}
}
