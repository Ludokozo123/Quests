package fr.ludo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.ludo.PlayerQuest;
import fr.ludo.Quest;
import fr.ludo.QuestPlugin;

public class CommandQuests implements CommandExecutor{
	private final QuestPlugin inst;
	
	public CommandQuests(QuestPlugin instance) {
		this.inst = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labl, String[] args) {
		if(!sender.isOp()) return true;
		
		if(args.length == 1) {
			this.firstLength(sender, args[0]);
		}else if(args.length == 2){
			this.secondLength(sender, args[0], args[1]);
		}else if(args.length > 2){
			this.thirdLength(sender, args[0], args[1], args[2]);
		}else {
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Commands:");
			sender.sendMessage(ChatColor.RED + "   /quest add <questname> <playername>");
			sender.sendMessage(ChatColor.RED + "   /quest remove <questname> <playername>");
			sender.sendMessage(ChatColor.RED + "   /quest list <playername>");
		}
		
		return true;
	}	
	
	public void firstLength(CommandSender sender, String arg) {
		if(arg.equalsIgnoreCase("add")){
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Command Syntax: /quest add <questname> <playername>");
		}else if(arg.equalsIgnoreCase("remove")){
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Command Syntax: /remove remove <questname> <playername>");
		}else if(arg.equalsIgnoreCase("list")){
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Command Syntax: /quest list <playername>");
		}
	}
	
	public void secondLength(CommandSender sender, String first, String second) {
		if(first.equalsIgnoreCase("add")){
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Command Syntax: /quest add <questname> <playername>");
		}else if(first.equalsIgnoreCase("remove")){
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Command Syntax: /remove remove <questname> <playername>");
		}else if(first.equalsIgnoreCase("list")){
			final String playerName = second;
			
			sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Active Quests:");
			
			for(final PlayerQuest playerQuest : inst.getPlayerQuests().get(playerName))
				sender.sendMessage(ChatColor.GREEN + "   - " + playerQuest.getQuest().getLabel() + ": " + playerQuest.done + "/" + playerQuest.getQuest().getAmount()); 
		}
	}
	
	public void thirdLength(CommandSender sender, String first, String second, String third) {
		if(first.equalsIgnoreCase("add")){
			final String playerName = second;
			final String questName = third;
						
			for(Quest quest : QuestPlugin.inst().getConfigManager().getQuests()) {
				if(quest.getLabel().equalsIgnoreCase(questName)) {
					this.inst.getPlayerQuests().get(playerName).add(new PlayerQuest(playerName, quest));
					sender.sendMessage(ChatColor.GREEN + "Quest '" + questName + "' added to '" + playerName + "'!");
					return;
				}
			}
		}else if(first.equalsIgnoreCase("remove")){
			final String playerName = second;
			final String questName = third;
			
			for(PlayerQuest playerQuest : this.inst.getPlayerQuests().get(playerName)) {
				if(playerQuest.getQuest().getLabel().equals(questName)) {
					this.inst.getPlayerQuests().get(playerName).remove(playerQuest);
					sender.sendMessage(ChatColor.GREEN + "Quest '" + questName + "' removed to '" + playerName + "'!");
					return;
				}
			}
		}else if(first.equalsIgnoreCase("list")){
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Command Syntax: /quest list <playername>");
		}
	}
}
