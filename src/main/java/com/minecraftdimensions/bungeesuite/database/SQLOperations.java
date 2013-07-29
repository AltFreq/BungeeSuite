package com.minecraftdimensions.bungeesuite.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLOperations {

	protected synchronized int standardQuery(String query, Connection connection) throws SQLException{
		Statement statement = null;

			statement = connection.createStatement();

		int rowsUpdated = 0;
			rowsUpdated = statement.executeUpdate(query);

			statement.close();
		int rows = rowsUpdated;
		return rows;
	}

	protected synchronized ResultSet sqlQuery(String query, Connection connection) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet result = null;
		try {
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	protected synchronized boolean checkTable(String table, Connection connection) {
		DatabaseMetaData dbm = null;
		try {
			dbm = connection.getMetaData();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ResultSet tables = null;
		try {
			tables = dbm.getTables(null, null, table, null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean check = false;
		try {
			check = tables.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}

}