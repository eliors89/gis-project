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


//import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connectinWithServer.connection;
import SQL_DataBase.SQL_db;

public class ArriveTime extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArriveTime() {
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
			StringBuffer jb = new StringBuffer();
			String stringToParse = null;
			try {
				BufferedReader reader = request.getReader();
				while ((stringToParse = reader.readLine()) != null){
					jb.append(stringToParse);
				}
			} catch (Exception e) { /*report an error*/ }
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);

			JSONArray jsonArrayOb=(JSONArray) jsonObject.get("JSONFile");
			// take each value from the json array separately
			Iterator i = jsonArrayOb.iterator();
			List<String> cmidAtRadius = new ArrayList<String>();
			String walking, driving, location_remark,  eventID;
			int x, y;
			double[] point = new double[2]; 
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				if (innerObj.get("RequestID").equals("Times")){
					//get from Json the data
					eventID = innerObj.get("eventID").toString();
					//TODO get list
					String cmid = innerObj.get("comunity_member_id").toString();
					List<String> listCmid = innerObj.getListOfCMID("comunity_member_id").toString();
					point = sqlDataBase.getPointByCmid(eventID);
					RequestGoogle googleReq = new RequestGoogle();
					walking = RequestGoogle.sendGet("walking", point[1], point[0], y, x);
					driving = RequestGoogle.sendGet("driving", point[1], point[0], y, x);
					location_remark = googleReq.getAddresss(point[0], point[1]);
					String[] split=location_remark.split(",");
				}
				JSONArray jsonToSend=new JSONArray();
				JSONObject obj=new JSONObject();
				JSONObject send=new JSONObject();
				obj.put("RequestID", "UsersArrivalTimes");
				obj.put("eventID", eventID);
				obj.put("location_remark",location_remark);
				obj.put("radius",3);
				obj.put("eta_by_foot",walking);
				obj.put("eta_by_car",driving);

				//	        for (int j=0; j<cmidAtRadius.size();j++) {
				//	        	obj.put(cmidAtRadius.get(j), "NULL");
				//	        }
				jsonToSend.add(obj);
				send.put("JSONFile", jsonToSend.toString());
				connection con=new connection();
				//TODO
				//need to ask from server what url to send
				con.sendJsonObject(jsonToSend, url);
				//send with sendResponse
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
}



