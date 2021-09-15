package fr.ludo;

public class PlayerQuest {
	private final String playerName;
	private final Quest quest;
	public int done;
	
	public PlayerQuest(String playerName, Quest quest) {
		this.playerName = playerName;
		this.quest = quest;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public Quest getQuest() {
		return this.quest;
	}
	
	public boolean isFinish() {
		return done >= quest.getAmount();
	}
}
