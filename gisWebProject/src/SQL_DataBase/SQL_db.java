package SQL_DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//import java.util.Set;
import java.sql.Time;

//import org.json.JSONObject;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class SQL_db {
	private Connection connection;
	private Statement statement;
	private static final int maxCMID = 20;

	// ctor
	public SQL_db() {
		try {
			connect();
			// create tables for all the data
			statement.execute("USE GIS_DB;");
			statement.execute("CREATE TABLE IF NOT EXISTS updatedLocation (cmid VARCHAR(20), x DOUBLE(9,7), y DOUBLE(9,7), eventID VARCHAR(20), createdDate DATE, createdTime TIME, lastUpdatedDate DATE, lastUpdatedTime TIME);");/*  */
			statement.execute("CREATE TABLE IF NOT EXISTS locationHistory (cmid VARCHAR(20), x DOUBLE(9,7), y DOUBLE(9,7), createdDate DATE, createdTime TIME, lastUpdatedDate DATE, lastUpdatedTime TIME);");
			statement.execute("CREATE TABLE IF NOT EXISTS decisionTable (eventID VARCHAR(20), cmid VARCHAR(20), x DOUBLE(9,7), y DOUBLE(9,7), state VARCHAR(20), region_type INT, medical_condition VARCHAR(30), age DOUBLE(5,2), radius INT);");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		// disconnect
		finally {
			disconnect();
		}
	}

	// TODO
	//function that calculate the type of the region
	public int getregion_type() {
		int region_type = 0;
		return region_type;
	}

	// TODO
	// if we dont have a radius we take a radius from the decision table
	public int getRadiusFromDesicionTable(String eventID, String cmid,
			double x, double y, String state, int region_type,
			String medical_condition_description, double age) {
		// for test at future need to insert func
		int radius = 20;

		return radius;
	}

	// get a list of cmid were there locations between the radius
	public List<String> getCMIDByRadius(String communityMember, int radius,
			double x, double y) {
		List<String> cmidAtRadius = new ArrayList<String>();
		int countCMIDAtRadius = 0;
		double secondX, secondY;
		String cmid;
		String secondCmid;
		double distance;
		try {
			connect();
			statement.execute("USE GIS_DB;");
			// query that calculate the distance by kilometer from users
			ResultSet rs = statement.executeQuery("SELECT cmid, "
					+ "(6371 * acos (cos ( radians(" + x
					+ ") )* cos( radians( x ) )*"
					+ " cos( radians( y ) - radians(" + y
					+ ") )+ sin ( radians(" + x + ") )"
					+ "* sin( radians( x ) ))) AS distance "
					+ "FROM updatedLocation HAVING distance < " + radius
					+ " ORDER BY distance LIMIT 0 , 20;");
			//if the distance smaller than the radius we add the cmid to our list
			while (rs.next()) {
				cmid = rs.getString("cmid");
				if (!communityMember.equals(cmid))
					cmidAtRadius.add(cmid);
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		// disconnect
		finally {
			disconnect();
		}
		//return the list of the closet cmid
		return cmidAtRadius;
	}

	// get a cmid by point of location
	public String getCMIDByPoint(double x, double y) {
		String cmidNum = "";
		try {
			connect();
			statement.execute("USE GIS_DB;");
			//get from updatelocation table the cmid by value of x and y
			ResultSet rs = statement
					.executeQuery("SELECT * FROM updatedLocation WHERE x='" + x
							+ "' AND y='" + y + "';");
			if (!rs.next()) {
				cmidNum = null;
			} else {
				cmidNum = rs.getString("cmid");
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			disconnect();
		}
		//return the rellevant cmid
		return cmidNum;
	}

	// update location table with a new location that we got from the server
	public void updateLocation(String cmid, double x, double y) {
		try {
			connect();
			statement.execute("USE GIS_DB;");
			//get event id by cmid
			String ID = getEventID(cmid);
			//get the rellevant cmid
			ResultSet rs = statement
					.executeQuery("SELECT * FROM updatedLocation WHERE cmid='"
							+ cmid + "';");
			//if the cmid not exist in our db we will create a new row
			if (!rs.next()) {
				//if the cmid not in emergency proccess we insert with eventid=null 
				if (ID == null) {
					statement
							.executeUpdate("INSERT INTO updatedLocation VALUES ('"
									+ cmid
									+ "',"
									+ x
									+ ","
									+ y
									+ ",NULL,CURDATE(),CURTIME(),CURDATE(),CURTIME());");
					//else we insert with the eventid
				} else {
					statement
							.executeUpdate("INSERT INTO updatedLocation VALUES ('"
									+ cmid
									+ "',"
									+ x
									+ ","
									+ y
									+ ","
									+ ID
									+ ",CURDATE(),CURTIME(),CURDATE(),CURTIME());");
				}
				//if we already have the cmid in our db we need to update two tables
			} else {
				//get the values of that cmid from the db
				double x_val = rs.getDouble("x");
				double y_val = rs.getDouble("y");
				String cmid_val = rs.getString("cmid");
				Date date_val = rs.getDate("createdDate");
				Time time_val = rs.getTime("createdTime");
				Date lastUpdatedDate_val = rs.getDate("lastUpdatedDate");
				Time lastUpdatedTime_val = rs.getTime("lastUpdatedTime");
				// if the location changed we insert the old to location history table
				if ((x != x_val) || (y != y_val)) {
					statement
							.executeUpdate("INSERT INTO locationHistory VALUES ('"
									+ cmid_val
									+ "',"
									+ x_val
									+ ","
									+ y_val
									+ ",'"
									+ date_val
									+ "','"
									+ time_val
									+ "','"
									+ lastUpdatedDate_val
									+ "','"
									+ lastUpdatedTime_val + "');");
					//and insert the new values to updatelocation table
					if (ID != null) {
						statement
								.executeUpdate("UPDATE updatedLocation SET x="
										+ x
										+ ", y="
										+ y
										+ ",eventID="
										+ ID
										+ ", createdDate = CURDATE(), createdTime = CURTIME(), lastUpdatedDate = CURDATE(), lastUpdatedTime = CURTIME() WHERE cmid='"
										+ cmid + "';");
					} else {
						statement
								.executeUpdate("UPDATE updatedLocation SET x="
										+ x
										+ ", y="
										+ y
										+ ",eventID=NULL, createdDate = CURDATE(), createdTime = CURTIME(), lastUpdatedDate = CURDATE(), lastUpdatedTime = CURTIME() WHERE cmid='"
										+ cmid + "';");
					}
				} else {// the location didn't changed we update the time and the date
					statement
							.executeUpdate("UPDATE updatedLocation SET lastUpdatedDate = CURDATE(), lastUpdatedTime = CURTIME() WHERE cmid='"
									+ cmid + "';");
				}
			}
		}

		catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		// disconnect
		finally {
			disconnect();
		}
	}

	// update decision table 
	public void updateDecisionTable(String eventID, String cmid, double x,
			double y, String states, int region_type,
			String medical_condition_description, double age, int radius) {
		try {
			updateLocation(cmid, x, y);
			updateEmergency(cmid, eventID);
			connect();
			statement.execute("USE GIS_DB;");

			ResultSet rs = statement
					.executeQuery("SELECT * FROM decisionTable WHERE eventID='"
							+ eventID + "';");

			if (!rs.first()) {
				statement.executeUpdate("INSERT INTO decisionTable VALUES ('"
						+ eventID + "','" + cmid + "'," + x + "," + y + ",'"
						+ states + "','" + region_type + "','"
						+ medical_condition_description + "'," + age + ", "
						+ radius + ");");
			} else {
				statement.executeUpdate("UPDATE decisionTable SET eventID="
						+ eventID + ", cmid=" + cmid + ", x=" + x + ", y=" + y
						+ ", state=" + "\"" + states + "\"" + ", region_type="
						+ region_type + ", medical_condition=" + "\""
						+ medical_condition_description + "\"" + ", age=" + age
						+ ", radius=" + radius + " WHERE eventID='" + eventID
						+ "';");
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		// disconnect
		finally {
			disconnect();

		}
	}

	public void setRadiusDecisionTable(int radius, String eventID) {
		try {
			connect();
			statement.execute("USE GIS_DB;");
			statement.executeUpdate("UPDATE decisionTable SET radius=" + radius
					+ " WHERE eventID='" + eventID + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// disconnect
		finally {
			disconnect();

		}
	}

	// check if the cmid at routine process return null or emergency process
	// return eventID
	public String checkRoutineOrEmerg(String cmid) {
		String eventID = "";
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs = statement
					.executeQuery("SELECT * FROM updatedLocation WHERE cmid='"
							+ cmid + "';");
			if (!rs.next()) {
				eventID = "null";
			} else {

				eventID = rs.getString("eventID");
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return eventID;
	}

	
	// status of cmid change to emergency
	public void updateEmergency(String cmid, String eventID) {
		try {
			connect();
			statement.execute("USE GIS_DB;");
			statement.executeUpdate("UPDATE updatedLocation SET eventID="
					+ eventID + " WHERE cmid='" + cmid + "';");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// status of cmid change to routine
	public void updateRoutine(String cmid) {
		try {
			connect();

			statement.execute("USE GIS_DB;");
			statement
					.executeUpdate("UPDATE updatedLocation SET eventID=NULL WHERE cmid='"
							+ cmid + "';");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	//get event id from update location table by cmid
	public String getEventID(String cmid) {
		String eventID = "";
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs = statement
					.executeQuery("SELECT * FROM updatedLocation WHERE cmid='"
							+ cmid + "';");
			if (!rs.next()) {
				return null;
			} else {
				eventID = rs.getString("eventID");
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {

		}
		return eventID;
	}

	//get list of all the keys from the json object
	public ArrayList<String> getListOfKeys(org.json.JSONObject jsonObject) {
		ArrayList<String> keyList = new ArrayList<String>();
		Iterator a = jsonObject.keys();
		while (a.hasNext()) {
			String key = (String) a.next();
			keyList.add(key);
		}
		return keyList;
	}

	//change the status of the all members in event fron emergency to routine
	public void routineAllMembersByEventID(String eventID) {
		String cmid = "";
		try {
			connect();
			statement.execute("USE GIS_DB;");
			//get the line of the event id from update location table
			ResultSet rs = statement
					.executeQuery("SELECT * FROM updatedLocation WHERE eventID='"
							+ eventID + "';");
			//get the number of the row of this query
			int count = rs.getRow();
			//TODO we need this print????????????
//			System.out.println(count);
			//change for each member from emergency to routine
			while (rs.next()) {
				cmid = rs.getString("cmid");
				updateRoutine(cmid);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			disconnect();
		}
	}

	//if we want to remove event
	public void deleteEvent(String eventID) {
		try {
			connect();
			statement.execute("USE GIS_DB;");
			//find the event in the decision table
			ResultSet rs = statement
					.executeQuery("SELECT * FROM decisionTable WHERE eventID='"
							+ eventID + "';");
			if (rs == null) {
				// eventID not found
			} else {
				//remove the line of this event
				String delete = "DELETE FROM decisionTable "
						+ "WHERE eventID='" + eventID + "'";
				statement.executeUpdate(delete);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	//get a cmid by eventid
	public String getCmidByEventId(String eventID) {
		String cmidNum = "";
		try {
			connect();
			statement.execute("USE GIS_DB;");
			//get from daecision table the line with our eventid
			ResultSet rs = statement
					.executeQuery("SELECT * FROM decisionTable WHERE eventID='"
							+ eventID + "';");
			//if we havnt this eventid return null
			if (!rs.next()) {
				cmidNum = "null";
			//if the eventid in the db we get from it the values
			} else {
				cmidNum = rs.getString("cmid");
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return cmidNum;
	}

	//get a location point by cmid
	public double[] getPointByCmid(String cmid) {
		double[] point = new double[2];
		try {
			connect();
			statement.execute("USE GIS_DB;");
			//get from update location the line with our cmid
			ResultSet rs = statement
					.executeQuery("SELECT * FROM updatedLocation WHERE cmid='"
							+ cmid + "';");
			//if we havnt this cmid return null
			if (!rs.next()) {
				point = null;
			//if the cmid in the db we get from it the values
			} else {
				point[0] = rs.getDouble("x");
				point[1] = rs.getDouble("y");
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return point;
	}

	//get the radius of the event by eventid
	public int getRadiusByEventID(String eventID) {
		int radius = 0;
		try {
			connect();
			statement.execute("USE GIS_DB;");
			//get from the decision table the radius of our event
			ResultSet rs = statement
					.executeQuery("SELECT * FROM decisionTable WHERE eventID='"
							+ eventID + "';");
			//if we havnt an event at the db the default radius will be 0
			if (!rs.next()) {
				radius = 0;
			//else get from the db
			} else {
				radius = rs.getInt("radius");
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return radius;
	}

	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String dbUrl = "jdbc:mysql://localhost";
			connection = DriverManager.getConnection(dbUrl, "root", "");
			MysqlDataSource ds = new MysqlConnectionPoolDataSource();
			ds.setServerName("localhost");
			ds.setDatabaseName("GIS_DB");
			statement = connection.createStatement();
			String dbName = new String("GIS_DB");
			statement.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	private void disconnect() {
		// finally block used to close resources
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException se2) {
		}// nothing we can do
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}// end finally try
	}

}
