package emergencyProcess;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.json.JSONException;
//import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connectinWithServer.Connection;
import SQL_DataBase.SQL_db;

public class FollowUser extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public FollowUser() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SQL_db sqlDataBase = new SQL_db();
//			StringBuffer jb = new StringBuffer();
//			String stringToParse = null;
//			//get data of requset from server
//			try {
//				BufferedReader reader = request.getReader();
//			    while ((stringToParse = reader.readLine()) != null){
//			    	jb.append(stringToParse);
//			    }
//			  } catch (Exception e) { /*report an error*/ }
//			JSONParser parser = new JSONParser();
//			//convert the data to json object
			Connection con=new Connection();
			org.json.JSONObject jsonObject = con.getRequest(request);
			
			JSONArray jsonArrayOb = null;
			try {
				jsonArrayOb = (JSONArray) jsonObject.get("JSONFile");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// take each value from the json array separately
			int i = 0;
			int a = jsonArrayOb.length();
			while(i < a) {
             	JSONObject innerObj = null;
				try {
					innerObj = (JSONObject) jsonArrayOb.get(i);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                try {
					if (innerObj.get("RequestID").equals("followUser")){
						//get from Json the data
						String eventID = innerObj.get("eventID").toString();
						String cmid  = innerObj.get("comunity_member_id").toString();	                	
						sqlDataBase.updateEmergency(cmid, eventID);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
}