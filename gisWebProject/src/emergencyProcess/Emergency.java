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



import org.json.JSONException;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
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
//			StringBuffer jb = new StringBuffer();
//			String stringToParse = null;
//			try {
//				BufferedReader reader = request.getReader();
//			    while ((stringToParse = reader.readLine()) != null){
//			    	jb.append(stringToParse);
//			    }
//			  } catch (Exception e) { /*report an error*/ }
//			JSONParser parser = new JSONParser();
//			JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);
//			
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
			int arrSize = jsonArrayOb.length();
			int i = 0;
			List<String> cmidAtRadius = new ArrayList<String>();
			double x = 0,y = 0;
			int radius = 0;
	        while (i<arrSize) {
	             	JSONObject innerObj;
	             	int region_type;
	             	String eventID = null,cmid = null,state = null,medical_condition_description = null;
	             	float age = 0;
					try {
						innerObj = (JSONObject) jsonArrayOb.get(i);
						if (innerObj.get("RequestID").equals("AroundLocation")){
		                	
		                	//get from Json the data
		                	eventID = innerObj.get("eventID").toString();
		                	cmid  = innerObj.get("comunity_member_id").toString();
		                	x = Double.parseDouble(innerObj.get("x").toString());
		                    y = Double.parseDouble(innerObj.get("y").toString());
		                	state = innerObj.get("region_type").toString();	                	
		                	medical_condition_description  = innerObj.get("medical_condition_description").toString();
		                	age = Float.parseFloat(innerObj.get("age").toString());
		                	radius = Integer.parseInt(innerObj.get("radius").toString());
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
	        JSONArray obj=new JSONArray();
//	        JSONObject send=new JSONObject();
	        RequestGoogle req=new RequestGoogle();
	        String address=req.getAddress(x, y);
	        String[] split=address.split(",");
	        try {
				obj.put(new JSONObject().append("RequestID", "AroundLocation"));
				obj.put(new JSONObject().append("state", split[2]));
		        obj.put(new JSONObject().append("location_remark",address));
		        obj.put(new JSONObject().append("region_type", sqlDataBase.getregion_type()));
		        obj.put(new JSONObject().append("radius", radius));
		        for (int j=0; j<cmidAtRadius.size();j++) {
		        	obj.put(new JSONObject().append(cmidAtRadius.get(j), "NULL"));
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
		} catch (ParseException ex1) {
			ex1.printStackTrace();
		} 
	}
}



