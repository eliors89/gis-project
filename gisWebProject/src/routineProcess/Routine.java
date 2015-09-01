package routineProcess;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;















import org.json.JSONException;
//import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import connectinWithServer.Connection;
import emergencyProcess.RequestGoogle;
import SQL_DataBase.SQL_db;

//@WebServlet("/routine")
//update location + send json to server if we need
public class Routine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Routine() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		try {
			//sending response to server that we get the request from them
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
			//create new instance of sql 
			SQL_db sqlDataBase = new SQL_db();
			//create new instance of connection
			Connection con=new Connection();
			//get JSONFile string
			String jfString = request.getParameter("JSONFile");
			//create new json array from the string we get
			JSONArray jarr = new JSONArray(jfString);
			Writer writer=null;
			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("routinetest.txt"), "utf-8"));
			    writer.write("enter 112 ");
				writer.write(jfString);
			} catch (IOException ex) {}
			org.json.JSONObject obj=new org.json.JSONObject();
			//create new instance of RequestGoogle
			RequestGoogle req=new RequestGoogle();

			// take each value from the json array separately
			String routineOrEmerg;
			//length of json array we get
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
					//check the requstID from json array
					if (innerObj.getString("RequestID").equals("routineLocation")){
						//get community_member_id from json
						cmid  = innerObj.getString("community_member_id");
						//get lat from json
						x = innerObj.getDouble("x");
						//get lng from json
						y = innerObj.getDouble("y");
						//update the location of user at sql table
						sqlDataBase.updateLocation(cmid, x, y);
						//check if event is emergency or routine
						routineOrEmerg = sqlDataBase.checkRoutineOrEmerg(cmid);
						
							
						writer.write(routineOrEmerg);
						writer.close();
						//if the event is emergency we send to server 
						// the new loction of this user
						//location remark of this user
						//traveling time of this user(at minutes)
						//event id of this event
						if(!routineOrEmerg.equals("null")){
							address=req.getAddress(x, y);
							//split the answer from google map
							split=address.split(",");
							obj.put("RequestID", "followUser");
							//loction remark of this user
							obj.put("location_remark",address);
							//lat of this user
							obj.put("x", x);
							//lng of this user
							obj.put("y", y);
							//cmid of this user
							obj.put("community_member_id", cmid);
							//get eventID by cmid
							eventId = sqlDataBase.getEventID(cmid);
							//get sick person by eventID
							sickCmid = sqlDataBase.getCmidByEventId(eventId);
							//location of sick person
							sickPoint = sqlDataBase.getPointByCmid(sickCmid);
							try{
								//adding traveling time by car and by foot from google maps 
								String driving=req.sendGet("driving", x, y, sickPoint[0], sickPoint[1]);
								//compute the time by min
								int IntDriving = req.getTimeInInt(driving);
								//add to json
								obj.put("eta_by_car",IntDriving);
								String walking=req.sendGet("walking", x ,y, sickPoint[0], sickPoint[1]);
								//compute the time by min
								int IntWalking = req.getTimeInInt(walking);
								//add to json
								obj.put("eta_by_foot", IntWalking);
								obj.put("event_id", sqlDataBase.getEventID(cmid));
							
								
								//send to server (location)
								con.sendJsonObject(obj, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis");
								
							}catch (Exception ex){ex.printStackTrace();}
				
							
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
			
			e1.printStackTrace();
		}

	}
}
