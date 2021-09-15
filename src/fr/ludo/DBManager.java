package fr.ludo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {
	private final Connection conn;
	
	public DBManager() throws Exception {
		Class.forName("org.sqlite.JDBC");
		this.conn = DriverManager.getConnection("jdbc:sqlite:sqlite.db");
		
		final Statement st = this.conn.createStatement();
		st.executeUpdate("CREATE TABLE IF NOT EXISTS `players` (player TEXT, json JSON);");
		st.close();
	}
	
	public void update(String key, ArrayList<PlayerQuest> value) {
		try {
			final PreparedStatement st = this.conn.prepareStatement("INSERT INTO players VALUES (?, ?) ON CONFLICT(`player`) DO UPDATE SET json=?;");
			st.setString(1, key);
			st.setString(2, QuestPlugin.inst().getGson().toJson(value));
			st.execute();
			st.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<PlayerQuest> select(String key) {
		try {
			final PreparedStatement st = this.conn.prepareStatement("SELECT * FROM players WHERE player=?;");
			final ResultSet result = st.executeQuery();
			final String json = result.getString("json");
			result.close();
			st.close();
			return QuestPlugin.inst().getGson().fromJson(json, new ArrayList<PlayerQuest>().getClass());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}
