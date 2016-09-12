/*****************************
 * Class name: Dao (.java)
 * 
 * Purpose: Dad Class with persist data.
 *****************************/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Dao {

	/**
	 * Method to try open connection with database
	 * 
	 * @return null if failed else connection object
	 * @throws SQLException
	 *             failed in connection
	 */
	private static Connection getMySqlConnection() throws SQLException {

		Connection connection = null; // connection variable

		// Load Default JDBC Driver

		final String driverName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			assert false;
		}

		// Configuration connection

		final String SERVER_NAME = "127.0.0.1"; // Host
		final String DATABASE = "DeManS_db"; // DatabaseName
		final String URL = "jdbc:mysql://" + SERVER_NAME + "/" + DATABASE;
		final String USERNAME = "DeManS"; // UserName
		final String PASSWORD = "demolay"; // Password
		connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		return connection;

	}

	/**
	 * Method to close connection
	 * @return boolean true if success else false  
	 */
	@SuppressWarnings("finally")
	private static boolean closeConnection() {
		boolean isClosed = false;
		
        try {
            getMySqlConnection().close();
            isClosed =  true;
        } catch (SQLException e) {
        	isClosed =  false;
        } finally {
			return isClosed;
		}
    }
	
	/**
	 * Method to prepare and execute query
	 * @param query String with command to execute in database
	 * @return ResultSet with the return of the database
	 * @throws SQLException
	 */
	protected static ResultSet executeQuery(String query) throws SQLException
	{
		// Open Connection
		Connection connection = getMySqlConnection();
		
		// Execute query
		java.sql.PreparedStatement prepareStatement = connection.prepareStatement(query);
		ResultSet queryResult = prepareStatement.executeQuery();
		
		// Close connection
		closeConnection();
		
		return queryResult;
		
	}
}