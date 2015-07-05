package emergencyProcess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






import org.json.JSONException;
//import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;


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
			String jfString = request.getParameter("JSONFile");
			

			JSONArray jarr = new JSONArray(jfString);
	
			// take each value from the json array separately
			int arrLen = jarr.length();
			for(int curr=0;curr<arrLen;curr++) {
             	JSONObject innerObj;
             	try {
             		innerObj = (JSONObject) jarr.getJSONObject(curr);
					if (innerObj.getString("RequestID").equals("followUser")){
						//get from Json the data
						String eventID = innerObj.getString("eventID");
						String cmid  = innerObj.getString("community_member_id");	                	
						sqlDataBase.updateEmergency(cmid, eventID);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}