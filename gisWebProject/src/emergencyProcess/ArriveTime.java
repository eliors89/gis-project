package emergencyProcess;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;











import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import connectinWithServer.Connection;
import SQL_DataBase.SQL_db;



//@WebServlet("/arriveTime")
//servlet for get travel times
public class ArriveTime extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArriveTime() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Writer writer=null;
		try {
			//output with format
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("arrive.txt"), "utf-8"));
		    writer.write("enter 112 ");
			
			
			
		} catch (IOException ex) {ex.printStackTrace();}
		try {
			JSONArray arr=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("status", "success");
			arr.put(jsonObject);
			response.setContentType("application/json"); 
			// Get the printwriter object from response to write the required json object to the output stream 
			PrintWriter out = response.getWriter(); 
			// Assuming your json object is **jsonObject**, perform the following, it will return your json object 
			out.print(arr);
			out.flush();
			//create instance of sql and connection
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			//get string of json
			String jfString = request.getParameter("JSONFile");
			//create json array from string
			JSONArray jarr = new JSONArray(jfString);
			writer.write(jarr.toString());
			RequestGoogle req=new RequestGoogle();
			//length of array
			int arrLen = jarr.length();
			String address="",walking="", driving="",location_remark="",  eventID = "";
			int j;
			
			double[] sickPoint = new double[2]; 
			double[] cmidPoint = new double[2];
			ArrayList<String> cmidFromKey;
			String sickCmid;
			org.json.JSONArray jsonToSend=new org.json.JSONArray();
			JSONObject obj=new JSONObject();
			
			
			for (int curr =0;curr < arrLen;curr++) {
				JSONObject innerObj;
				try {
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					//check if requestID is currect
					if (innerObj.get("RequestID").equals("Times")){
						//get values for event id and point of sick and helper users
						eventID = innerObj.getString("event_id");
						sickCmid = sqlDataBase.getCmidByEventId(eventID);
						sickPoint = sqlDataBase.getPointByCmid(sickCmid);
						writer.write(sickPoint.toString()+" ");
						writer.write(" "+sickCmid+" ");
						
						cmidFromKey = new ArrayList<String>();
						//get all keys 
						cmidFromKey=sqlDataBase.getListOfKeys(innerObj);
						for(j=0; j < cmidFromKey.size(); j++) {
							//ignore keys event_id and requestID to get all helper
							if(!(cmidFromKey.get(j).equals("event_id"))&&
							   !(cmidFromKey.get(j).equals("RequestID"))) {
								writer.write("if");
								writer.write(cmidFromKey.get(j));
								String cmidHelper=cmidFromKey.get(j);
								JSONObject cmidJson = new JSONObject();
								cmidJson.put("subRequest", "cmid");
								cmidPoint = sqlDataBase.getPointByCmid(cmidFromKey.get(j));
								cmidJson.put("community_member_id", cmidHelper);
								//get radius of this event
								try {
									obj.put("RequestID", "UsersArrivalTimes");
									obj.put("event_id", eventID);
									int radius = sqlDataBase.getRadiusByEventID(eventID);
									obj.put("radius",radius);
								} catch (JSONException e) {
									
									e.printStackTrace();
								}
								jsonToSend.put(obj);
								//try to get time for driving and walking from google
								try{
									writer.write("try");
									//location remark
									location_remark=req.getAddress(cmidPoint[0], cmidPoint[1]);
									writer.write(address);
									//driving time
									driving=req.sendGet("driving", cmidPoint[0], cmidPoint[1], sickPoint[0], sickPoint[1]);
									//driving time by min
									int IntDriving = req.getTimeInInt(driving);
									cmidJson.put("eta_by_car",IntDriving);
									//walking time
									walking=req.sendGet("walking",cmidPoint[0], cmidPoint[1], sickPoint[0], sickPoint[1]);
									//walking time by min
									int IntWalking = req.getTimeInInt(walking);
									cmidJson.put("eta_by_foot", IntWalking);
									cmidJson.put("location_remark",location_remark);
									//need to check with server url for this
									jsonToSend.put(cmidJson);
								}catch (Exception ex){ex.printStackTrace();}
	
							}
						}
					}
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				
				writer.write(obj.toString());
				writer.close();

				con.sendJsonArray(jsonToSend, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis-times");
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (JSONException e1) {
			
			e1.printStackTrace();
		}
	}
}



