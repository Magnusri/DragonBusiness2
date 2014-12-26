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
	
	public DBHandler(){
		
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
					  .getConnection("jdbc:mysql://localhost/dragonbusiness?"
							  + "user=minecraft&password=minecraftpass");
			  
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
						  .getConnection("jdbc:mysql://localhost/dragonbusiness?"
								  + "user=minecraft&password=minecraftpass");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET company_company_id=? WHERE player_uuid=?");
				  
				  preparedStatement.setInt(1, companyId);
				  preparedStatement.setString(2, player.getUniqueId().toString());
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			  player.sendMessage(ChatColor.AQUA + "You joined " + companyName + "!");
		      
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
						  .getConnection("jdbc:mysql://localhost/dragonbusiness?"
								  + "user=minecraft&password=minecraftpass");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET player_rank=? WHERE player_uuid=?");
				  
				  preparedStatement.setString(1, rank);
				  preparedStatement.setString(2, player.getUniqueId().toString());
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			  player.sendMessage(ChatColor.AQUA + "Your company rank is now " + rank);
		      
		      close();
			  return true;
	  }
	  
	  public boolean removePlayerFromCompany(Plugin plugin, Player player){
		  
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
						  .getConnection("jdbc:mysql://localhost/dragonbusiness?"
								  + "user=minecraft&password=minecraftpass");
				  
				  preparedStatement = connect
				          .prepareStatement("UPDATE player SET company_company_id=NULL WHERE player_uuid=?");
				  
				  preparedStatement.setString(1, player.getUniqueId().toString());
				  
				  preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			  player.sendMessage(ChatColor.AQUA + "You left the company!");
		      
		      close();
			  return true;
	  }
	  
	  public boolean insertPlayer(Plugin plugin, Player player){
		  
		  insertPlayer(plugin, player, 0.0);
		  
		  return true;
	  }
	  
	  public boolean insertCompany(Plugin plugin, Player player, String companyName, String info){
		  
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
					  .getConnection("jdbc:mysql://localhost/dragonbusiness?"
							  + "user=minecraft&password=minecraftpass");
			  
			  preparedStatement = connect
			          .prepareStatement("INSERT INTO company (company_name, company_value, company_info) VALUES (?, ?, ?)");
			  
			  preparedStatement.setString(1, companyName);
			  preparedStatement.setInt(2, 0);
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
		  
		  insertCompany(plugin, player, companyName, "");
		  
		  return true;
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
			          .getConnection("jdbc:mysql://localhost/dragonbusiness?"
			              + "user=minecraft&password=minecraftpass");

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
	  
	  public ArrayList<DBPlayer> getPlayerList(){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  
		  ArrayList<DBPlayer> playerList = null;
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/dragonbusiness?"
			              + "user=minecraft&password=minecraftpass");

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
	  
	  
	  //NOT DONE
	  public ArrayList<DBPlayer> getPlayerListInCompany(){
		  
		  try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  
		  ArrayList<DBPlayer> playerList = null;
		try {
			connect = DriverManager
			          .getConnection("jdbc:mysql://localhost/dragonbusiness?"
			              + "user=minecraft&password=minecraftpass");

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
	  
	  
	  private ArrayList<DBPlayer> makePlayerList(ResultSet resultSet){
		  	ArrayList<DBPlayer> dbPlayerList = new ArrayList<DBPlayer>();
		    try {
				while (resultSet.next()) {
				  int id = resultSet.getInt("player_id");
				  String uuid = resultSet.getString("player_uuid");
				  String name = resultSet.getString("player_name");
				  String rank = resultSet.getString("player_rank");
				  int companyid = resultSet.getInt("company_company_id");
				  Double earned = resultSet.getDouble("player_earned");
				  
				  DBPlayer dbPlayer = new DBPlayer(id, uuid, name, rank, companyid, earned);
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
		      int value = resultSet.getInt("company_value");
		      String info = resultSet.getString("company_info");
		      
		      DBCompany dbCompany = new DBCompany(id, name, value, info);
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
