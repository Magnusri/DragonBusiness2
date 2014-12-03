package me.Magnusri.DragonBusiness2.DBSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class DBHandler {
	
	public DBHandler(){
		
	}
	
	private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
	  
	  public boolean insertPlayer(Plugin plugin, Player player, Double balance) throws ClassNotFoundException, SQLException{
		  
		  for (DBPlayer dbPlayer : getPlayerList()){
			  if (dbPlayer.getUuid().equals(player.getUniqueId().toString())){
				  return false;
			  }
		  }
		  
		  Class.forName("com.mysql.jdbc.Driver");
	      
	      connect = DriverManager
	    		  .getConnection("jdbc:mysql://localhost/dragonbusiness?"
	    				  + "user=minecraft&password=minecraftpass");
	      
	      preparedStatement = connect
		          .prepareStatement("INSERT INTO player (player_uuid, player_name, player_rank, player_earned) VALUES (?, ?, 'none', ?)");
	      
	      preparedStatement.setString(1, player.getUniqueId().toString());
	      preparedStatement.setString(2, player.getName());
	      preparedStatement.setString(3, Double.toString(balance));
	      
	      preparedStatement.executeUpdate();
	      
	      close();
		  return true;
	  }
	  public boolean insertPlayer(Plugin plugin, Player player) throws ClassNotFoundException, SQLException{
		  
		  insertPlayer(plugin, player, 0.0);
		  
		  return true;
	  }
	  
	  public boolean insertCompany(Plugin plugin, String companyName, String info) throws ClassNotFoundException, SQLException{
		  
		  for (DBCompany dbCompany : getCompanyList()){
			  if (dbCompany.getName().equals(companyName)){
				  return false;
			  }
		  }
		  
		  Class.forName("com.mysql.jdbc.Driver");
	      
	      connect = DriverManager
	    		  .getConnection("jdbc:mysql://localhost/dragonbusiness?"
	    				  + "user=minecraft&password=minecraftpass");
	      
	      preparedStatement = connect
		          .prepareStatement("INSERT INTO company (company_name, company_value, company_info) VALUES (?, ?, ?)");
	      
	      preparedStatement.setString(1, companyName);
	      preparedStatement.setInt(2, 0);
	      preparedStatement.setString(3, info);
	      
	      preparedStatement.executeUpdate();
	      
	      close();
		  return true;
	  }
	  public boolean insertCompany(Plugin plugin, String companyName) throws ClassNotFoundException, SQLException{
		  
		  insertCompany(plugin, companyName, "");
		  
		  return true;
	  }
	  
	  public ArrayList<DBCompany> getCompanyList() throws ClassNotFoundException, SQLException{
		  
		  Class.forName("com.mysql.jdbc.Driver");
		  
		  connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/dragonbusiness?"
		              + "user=minecraft&password=minecraftpass");

	      statement = connect.createStatement();
	      
	      resultSet = statement
	          .executeQuery("select * from company");
	      ArrayList<DBCompany> companyList = makeCompanyList(resultSet);
	      close();
	      return companyList;
	  }
	  
	  public ArrayList<DBPlayer> getPlayerList() throws ClassNotFoundException, SQLException{
		  
		  Class.forName("com.mysql.jdbc.Driver");
		  
		  connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/dragonbusiness?"
		              + "user=minecraft&password=minecraftpass");

	      statement = connect.createStatement();
	      
	      resultSet = statement
	          .executeQuery("select * from player");
	      ArrayList<DBPlayer> playerList = makePlayerList(resultSet);
	      close();
	      return playerList;
	  }

	  private ArrayList<DBPlayer> makePlayerList(ResultSet resultSet) throws SQLException {
		  	ArrayList<DBPlayer> dbPlayerList = new ArrayList<DBPlayer>();
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
