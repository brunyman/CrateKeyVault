package net.craftersland.crate.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import net.craftersland.crate.CKV;

public class StorageHandler {
	
	private CKV pl;
	
	public StorageHandler(CKV pl) {
		this.pl = pl;
	}
	
	public boolean setData(Connection conn, UUID playerUUID, String playerName, String vault) {
		if (!hasAccount(conn, playerUUID)) {
			createAccount(conn, playerUUID, playerName);
		}
		PreparedStatement preparedUpdateStatement = null;
		if (conn != null) {
			try {
				String data = "UPDATE `" + pl.getConfigHandler().getString("Database.Mysql.TableName") + "` " + "SET `player_name` = ?" + ", `vault` = ?" + ", `last_seen` = ?" + " WHERE `player_uuid` = ?";
				preparedUpdateStatement = conn.prepareStatement(data);
				preparedUpdateStatement.setString(1, playerName);
				preparedUpdateStatement.setString(2, vault);
				preparedUpdateStatement.setString(3, String.valueOf(System.currentTimeMillis()));
				preparedUpdateStatement.setString(4, playerUUID.toString());
				preparedUpdateStatement.executeUpdate();
				return true;
			} catch (SQLException e) {
				CKV.log.warning("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (preparedUpdateStatement != null) {
						preparedUpdateStatement.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
        return false;
	}
	
	public String getData(Connection conn, UUID playerUUID) {
		PreparedStatement preparedUpdateStatement = null;
		ResultSet result = null;
		if (conn != null) {
			try {
				String sql = "SELECT `vault` FROM `" + pl.getConfigHandler().getString("Database.Mysql.TableName") + "` WHERE `player_uuid` = ? LIMIT 1";
		        preparedUpdateStatement = conn.prepareStatement(sql);
		        preparedUpdateStatement.setString(1, playerUUID.toString());
		        result = preparedUpdateStatement.executeQuery();
		        while (result.next()) {
		        	return result.getString("vault");
		        }
		    } catch (SQLException e) {
		    	CKV.log.warning("Error: " + e.getMessage());
				e.printStackTrace();
		    } finally {
		    	try {
		    		if (result != null) {
		    			result.close();
		    		}
		    		if (preparedUpdateStatement != null) {
		    			preparedUpdateStatement.close();
		    		}
		    	} catch (Exception e) {
		    		e.printStackTrace();
		    	}
		    }
		}
		return null;
	}
	
	public boolean createAccount(Connection conn, UUID playerUUID, String playerName) {
		PreparedStatement preparedStatement = null;
		if (conn != null) {
			try {
				String sql = "INSERT INTO `" + pl.getConfigHandler().getString("Database.Mysql.TableName") + "`(`player_uuid`, `player_name`, `vault`, `last_seen`) " + "VALUES(?, ?, ?, ?)";
		        preparedStatement = conn.prepareStatement(sql);
		        preparedStatement.setString(1, playerUUID.toString());
		        preparedStatement.setString(2, playerName);
		        preparedStatement.setString(3, "none");
		        preparedStatement.setString(4, String.valueOf(System.currentTimeMillis()));
		        preparedStatement.executeUpdate();
		        return true;
		      } catch (SQLException e) {
		    	  CKV.log.warning("Error: " + e.getMessage());
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
		return false;
	}
	
	public boolean hasAccount(Connection conn, UUID playerUUID) {
		PreparedStatement preparedUpdateStatement = null;
		ResultSet result = null;
		if (conn != null) {
			try {
		        String sql = "SELECT `player_uuid` FROM `" + pl.getConfigHandler().getString("Database.Mysql.TableName") + "` WHERE `player_uuid` = ? LIMIT 1";
		        preparedUpdateStatement = conn.prepareStatement(sql);
		        preparedUpdateStatement.setString(1, playerUUID.toString());
		        
		        result = preparedUpdateStatement.executeQuery();
		        while (result.next()) {
		        	return true;
		        }
		      } catch (SQLException e) {
		    	  CKV.log.warning("Error: " + e.getMessage());
		      } finally {
		    	  try {
		    		  if (result != null) {
		    			  result.close();
		    		  }
		    		  if (preparedUpdateStatement != null) {
		    			  preparedUpdateStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return false;
	}

}
