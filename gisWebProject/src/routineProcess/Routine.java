package routineProcess;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connectinWithServer.Connection;
import emergencyProcess.RequestGoogle;
import SQL_DataBase.SQL_db;


public class Routine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Routine() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		try {
			JSONArray arr=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("status", "success");
			arr.put(jsonObject);
			SQL_db sqlDataBase = new SQL_db();
			//JSONArray jsonToSend=new JSONArray();
			Connection con=new Connection();
			String jfString = request.getParameter("JSONFile");
			JSONArray jarr = new JSONArray(jfString);
			//			org.json.JSONObject jsonObject = con.getRequest(request);
			org.json.JSONObject obj=new org.json.JSONObject();
			//JSONObject send =new JSONObject();
			RequestGoogle req=new RequestGoogle();


			// take each value from the json array separately
			String routineOrEmerg;
			int len = jarr.length();

			double x, y;
			String eventId, cmid, sickCmid;
			String[] split;
			double[] sickPoint = new double[2]; 
			String address="";
			for(int curr=0; curr<len;curr++) {
				JSONObject innerObj;
				try {
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					if (innerObj.getString("RequestID").equals("routineLocation")){
						cmid  = innerObj.getString("community_member_id");
						x = innerObj.getDouble("x");
						y = innerObj.getDouble("y");
						sqlDataBase.updateLocation(cmid, x, y);
						routineOrEmerg = sqlDataBase.checkRoutineOrEmerg(cmid);
						//enum for emergency 
						response.setContentType("application/json"); 
						// Get the printwriter object from response to write the required json object to the output stream 
						PrintWriter out = response.getWriter(); 
						// Assuming your json object is **jsonObject**, perform the following, it will return your json object 
						out.print(arr);
						out.flush();	
						if(routineOrEmerg != null){
							
							address=req.getAddress(x, y);
							split=address.split(",");
							obj.put("RequestID", "followUser");
							obj.put("location_remark",address);
							obj.put("community_member_id", cmid);

							eventId = sqlDataBase.getEventID(cmid);
							sickCmid = sqlDataBase.getCmidByEventId(eventId);
							sickPoint = sqlDataBase.getPointByCmid(sickCmid);
							try{
								String driving=req.sendGet("driving", x, y, sickPoint[0], sickPoint[1]);
								obj.put("eta_by_car",driving);
								String walking=req.sendGet("walking", x ,y, sickPoint[0], sickPoint[1]);
								obj.put("eta_by_foot", walking);
								obj.put("event_id", sqlDataBase.getEventID(cmid));
								//			jsonToSend.add(obj);
								//			send.put("JSONFile", jsonToSend.toString());
								//			connection con= new connection();
								//need to check with server url for this
								con.sendJsonObject(obj, "http://mba4.ad.biu.ac.il/gisWebProject/test");
								
							}catch (Exception ex){/*error report*/}
				
							
						}

					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (ParseException ex) {

			ex.printStackTrace();

		} catch (NullPointerException ex) {

			ex.printStackTrace();

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
