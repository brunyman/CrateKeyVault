package net.craftersland.crate.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import net.craftersland.crate.CKV;

public class MysqlSetup {
	
	private CKV plugin;
	private Connection conn = null;
	
	public MysqlSetup(CKV as) {
		this.plugin = as;
		setupDatabase();
		databaseMaintenanceTask();
	}
	
	public Connection getConnection() {
		checkConnection();
		return conn;
	}
	
	private void checkConnection() {
		try {
			if (conn == null) {
				CKV.log.warning("Database connection failed. Reconnecting...");
				conn = null;
				connectToDatabase();
			} else if (!conn.isValid(3)) {
				CKV.log.warning("Database connection failed. Reconnecting...");
				conn = null;
				connectToDatabase();
			} else if (conn.isClosed() == true) {
				CKV.log.warning("Database connection failed. Reconnecting...");
				conn = null;
				connectToDatabase();
			}
		} catch (Exception e) {
			CKV.log.severe("Error re-connecting to the database! Error: " + e.getMessage());
		}
	}
	
	public void closeConnection() {
		try {
			CKV.log.info("Closing database connection...");
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setupDatabase() {
		connectToDatabase();
		setupTables();
	}
	
	public void setupTables() {
		PreparedStatement query1 = null;
		try {
			String data = "CREATE TABLE IF NOT EXISTS `" + plugin.getConfigHandler().getString("Database.Mysql.TableName") + "` (id INT UNSIGNED NOT NULL AUTO_INCREMENT, player_uuid char(36) UNIQUE NOT NULL, player_name varchar(16) NOT NULL, vault LONGTEXT NOT NULL, last_seen char(13) NOT NULL, PRIMARY KEY(id));";
	        query1 = conn.prepareStatement(data);
	        query1.execute();
		} catch (Exception e) {
			CKV.log.severe("Error creating tables! Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (query1 != null) {
					query1.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void connectToDatabase() {
		try {
       	 	//Load Drivers
            Class.forName("com.mysql.jdbc.Driver");
            
            String passFix = plugin.getConfigHandler().getString("Database.Mysql.Password").replaceAll("%", "%25");
            String passFix2 = passFix.replaceAll("\\+", "%2B");
            
            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getConfigHandler().getString("Database.Mysql.Host") + ":" + plugin.getConfigHandler().getString("Database.Mysql.Port") + "/" + plugin.getConfigHandler().getString("Database.Mysql.DatabaseName") + "?" + "user=" + plugin.getConfigHandler().getString("Database.Mysql.User") + "&" + "password=" + passFix2);
            CKV.log.info("Database connection established!");
          } catch (ClassNotFoundException e) {
        	  CKV.log.severe("Could not locate drivers for mysql! Error: " + e.getMessage());
          } catch (SQLException e) {
        	  CKV.log.severe("Could not connect to mysql database! Error: " + e.getMessage());
          } catch (Exception ex) {
        	  ex.printStackTrace();
          }
	}
	
	private void databaseMaintenanceTask() {
		if (plugin.getConfigHandler().getBoolean("Database.RemoveInactiveUsers.enabled") == true) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

				@Override
				public void run() {
					if (conn != null) {
						long inactivityDays = Long.parseLong(plugin.getConfigHandler().getString("Database.RemoveInactiveUsers.inactivity"));
						long inactivityMils = inactivityDays * 24 * 60 * 60 * 1000;
						long curentTime = System.currentTimeMillis();
						long inactiveTime = curentTime - inactivityMils;
						CKV.log.info("Database maintenance task started...");
						tableMaintenance(inactiveTime, getConnection(), plugin.getConfigHandler().getString("Database.Mysql.TableName"));
						CKV.log.info("Database maintenance complete!");
					}
				}
				
			}, 100 * 20L);
		}
	}
	
	private void tableMaintenance(long inactiveTime, Connection conn, String tableName) {
		PreparedStatement preparedStatement = null;
		try {
			String sql = "DELETE FROM `" + tableName + "` WHERE `last_seen` < ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, String.valueOf(inactiveTime));
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
