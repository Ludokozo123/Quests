package fr.ludo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import fr.ludo.commands.CommandQuests;
import fr.ludo.listeners.PlayerListener;

public class QuestPlugin extends JavaPlugin{
	private static QuestPlugin instance;
	
	public static QuestPlugin inst() {
		return QuestPlugin.instance;
	}
	
	private ConfigManager configManager;
	private DBManager dbManager;
	
	private Gson gson;

	public Gson getGson() {
		return this.gson;
	}
	
	@Override
	public void onEnable() {
		QuestPlugin.instance = this;
		
		this.saveDefaultConfig();
		this.configManager = new ConfigManager(this.getConfig());
		
		this.gson = new Gson();
		
		try {
			this.dbManager = new DBManager();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		//this.playerQuests = new HashMap<String, ArrayList<PlayerQuest>>();
		
		this.getCommand("quests").setExecutor(new CommandQuests(this));
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		System.out.println(this.gson.toJson(this.configManager.getQuests()));
	}
	
	public ConfigManager getConfigManager() {
		return this.configManager;
	}
	
	public DBManager getDBManager() {
		return this.dbManager;
	}
}
