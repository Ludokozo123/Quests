package fr.ludo.listeners;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.ludo.PlayerQuest;
import fr.ludo.Quest.QuestType;
import fr.ludo.QuestPlugin;

public class PlayerListener implements Listener{
	private final QuestPlugin inst;
	
	public PlayerListener(QuestPlugin instance) {
		this.inst = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		inst.getPlayerQuests().putIfAbsent(event.getPlayer().getName(), new ArrayList<PlayerQuest>());
	}
	
	@EventHandler
	public void onPlayerBreak(BlockBreakEvent event) {
		final Player player = event.getPlayer();
		final Material mat = event.getBlock().getType();
		
		PlayerQuest playerQuest = null;
		
		for(final PlayerQuest quest : this.inst.getPlayerQuests().get(player.getName())) {
			if(quest.getQuest().getType() == QuestType.BREAK_BLOCK && quest.getQuest().getMaterialSpecify() == mat) {
				quest.done++;
				
				if(!quest.isFinish()) {
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' increased 1!");
				}else {
					playerQuest = quest;
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' is finished!");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), quest.getQuest().getRewardCmd());
				}
			}
		}
		
		if(playerQuest != null)
			this.inst.getPlayerQuests().get(player.getName()).remove(playerQuest);
	}
	
	@EventHandler
	public void onPlayerPlace(BlockPlaceEvent event) {
		final Player player = event.getPlayer();
		final Material mat = event.getBlock().getType();
		
		PlayerQuest playerQuest = null;
		
		for(final PlayerQuest quest : this.inst.getPlayerQuests().get(player.getName())) {
			if(quest.getQuest().getType() == QuestType.PLACE_BLOCK && quest.getQuest().getMaterialSpecify() == mat) {
				quest.done++;
				
				if(!quest.isFinish()) {
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' increased 1!");
				}else {
					playerQuest = quest;
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' is finished!");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), quest.getQuest().getRewardCmd());
				}
			}
		}
		
		if(playerQuest != null)
			this.inst.getPlayerQuests().get(player.getName()).remove(playerQuest);
	}
	
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		final String cmd = event.getMessage().toLowerCase();

		PlayerQuest playerQuest = null;
		
		for(final PlayerQuest quest : this.inst.getPlayerQuests().get(player.getName())) {
			if(quest.getQuest().getType() == QuestType.RUN_CMD && cmd.equalsIgnoreCase(quest.getQuest().getStringSpecify())) {
				quest.done++;
				
				if(!quest.isFinish()) {
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' increased 1!");
				}else {
					playerQuest = quest;
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' is finished!");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), quest.getQuest().getRewardCmd());
				}
			}
		}
		
		if(playerQuest != null)
			this.inst.getPlayerQuests().get(player.getName()).remove(playerQuest);
	}
	
	@EventHandler
	public void onMobKilled(EntityDeathEvent event) {
		if(event.getEntity() == null) return;
		
		final EntityType entityType = event.getEntityType();
		final Player player = event.getEntity().getKiller();
		
		if(player == null) return;
		
		PlayerQuest playerQuest = null;
		
		for(final PlayerQuest quest : this.inst.getPlayerQuests().get(player.getName())) {
			if(quest.getQuest().getType() == QuestType.KILL_MOBS && entityType == quest.getQuest().getEntitySpecify()) {
				quest.done++;
				
				if(!quest.isFinish()) {
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' increased 1!");
				}else {
					playerQuest = quest;
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' is finished!");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), quest.getQuest().getRewardCmd());
				}
			}
		}
		
		if(playerQuest != null)
			this.inst.getPlayerQuests().get(player.getName()).remove(playerQuest);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		
		if(player == null) return;
		
		PlayerQuest playerQuest = null;
		
		for(final PlayerQuest quest : this.inst.getPlayerQuests().get(player.getName())) {
			if(quest.getQuest().getType() == QuestType.WALK_DISTANCE && event.getFrom().getBlockX() != event.getTo().getBlockX() && event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
				quest.done++;
				
				if(!quest.isFinish()) {
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' increased 1!");
				}else {
					playerQuest = quest;
					player.sendMessage(ChatColor.GREEN + "Your quest '" + quest.getQuest().getLabel() + "' is finished!");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), quest.getQuest().getRewardCmd());
				}
			}
		}
		
		if(playerQuest != null)
			this.inst.getPlayerQuests().get(player.getName()).remove(playerQuest);
	}
}
