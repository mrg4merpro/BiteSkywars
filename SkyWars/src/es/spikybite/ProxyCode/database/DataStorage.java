package es.spikybite.ProxyCode.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import es.spikybite.ProxyCode.Skywars;
import es.spikybite.ProxyCode.player.SPlayer;
import es.spikybite.ProxyCode.storage.YMLStorage;


public class DataStorage{
	 public void saveData(){
			if(Skywars.useData()){
				try {
					Statement s = Skywars.getData().createStatement();
				     s.executeUpdate("CREATE TABLE IF NOT EXISTS `player_data` (`uuid` VARCHAR(36), `name` VARCHAR(32), `kills` INT, `deaths` INT, `wins` INT, `games` INT, `break` INT, `placed` INT, `steps` INT, `shots` INT, `glass` VARCHAR(28), `trail` VARCHAR(28));");
				 	 s.close();
				 	 System.out.println("Database create table done!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		 
		 public void createData(Player p){
			 if(Skywars.useData()){
				  try{
						
						Statement s = Skywars.getData().createStatement();
						ResultSet rs = s.executeQuery("SELECT * FROM `player_data` WHERE uuid = '"+p.getUniqueId()+"';");
						if(!rs.next()){
							s.executeUpdate("INSERT INTO `player_data` (`uuid`, `name`, `kills`, `deaths`, `wins`, `games` ,`break` ,`placed` ,`steps` , `shots`, `glass`, `trail`) VALUES( '"+p.getUniqueId()+"','"+p.getName()+"', '0', '0', '0', '0', '0', '0', '0', '0', 'normal', 'normal');");
						    s.close();
						}
						
					    }catch(Exception e){
						e.printStackTrace();
					    }
			 }else {
				 YMLStorage yml = new YMLStorage(p);
				 yml.createDataPlayer();
				 System.out.println("File created!");
			 }
		 }
		 
		 public void loadData(final SPlayer player) {
			  Bukkit.getScheduler().runTaskAsynchronously(Skywars.pl, new Runnable() {
		          @Override
		          public void run() {
		            if(Skywars.useData()){
		                  Connection connection = Skywars.getData();
		                  PreparedStatement preparedStatement = null;
		                  ResultSet resultSet = null;

		                  try {
		                      StringBuilder queryBuilder = new StringBuilder();
		                      queryBuilder.append("SELECT `name`, `kills`, `deaths`, `wins`, `games`, `break`, `placed`, `steps`, `shots`, `glass`, `trail` ");
		                      queryBuilder.append("FROM `player_data` ");
		                      queryBuilder.append("WHERE `uuid` = ? ");
		                      queryBuilder.append("LIMIT 1;");

		                      preparedStatement = connection.prepareStatement(queryBuilder.toString());
		                      preparedStatement.setString(1, player.getPlayer().getUniqueId().toString());
		                      resultSet = preparedStatement.executeQuery();

		                      if (resultSet != null && resultSet.next()) {
		                          player.setGames(resultSet.getInt("games"));
		                          player.setWins(resultSet.getInt("wins"));
		                          player.setKills(resultSet.getInt("kills"));
		                          player.setDeaths(resultSet.getInt("deaths"));
		                          player.setPlacedBlocks(resultSet.getInt("placed"));
		                          player.setBreakBlocks(resultSet.getInt("break"));
		                          player.setWalked(resultSet.getInt("steps"));
		                          player.addShots(resultSet.getInt("shots"));
		                          if (resultSet.getString("glass") != null) {
			                            player.setGlass(resultSet.getString("glass"));
		                          } else {
		                          	player.setGlass("normal");
		                          }
		                      
		                          if (resultSet.getString("trail") != null) {
			                            player.setTrail(resultSet.getString("trail"));
		                          } else {
		                          	player.setTrail("normal");
		                          }  
		                      }

		                  } catch (final SQLException sqlException) {
		                      sqlException.printStackTrace();
		                  }finally {
		                      if (preparedStatement != null) {
		                          try {
		                              preparedStatement.close();
		                          } catch (final SQLException ignored) {
		                          }
		                      }
		                  }
		                  
		            }else {
		            	YMLStorage data = new YMLStorage(player.getPlayer());
		            	player.setGames(data.get().getInt("games"));
		            	player.setWins(data.get().getInt("wins"));
		            	player.setKills(data.get().getInt("kills"));
		            	player.setDeaths(data.get().getInt("deaths"));
		            	player.setPlacedBlocks(data.get().getInt("placed"));
		            	player.setBreakBlocks(data.get().getInt("break"));
		            	player.setWalked(data.get().getInt("steps"));
		            	player.addShots(data.get().getInt("shots"));
		            	player.setGlass(data.get().getString("glass"));
		            	player.setTrail(data.get().getString("trail"));
		            	
		            
		            }

//		Skywars.getHoloPlayer().addHoloStats(player);
		          }
		              
		              
		      });

		 }
		 public void unloadData(final SPlayer player) {
			
		           if(Skywars.useData()){
		        	

		               Connection connection = Skywars.getData();
		               PreparedStatement preparedStatement = null;


		               try {
		               	 StringBuilder queryBuilder = new StringBuilder();
		                    queryBuilder.append("UPDATE `player_data` SET ");
		                    queryBuilder.append(" `games` = ?, `wins` = ?, ");
		                    queryBuilder.append("`kills` = ?, `deaths` = ?, ");
		                    queryBuilder.append("`placed` = ?, `break` = ?, `steps` = ?, `shots` = ?,  `glass` = ?, `trail` = ?");
		                    queryBuilder.append("WHERE `uuid` = ?;");

		                    preparedStatement = connection.prepareStatement(queryBuilder.toString());
		                    preparedStatement.setInt(1, player.getGames());
		                    preparedStatement.setInt(2, player.getWins());
		                    preparedStatement.setInt(3, player.getKills());
		                    preparedStatement.setInt(4, player.getDeaths());
		                    preparedStatement.setInt(5, player.getBlockPlaceds());
		                    preparedStatement.setInt(6, player.getBlockBreaks());
		                    preparedStatement.setInt(7, player.getWalked());
		                    preparedStatement.setInt(8, player.getShots());
		                    preparedStatement.setString(9, player.getGlass());
		                    preparedStatement.setString(10, player.getTrail());
		                    preparedStatement.setString(11, player.getPlayer().getUniqueId().toString());
		                    preparedStatement.executeUpdate();

		               } catch (final SQLException sqlException) {
		                   sqlException.printStackTrace();

		               } finally {
		                   if (preparedStatement != null) {
		                       try {
		                           preparedStatement.close();
		                       } catch (final SQLException ignored) {
		                       }
		                   }
		               }
		           }else {
		        	   YMLStorage data = new YMLStorage(player.getPlayer());
		           	data.get().set("games", player.getGames());
		           	data.get().set("wins", player.getWins());
		           	data.get().set("kills", player.getKills());
		           	data.get().set("deaths", player.getDeaths());
		          data.get().set("placed", player.getBlockPlaceds());
		           	data.get().set("break", player.getBlockBreaks());
		           	data.get().set("steps", player.getWalked());
		           	data.get().set("shots", player.getShots());
		           	data.get().set("glass", player.getGlass());
		           	data.get().set("trail", player.getTrail());
		           data.save();
		           }

		         }
		             
}