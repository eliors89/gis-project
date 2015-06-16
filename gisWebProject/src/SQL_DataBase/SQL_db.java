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
import java.util.Set;
import java.sql.Time;

import org.json.simple.JSONObject;

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
			statement.execute("USE GIS_DB;");
			statement.execute("CREATE TABLE IF NOT EXISTS updatedLocation (cmid VARCHAR(20), x DOUBLE(9,6), y DOUBLE(9,6), routineOrEmerg INT, createdDate DATE, createdTime TIME, lastUpdatedDate DATE, lastUpdatedTime TIME);");/*  */
			statement.execute("CREATE TABLE IF NOT EXISTS locationHistory (cmid VARCHAR(20), x DOUBLE(9,6), y DOUBLE(9,6), createdDate DATE, createdTime TIME, lastUpdatedDate DATE, lastUpdatedTime TIME);");
			statement.execute("CREATE TABLE IF NOT EXISTS decisionTable (eventID VARCHAR(20), cmid VARCHAR(20), x DOUBLE(9,6), y DOUBLE(9,6), state VARCHAR(20), region_type VARCHAR(20), medical_condition VARCHAR(30), age DOUBLE(5,2), radius INT);");
			statement.execute("CREATE TABLE IF NOT EXISTS emergencyProcess (eventID VARCHAR(20), cmid VARCHAR(20), radius INT, type INT);");
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		// disconnect
		finally
		{
			disconnect();
		}
	}
	//TODO
	public String getregion_type() {
		String region_type = " ";
		return region_type;
	}
	//TODO
	//if we dont have a radius we take radius from decision table
	public int getRadiusFromDesicionTable(String eventID, String cmid, double x, double y, String state, String region_type, String medical_condition_description, float age) {
		int radius=0;
		
		return radius;
	}
	// get cmid that relevant in this radius for emergency process
	public List<String> getCMIDByRadius(int radius, double x, double y) {
		List<String> cmidAtRadius = new ArrayList<String>();
		int countCMIDAtRadius=0;
    	double secondX, secondY;
    	String secondCmid;
    	double distance;
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs=statement.executeQuery("SELECT * FROM updatedLocation;");
	    	//List<Cmid> listOfObjects = new ArrayList<Cmid>();
			while(rs.next() && countCMIDAtRadius < maxCMID){
	    		secondCmid = rs.getString(1);
	    		secondX = rs.getDouble(2);
	    		secondY= rs.getDouble(3);
	    	    //Cmid newCmid = new Cmid (secondCmid, secondX, secondY);
	    	    distance = Math.sqrt((x-secondX)*(x-secondX) + (y-secondY)*(y-secondY));
	    	    if(distance <= radius) {
	    	    	//listOfObjects.add(newCmid);
	    	    	cmidAtRadius.add(secondCmid);
	    	    	countCMIDAtRadius++;
	    	    }
			}
			
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		// disconnect
		finally
		{
			disconnect();
		}
		return cmidAtRadius;
	}
	
	public String getCMIDByPoint(double x, double y) {
		String cmidNum="";
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs=statement.executeQuery("SELECT * FROM updatedLocation WHERE x='"+x+"' AND y='"+y+"';");
			cmidNum = rs.getString("cmid");
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		finally {
			disconnect();
		}
		return cmidNum;
	}
	//update location table
	public void updateLocation(String cmid, double x, double y) {
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs=statement.executeQuery("SELECT * FROM updatedLocation WHERE cmid='"+cmid+"';");
			if(!rs.next()){
				statement.executeUpdate("INSERT INTO updatedLocation VALUES ('"+cmid+"',"+x+","+y+",CURDATE(),CURTIME(),CURDATE(),CURTIME());");
			}
			else{
				//rs.previous();
				double x_val = rs.getDouble("x");
				double y_val = rs.getDouble("y");
				String cmid_val = rs.getString("cmid");
				Date date_val = rs.getDate("createdDate");
				Time time_val = rs.getTime("createdTime");
				Date lastUpdatedDate_val = rs.getDate("lastUpdatedDate");
				Time lastUpdatedTime_val = rs.getTime("lastUpdatedTime");
				// if the location changed
				if((x!=x_val)||(y!=y_val)){
					statement.executeUpdate("INSERT INTO locationHistory VALUES ('"+cmid_val+"',"+x_val+","+y_val+",'"+date_val+"','"+time_val+"','"+lastUpdatedDate_val+"','"+lastUpdatedTime_val+"');");
					statement.executeUpdate("UPDATE updatedLocation SET x="+x+", y="+y+", createdDate = CURDATE(), createdTime = CURTIME(), lastUpdatedDate = CURDATE(), lastUpdatedTime = CURTIME() WHERE cmid='"+cmid+"';");
				}
				else{// the location didn't changed
					statement.executeUpdate("UPDATE updatedLocation SET lastUpdatedDate = CURDATE(), lastUpdatedTime = CURTIME() WHERE cmid='"+cmid+"';");
				}
			}
		}
		
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		// disconnect
		finally
		{
			disconnect();
		}
	}
	//update the table of decisionTable
	public void updateDecisionTable(String eventID, String cmid, double x, double y, String state, String region_type, String medical_condition_description, float age, int radius){
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs=statement.executeQuery("SELECT * FROM updatedLocation WHERE eventID='"+eventID+"';");
			if(!rs.next()) {
				statement.executeUpdate("INSERT INTO updatedLocation VALUES ('"+eventID+"','"+cmid+"',"+x+","+y+",'"+state+"','"+region_type+"','"+medical_condition_description+"',"+age+", "+radius+");");
			}
			else {
				String eventID_val = rs.getString("eventID");
				String cmid_val = rs.getString("cmid");
				double x_val = rs.getDouble("x");
				double y_val = rs.getDouble("y");
				String state_val = rs.getString("state");
				String region_type_val = rs.getString("region_type");
				String medical_condition_description_val = rs.getString("medical_condition_description");
				float age_val = rs.getFloat("age");
				int radius_val = rs.getInt("radius");
				statement.executeUpdate("UPDATE decisionTable SET eventID="+eventID_val+", cmid="+cmid_val+", x="+x_val+", y="+y_val+", state= "+state_val+", region_type="+region_type_val+", medical_condition_description="+medical_condition_description_val+", age="+age_val+", radius="+radius_val+", WHERE cmid='"+cmid+"';");

			}
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		// disconnect
		finally {
			disconnect();
		}
	}
	//update the table of emergency process
	public void updateEmergencyProcess(String eventID,String cmid,int radius,int type)
	{
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs=statement.executeQuery("SELECT * FROM emergencyProcess WHERE cmid='"+cmid+"';");
			if(!rs.next()){
				statement.executeUpdate("INSERT INTO emergencyProcess VALUES ('"+eventID+"','"+cmid+"',"+radius+",'"+type+"');");
			}
			else {
				String ID=rs.getString("eventID");
				String cmID=rs.getString("cmid");
				int radiusEvent=rs.getInt("radius");
				int typeOfPerson=rs.getInt("type");
				statement.executeUpdate("UPDATE emergencyProcess SET eventID="+ID+", cmid="+cmID+", radius="+radiusEvent+",type= "+typeOfPerson+" WHERE cmid='"+cmid+"';");
			}
		}
		catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		// disconnect
		finally {
			disconnect();
		}
	}
	

	
	//check if the cmid at routine proccess return 0 or emergency proccess return 1
	public int checkRoutineOrEmerg(String cmid) {
		int routineOrEmerg = 0;
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs=statement.executeQuery("SELECT * FROM updatedLocation WHERE cmid='"+cmid+"';");
			routineOrEmerg = rs.getInt("routineOrEmerg");
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		finally {
			disconnect();
		}
		//emergency
		if(routineOrEmerg == 1)
			return 1;
		else
			//routin
			return 0;
	}
	//status of cmid change to emergeny
	public void updateEmergency(String cmid) {
		try {
			connect();
			statement.execute("USE GIS_DB;");
			statement.executeUpdate("UPDATE updatedLocation SET routineOrEmerg="+1+" WHERE cmid='"+cmid+"';");
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		finally {
			disconnect();
		}
	}
	//status of cmid change to routine
	public void updateRoutine(String cmid) {
		try {
			connect();
			statement.execute("USE GIS_DB;");
			statement.executeUpdate("UPDATE updatedLocation SET routineOrEmerg="+0+" WHERE cmid='"+cmid+"';");
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		finally {
			disconnect();
		}
	}
	
	public String getEventID(String cmid) {
		String eventID="";
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs=statement.executeQuery("SELECT * FROM emergencyProcess WHERE cmid='"+cmid+"';");
			eventID = rs.getString("eventID");
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		finally {
			disconnect();
		}
		return eventID;
	}
	public ArrayList<String> getListOfKeys(JSONObject json)
	{
		ArrayList<String> keyList=new ArrayList<String>();
		Set keys = json.keySet();
		Iterator a = keys.iterator();
		while(a.hasNext()) {
			String key = (String)a.next();
			keyList.add(key);
		}
		return keyList;
	}
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String dbUrl = "jdbc:mysql://localhost";
			connection = DriverManager.getConnection(dbUrl,"root", "");
			MysqlDataSource ds = new MysqlConnectionPoolDataSource();
			ds.setServerName("localhost");
			ds.setDatabaseName("GIS_DB");
			statement=connection.createStatement();
			String dbName = new String("GIS_DB");
			statement.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
	}
	
	private void disconnect() {
	      //finally block used to close resources
	      try {
	         if(statement!=null)
	        	 statement.close();
	      }
	      catch(SQLException se2) {
	      }// nothing we can do
	      try{
	         if(connection!=null)
	        	 connection.close();
	      }
	      catch(SQLException se) {
	         se.printStackTrace();
	      }//end finally try
	}
	
//	public String getCmidByEventId(String eventID) {
//		String cmidNum = "";
//		try {
//			connect();
//			statement.execute("USE GIS_DB;");
//			ResultSet rs=statement.executeQuery("SELECT * FROM decisionTable WHERE eventID='"+eventID+"';");
//			cmidNum = rs.getString("cmid");
//		}
//		catch(SQLException se){
//		      //Handle errors for JDBC
//		      se.printStackTrace();
//		 }
//		 catch(Exception e){
//		      //Handle errors for Class.forName
//		      e.printStackTrace();
//		 }
//		finally {
//			disconnect();
//		}
//		return cmidNum;
//	}
	public double[] getPointByCmid(String cmid) {
		double point;
		try {
			connect();
			statement.execute("USE GIS_DB;");
			ResultSet rs=statement.executeQuery("SELECT * FROM updateLocation WHERE cmid='"+cmid+"';");
			point = rs.getDouble("x");
//			point[1] = rs.getDouble("y");
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		 }
		 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		 }
		finally {
			disconnect();
		}
		return point;
	}
}
