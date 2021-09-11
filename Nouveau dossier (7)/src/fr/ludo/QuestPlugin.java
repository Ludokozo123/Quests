package fr.ludo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import fr.ludo.commands.CommandQuests;
import fr.ludo.listeners.PlayerListener;

public class QuestPlugin extends JavaPlugin{
	private static QuestPlugin instance;
	
	public static QuestPlugin inst() {
		return QuestPlugin.instance;
	}
	
	private ConfigManager configManager;
	
	private Gson gson;
	
	private File saveFile;
	private HashMap<String, ArrayList<PlayerQuest>> playerQuests;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		QuestPlugin.instance = this;
		
		this.saveDefaultConfig();
		this.configManager = new ConfigManager(this.getConfig());
		
		this.gson = new Gson();
		
		this.saveFile = new File(this.getDataFolder(), "save.json");
		
		if(this.saveFile.exists()) {
			try {
				this.playerQuests = this.gson.fromJson(Files.readAllLines(this.saveFile.toPath(), StandardCharsets.UTF_8).get(0), new HashMap<String, ArrayList<PlayerQuest>>().getClass());
			} catch (JsonSyntaxException | IOException e) {
				e.printStackTrace();
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			}
		}else {
			this.playerQuests = new HashMap<String, ArrayList<PlayerQuest>>();
		}
		
		this.getCommand("quests").setExecutor(new CommandQuests(this));
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		System.out.println(this.gson.toJson(this.configManager.getQuests()));
	}
	
	@Override
	public void onDisable() {
		try {
			this.saveFile.createNewFile();
			PrintWriter writer = new PrintWriter(this.saveFile);
			writer.println(this.gson.toJson(this.playerQuests));
			writer.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public ConfigManager getConfigManager() {
		return this.configManager;
	}
	
	public HashMap<String, ArrayList<PlayerQuest>> getPlayerQuests(){
		return this.playerQuests;
	}
}
