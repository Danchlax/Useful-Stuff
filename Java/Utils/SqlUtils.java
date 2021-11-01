package monitora.qa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import monitora.qa.core.TestProperties;

public class SqlUtils {

	public static void checkDbConnection() throws SQLException{
			System.out.println("Connecting to orders_dev database...");
			Connection connection = DriverManager.getConnection(TestProperties.ORDERS_DB_CONNECTION_STRING,
					TestProperties.ORDERS_DB_USERNAME, TestProperties.ORDERS_DB_PASSWORD);
			System.out.println("Connected successfully!");
			connection.close();
			System.out.println("VPN dependent checks disabled. Skipping DB Check...");

	}
	
	public static ResultSet getOrderExportsByOrderId(String orderId) {
		System.out.println("Fetching Order Exports for order ID "+ orderId +"...");
		ResultSet orderExports = getSqlQueryResults("");
		if(orderExports != null)
			System.out.println("Order Exports fetched!");
		return orderExports;
	}

	public static ResultSet getOrderExportsByRequestId(String requestId) {
		System.out.println("Fetching Order Exports for request ID "+ requestId +"...");
		ResultSet orderExports = getSqlQueryResults("");
		if(orderExports != null)
			System.out.println("Order Exports fetched!");
		return orderExports;
	}

	public static ResultSet getLegExportsByRequestId(String requestId) {
		System.out.println("Fetching Leg Exports for request ID "+ requestId +"...");
		ResultSet legExports = getSqlQueryResults("");
		if(legExports != null)
			System.out.println("Leg Exports fetched!");
		return legExports;
	}

	public static ResultSet getSqlQueryResults(String sqlQuery) {
			try (Connection connection = DriverManager.getConnection(TestProperties.ORDERS_DB_CONNECTION_STRING,
					TestProperties.ORDERS_DB_USERNAME, TestProperties.ORDERS_DB_PASSWORD)){
				Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet = statement.executeQuery(sqlQuery);
				connection.close();
				return resultSet;

			} catch (SQLException e) {
				System.out.println("Error fetching query results");
				e.printStackTrace();
			}

			System.out.println("VPN dependent checks disabled. Skipping DB Check...");
		return null;
	}

	public static List<String> getStringListFromResultSet(ResultSet results, String columnName) {
		List<String> resultList = new ArrayList<String>();
		try {
			results.beforeFirst();
			while (results.next()) {
				resultList.add(results.getString(columnName));
			}
		} catch (SQLException e) {
			System.out.println("Error iterating through orders");
			e.printStackTrace();
		}
		return resultList;
	}

	public static List<Boolean> getBooleanListFromResultSet(ResultSet results, String columnName) {
		List<Boolean> resultList = new ArrayList<Boolean>();
		try {
			results.beforeFirst();
			while (results.next()) {
				resultList.add(results.getBoolean(columnName));
			}
		} catch (SQLException e) {
			System.out.println("Error iterating through orders");
			e.printStackTrace();
		}
		return resultList;
	}
}
