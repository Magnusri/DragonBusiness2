package me.Magnusri.DragonBusiness2.DBSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class DBHandler {
	
	public Plugin plugin;
	
	public DBHandler(Plugin plugin){
		this.plugin = plugin;
	}
	
	private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
	  
	  public boolean insertPlayer(Plugin plugin, Player player, Double earned){
		  
		  for (DBPlayer dbPlayer : getPlayerList()){
			  if (dbPlayer.getUuid().equals(player.getUniqueId().toString())){
				  return false;
			  }
		  }
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	      
	      try {
			connect = DriverManager
					  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
			  
			  preparedStatement = connect
			          .prepareStatement("INSERT INTO player (player_uuid, player_name, player_rank, player_earned) VALUES (?, ?, 'none', ?)");
			  
			  preparedStatement.setString(1, player.getUniqueId().toString());
			  preparedStatement.setString(2, player.getName());
			  preparedStatement.setString(3, Double.toString(earned));
			  
			  preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      
	      close();
		  return true;
	  }
	  
	  public boolean addPlayerToCompany(Plugin plugin, Player player, String companyName){
		  
		  boolean playerInDB = false;
		  
		  for (DBPlayer dbPlayer : getPlayerList()){
			  if (dbPlayer.getUuid().equals(player.getUniqueId().toString())){
				  playerInDB = true;
			  }
		  }
		  
		  int companyId = 0;
		  for (DBCompany dbCompany : getCompanyList()){
			  if (dbCompany.getName().equals(companyName)){
				  companyId = dbCompany.getId();
			  }
		  }
		  
		  if (!playerInDB){
			  insertPlayer(plugin, player);
		  }
		  
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET company_company_id=? WHERE player_uuid=?");
				  
				  preparedStatement.setInt(1, companyId);
				  preparedStatement.setString(2, player.getUniqueId().toString());
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean setPlayerRank(Plugin plugin, Player player, String rank){
		  
		  boolean playerInDB = false;
		  
		  for (DBPlayer dbPlayer : getPlayerList()){
			  if (dbPlayer.getUuid().equals(player.getUniqueId().toString())){
				  playerInDB = true;
			  }
		  }
		  
		  if (!playerInDB){
			  insertPlayer(plugin, player);
		  }
		  
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET player_rank=? WHERE player_uuid=?");
				  
				  preparedStatement.setString(1, rank);
				  preparedStatement.setString(2, player.getUniqueId().toString());
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		    close();
			return true;
	  }
	  
	  public boolean setPlayerInvite(Plugin plugin, String player, String companyInvite){
		  
		  boolean playerInDB = false;
		  
		  for (DBPlayer dbPlayer : getPlayerList()){
			  if (dbPlayer.getName().equals(player)){
				  playerInDB = true;
			  }
		  }
		  Player onlinePlayer = null;
		  if (plugin.getServer().getPlayer(player) != null){
			  onlinePlayer = plugin.getServer().getPlayer(player);
		  } else {
			  return false;
		  }
		  
		  if (!playerInDB){
			  insertPlayer(plugin, onlinePlayer);
		  }
		  
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET player_pendingInvite=? WHERE player_name=?");
				  
				  preparedStatement.setString(1, companyInvite);
				  preparedStatement.setString(2, player);
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean setPlayerEarned(Plugin plugin, String player, double newValue){
		  
		  boolean playerInDB = false;
		  
		  for (DBPlayer dbPlayer : getPlayerList()){
			  if (dbPlayer.getName().equals(player)){
				  playerInDB = true;
			  }
		  }
		  Player onlinePlayer = null;
		  if (plugin.getServer().getPlayer(player) != null){
			  onlinePlayer = plugin.getServer().getPlayer(player);
		  } else {
			  return false;
		  }
		  
		  if (!playerInDB){
			  insertPlayer(plugin, onlinePlayer);
		  }
		  
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET player_earned=? WHERE player_name=?");
				  
				  preparedStatement.setDouble(1, newValue);
				  preparedStatement.setString(2, player);
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean setPlayerLevel(Plugin plugin, String player, double newLevel){
		  
		  boolean playerInDB = false;
		  
		  for (DBPlayer dbPlayer : getPlayerList()){
			  if (dbPlayer.getName().equals(player)){
				  playerInDB = true;
			  }
		  }
		  Player onlinePlayer = null;
		  if (plugin.getServer().getPlayer(player) != null){
			  onlinePlayer = plugin.getServer().getPlayer(player);
		  } else {
			  return false;
		  }
		  
		  if (!playerInDB){
			  insertPlayer(plugin, onlinePlayer);
		  }
		  
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET player_level=? WHERE player_name=?");
				  
				  preparedStatement.setDouble(1, newLevel);
				  preparedStatement.setString(2, player);
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean setCompanyValue(Plugin plugin, String company, Double value){
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE company SET company_value=? WHERE company_name=?");
				  
				  preparedStatement.setDouble(1, value);
				  preparedStatement.setString(2, company);
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean setCompanyBankrupt(Plugin plugin, String company, boolean bankrupt){
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE company SET company_bankrupt=? WHERE company_name=?");
				  if (bankrupt)
					  preparedStatement.setString(1, "true");
				  else
					  preparedStatement.setString(1, "false");
					  
				  preparedStatement.setString(2, company);
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean setCompanyInfo(Plugin plugin, String company, String info){
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE company SET company_info=? WHERE company_name=?");
				  
				  preparedStatement.setString(1, info);
				  preparedStatement.setString(2, company);
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean removePlayerFromCompany(Plugin plugin, String player){
		  
			setPlayerEarned(plugin, player, 0.0);
			setPlayerLevel(plugin, player, 0);
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET company_company_id=NULL WHERE player_name=?");
				  
				  preparedStatement.setString(1, player);
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean removePlayerRank(Plugin plugin, String player){
		  
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      
		      try {
				connect = DriverManager
						  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET player_rank='none' WHERE player_name=?");
				  
				  preparedStatement.setString(1, player);
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      
		      close();
			  return true;
	  }
	  
	  public boolean removeCompany(Plugin plugin, int companyId){
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	      
	      try {
			connect = DriverManager.getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
			  
			  preparedStatement = connect.prepareStatement("DELETE FROM company WHERE company_id=?");
			  
			  preparedStatement.setInt(1, companyId);
			  
			  preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      
	      close();
		  return true;
	  }
	  
	  public boolean insertPlayer(Plugin plugin, Player player){
		  
		  insertPlayer(plugin, player, 0.0);
		  
		  return true;
	  }
	  
	  public boolean insertCompany(Plugin plugin, Player player, String companyName, String info, double value){
		  
		  for (DBCompany dbCompany : getCompanyList()){
			  if (dbCompany.getName().equals(companyName)){
				  player.sendMessage(ChatColor.RED + "A company named " + companyName + " already exists!");
				  return false;
			  }
		  }
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	      
	      try {
			connect = DriverManager
					  .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
			  
			  preparedStatement = connect
			          .prepareStatement("INSERT INTO company (company_name, company_value, company_info) VALUES (?, ?, ?)");
			  
			  preparedStatement.setString(1, companyName);
			  preparedStatement.setDouble(2, value);
			  preparedStatement.setString(3, info);
			  
			  preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      
	      close();
	      
	      addPlayerToCompany(plugin, player, companyName);
	      setPlayerRank(plugin, player, "CEO");
	      
		  return true;
	  }
	  
	  public boolean insertCompany(Plugin plugin, Player player, String companyName){
		  
		  insertCompany(plugin, player, companyName, "", 0);
		  
		  return true;
	  }
	  
	  public DBPlayer getPlayer(Player player){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
			  preparedStatement = connect.prepareStatement("select * from player where player_uuid=?");

			  preparedStatement.setString(1, player.getUniqueId().toString());
			  
			  resultSet = preparedStatement.executeQuery();
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBPlayer dbPlayer = null;
		
		try {
			while (resultSet.next()) {
			  int id = resultSet.getInt("player_id");
			  String uuid = resultSet.getString("player_uuid");
			  String name = resultSet.getString("player_name");
			  String rank = resultSet.getString("player_rank");
			  int companyid = resultSet.getInt("company_company_id");
			  String pendingInvite = resultSet.getString("player_pendingInvite");
			  Double earned = resultSet.getDouble("player_earned");
			  Double level = resultSet.getDouble("player_level");
			  
			  dbPlayer = new DBPlayer(plugin, this, id, uuid, name, rank, companyid, pendingInvite, earned, level);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    close();
	    return dbPlayer;
	  }
	  
	  public DBPlayer getPlayer(String playerName){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
			  preparedStatement = connect.prepareStatement("select * from player where player_name=?");

			  preparedStatement.setString(1, playerName);
			  
			  resultSet = preparedStatement.executeQuery();
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBPlayer dbPlayer = null;
		
		try {
			while (resultSet.next()) {
			  int id = resultSet.getInt("player_id");
			  String uuid = resultSet.getString("player_uuid");
			  String name = resultSet.getString("player_name");
			  String rank = resultSet.getString("player_rank");
			  int companyid = resultSet.getInt("company_company_id");
			  String pendingInvite = resultSet.getString("player_pendingInvite");
			  Double earned = resultSet.getDouble("player_earned");
			  Double level = resultSet.getDouble("player_level");
			  
			  dbPlayer = new DBPlayer(plugin, this, id, uuid, name, rank, companyid, pendingInvite, earned, level);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    close();
	    return dbPlayer;
	  }
	  
	  public DBCompany getCompany(String companyName){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
			  preparedStatement = connect.prepareStatement("select * from company where company_name=?");

			  preparedStatement.setString(1, companyName);
			  
			  resultSet = preparedStatement.executeQuery();
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBCompany dbCompany = null;
		
		try {
			while (resultSet.next()) {
			      int id = resultSet.getInt("company_id");
			      String name = resultSet.getString("company_name");
			      double value = resultSet.getDouble("company_value");
			      String info = resultSet.getString("company_info");
			      boolean bankrupt = false;
			      
			      if (resultSet.getString("company_bankrupt").equals("false"))
			    	  bankrupt = false;
			      else
			    	  bankrupt = true;
			      
			      dbCompany = new DBCompany(id, name, value, info, bankrupt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    close();
	    return dbCompany;
	  }
	  
	  public DBCompany getCompany(int companyID){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");
			  preparedStatement = connect.prepareStatement("select * from company where company_id=?");

			  preparedStatement.setInt(1, companyID);
			  
			  resultSet = preparedStatement.executeQuery();
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBCompany dbCompany = null;
		
		try {
			while (resultSet.next()) {
			      int id = resultSet.getInt("company_id");
			      String name = resultSet.getString("company_name");
			      double value = resultSet.getDouble("company_value");
			      String info = resultSet.getString("company_info");
			      boolean bankrupt = false;
			      
			      if (resultSet.getString("company_bankrupt").equals("false"))
			    	  bankrupt = false;
			      else
			    	  bankrupt = true;
			      
			      dbCompany = new DBCompany(id, name, value, info, bankrupt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    close();
	    return dbCompany;
	  }
	  
	  public ArrayList<DBCompany> getCompanyList(){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  
		  ArrayList<DBCompany> companyList = null;
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");

			  statement = connect.createStatement();
			  
			  resultSet = statement
			      .executeQuery("select * from company");
			  companyList = makeCompanyList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      close();
	      return companyList;
	  }
	  
	  public ArrayList<DBCompany> getTopCompanyList(){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  
		  ArrayList<DBCompany> companyList = null;
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");

			  statement = connect.createStatement();
			  
			  resultSet = statement
			      .executeQuery("select * from company ORDER BY company_value*1 DESC LIMIT 10");
			  companyList = makeCompanyList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      close();
	      return companyList;
	  }
	  
	  public ArrayList<DBPlayer> getPlayerList(){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  
		  ArrayList<DBPlayer> playerList = null;
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");

			  statement = connect.createStatement();
			  
			  resultSet = statement
			      .executeQuery("select * from player");
			  playerList = makePlayerList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      close();
	      return playerList;
	  }
	  
	  public ArrayList<DBPlayer> getPlayerListInCompany(String company){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  
		  ArrayList<DBPlayer> playerList = null;
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://mc.dragontechmc.com/dragonbusiness?"
							  + "user=dragonbusiness&password=dragonbusiness123");

			preparedStatement = connect.prepareStatement("select * from player where company_company_id = (select company_id from company where company_name = ?)");
			  
			  preparedStatement.setString(1, company);
			  
			  resultSet = preparedStatement.executeQuery();
			  playerList = makePlayerList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      close();
	      return playerList;
	  }
	  
	  private ArrayList<DBPlayer> makePlayerList(ResultSet resultSet){
		  	ArrayList<DBPlayer> dbPlayerList = new ArrayList<DBPlayer>();
		    try {
				while (resultSet.next()) {
				  int id = resultSet.getInt("player_id");
				  String uuid = resultSet.getString("player_uuid");
				  String name = resultSet.getString("player_name");
				  String rank = resultSet.getString("player_rank");
				  int companyid = resultSet.getInt("company_company_id");
				  String pendingInvite = resultSet.getString("player_pendingInvite");
				  Double earned = resultSet.getDouble("player_earned");
				  Double level = resultSet.getDouble("player_level");
				  
				  DBPlayer dbPlayer = new DBPlayer(plugin, this, id, uuid, name, rank, companyid, pendingInvite, earned, level);
				  dbPlayerList.add(dbPlayer);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return dbPlayerList;
	  }
	  
	  private ArrayList<DBCompany> makeCompanyList(ResultSet resultSet) throws SQLException {
		  	ArrayList<DBCompany> dbCompanyList = new ArrayList<DBCompany>();
		    while (resultSet.next()) {
		      int id = resultSet.getInt("company_id");
		      String name = resultSet.getString("company_name");
		      double value = resultSet.getDouble("company_value");
		      String info = resultSet.getString("company_info");
		      boolean bankrupt = false;
		      
		      if (resultSet.getString("company_bankrupt").equals("false"))
		    	  bankrupt = false;
		      else
		    	  bankrupt = true;
		      
		      DBCompany dbCompany = new DBCompany(id, name, value, info, bankrupt);
		      dbCompanyList.add(dbCompany);
		    }
		    return dbCompanyList;
	  }

	  // you need to close all three to make sure
	  private void close() {
	    close(resultSet);
	    close(statement);
	    close(connect);
	  }
	  
	  private void close(ResultSet c) {
	    try {
	      if (c != null) {
	        c.close();
	      }
	    } catch (Exception e) {
	    // don't throw now as it might leave following closables in undefined state
	    }
	  }
	  private void close(Statement c) {
		    try {
		      if (c != null) {
		        c.close();
		      }
		    } catch (Exception e) {
		    // don't throw now as it might leave following closables in undefined state
		    }
		  }
	  private void close(Connection c) {
		    try {
		      if (c != null) {
		        c.close();
		      }
		    } catch (Exception e) {
		    // don't throw now as it might leave following closables in undefined state
		    }
		  }
}
