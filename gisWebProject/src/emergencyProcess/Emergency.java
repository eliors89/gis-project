package emergencyProcess;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connectinWithServer.Connection;
import SQL_DataBase.SQL_db;

public class Emergency extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Emergency() {
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
			try{
				SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			String jfString = request.getParameter("JSONFile");
			

			JSONArray jarr = new JSONArray(jfString);
			// take each value from the json array separately
			int arrLen = jarr.length();
			List<String> cmidAtRadius = new ArrayList<String>();
			double x = 0,y = 0;
			int radius = 0;
	        for (int curr=0;curr<arrLen;curr++) {
	        		JSONObject innerObj;
	             	int region_type;
	             	String eventID = null,cmid = null,state = null,medical_condition_description = null;
	             	double age = 0;
					try {
						innerObj = (JSONObject) jarr.getJSONObject(curr);
						if (innerObj.getString("RequestID").equals("AroundLocation")){
							
		                	//get from Json the data
		                	eventID = innerObj.getString("eventID");
		                	cmid  = innerObj.getString("comunity_member_id");
		                	x = innerObj.getDouble("x");
		                    y = innerObj.getDouble("y");	                	
		                	medical_condition_description  = innerObj.getString("medical_condition_description");
		                	age = innerObj.getDouble("age");
		                	radius = innerObj.getInt("radius");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                	                	
	                	//need to implement the function
	                	region_type = sqlDataBase.getregion_type();
	                	
	                	/**/radius=3;
	                	//if we haven't a radius
	                	//TODO
	                	if(radius == 0 /*|| radius == null*/) {
	                		//need to implement the function
	                		radius = sqlDataBase.getRadiusFromDesicionTable(eventID, cmid, x, y, state, region_type, medical_condition_description, age);
	                	}
                		sqlDataBase.updateDecisionTable(eventID, cmid, x, y, state, region_type, medical_condition_description, age, radius);
            			cmidAtRadius = sqlDataBase.getCMIDByRadius(radius, x, y);
	               	}
            
//	        JSONArray jsonToSend=new JSONArray();
	        JSONObject obj=new JSONObject();
//	        JSONObject send=new JSONObject();
	        RequestGoogle req=new RequestGoogle();
	        String address=req.getAddress(x, y);
	        String[] split=address.split(",");
	        try {
				obj.put("RequestID", "AroundLocation");
				obj.put("state", split[2]);
		        obj.put("location_remark",address);
		        obj.put("region_type", sqlDataBase.getregion_type());
		        obj.put("radius", radius);
		        for (int j=0; j<cmidAtRadius.size();j++) {
		        	obj.put(cmidAtRadius.get(j), "NULL");
		        }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	//        jsonToSend.add(obj);
	  //      send.put("JSONFile", jsonToSend.toString());
	   //     connection con=new connection();
	        //TODO
	       //check
//	        con.sendJsonObject(obj, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis");
	        //obj.sendResponse();
	        //send with sendResponse
		} catch (ParseException | JSONException ex1) {
			ex1.printStackTrace();
		} 
	}
}



