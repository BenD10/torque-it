package ca.bendennerley.torque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
	private static final Logger logger = LoggerFactory.getLogger(Upload.class.getName());
	private static Connection getMySQL() throws Exception{
		Connection conn = null;
		try {
			String host = "localhost";
			String database = "torque-it";
			String username = "torque-it";
			String password = "****";
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?user=" + username + "&password=" + password + "&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST");
		    
		} catch (SQLException ex) {
		    logger.error("SQLException: {}", ex.getMessage());
		    logger.error("SQLState: {}", ex.getSQLState());
		    logger.error("VendorError: {}", ex.getErrorCode());
		    throw new SQLException();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new Exception();
		} 
		return conn;
	}
	public static void uploadData(Map<String,String[]> get){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		String columnName = null;
		String columnValue = null;
		List<String> fields = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		try{
			conn = Database.getMySQL();
			stmt = conn.createStatement();
			sql = "SHOW COLUMNS from test";
	        rs = stmt.executeQuery(sql);
	        while(rs.next()){
	        	columnName = rs.getString(1);
	        	if(get.containsKey(columnName)){
	        		columnValue = get.get(columnName)[0];
	        		fields.add(columnName);
	        		values.add(columnValue);
	        	}
	        }
	        sql = "INSERT INTO test (";
	        for(int i = 0; i < fields.size(); i++){
	        	if(i != 0){
	        		sql += ",";
	        	}
	        	sql += fields.get(i);
	        }
	        sql += ") VALUES (";
	        for(int i = 0; i < fields.size(); i++){
	        	if(i != 0){
	        		sql += ",";
	        	}
	        	sql += "?";
	        }
	        sql += ");";
	        PreparedStatement preppedstmt = conn.prepareStatement(sql);
	        for(int i = 1; i <= fields.size(); i++){
	        	preppedstmt.setString(i, values.get(i-1));
	        }
	        logger.debug(preppedstmt.toString());
	        preppedstmt.execute();
		} 
		catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} 
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} 
				catch (SQLException e) {}
			}
			if(conn != null) {
				try {
					conn.close();
				} 
				catch (SQLException e) {}
			}
		}
	}
}
