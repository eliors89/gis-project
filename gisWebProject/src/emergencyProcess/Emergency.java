package emergencyProcess;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



















import org.json.*;
import org.json.simple.parser.ParseException;

import connectinWithServer.Connection;
import SQL_DataBase.SQL_db;

//@WebServlet("/aroundLocation")
//servlet to create new emergency event
public class Emergency extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	
	public Emergency() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Writer writer=null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("emergencyclass.txt"), "utf-8"));
		    writer.write("enter 112 ");
			
			
			
		} catch (IOException ex) {}
		try{
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
			//create instance of database and connection
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			//get the string of json array
			String jfString = request.getParameter("JSONFile");
			writer.write("aa");
			//create json array from string
			JSONArray jarr = new JSONArray(jfString);
			//length of array
			int arrLen = jarr.length();
			List<String> cmidAtRadius = new ArrayList<String>();
			writer.write(jarr.toString());
			RequestGoogle req=new RequestGoogle();
			for (int curr=0;curr<arrLen;curr++) {
				JSONObject innerObj;
				int region_type;
				double x ,y ;
				int radius  ;
				String eventID = "",cmid = "",state = "",medical_condition_description = "",address="",existentEvent="";
				double age;
				try {
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					if (innerObj.getString("RequestID").equals("AroundLocation")){
						//get from event id
						eventID = innerObj.getString("event_id");
						//check if this event already existent
						existentEvent = sqlDataBase.getCmidByEventId(eventID);
						logger.info(existentEvent);
						//if the we fount an event 
						if(!existentEvent.equals("null"))
						{
							//update data of this wvent on table
							cmid=existentEvent;
							logger.info("if");
							double[] sickpoint=new double[2];
							radius=innerObj.getInt("radius");
							sqlDataBase.setRadiusDecisionTable(radius, eventID);
							sickpoint=sqlDataBase.getPointByCmid(existentEvent);
							x=sickpoint[0];
							y=sickpoint[1];
							//get address by point
							address=req.getAddress(x, y);
							//if the adress is on sea
							if(!address.equals("wrong address"))
							{
								String[] split=address.split(",");
								state=split[2].replace(" ","");
								logger.info(state);
							}
						}
						//create new event
						else
						{
							cmid  = innerObj.getString("community_member_id");
							x = innerObj.getDouble("x");
							y = innerObj.getDouble("y");
							
							address=req.getAddress(x, y);
							region_type = sqlDataBase.getregion_type();
							medical_condition_description  = innerObj.getString("medical_condition_description");
							age = innerObj.getDouble("age");
							//check if we have radius
							if (innerObj.has("radius"))
							{
								radius = innerObj.getInt("radius");
							}
							//if we haven't a radius
							else
							{
								radius=sqlDataBase.getRadiusFromDesicionTable(eventID, cmid, x, y, state, region_type,
										                                       medical_condition_description, age); 
							}
							//address is on sea
							if(!address.equals("wrong address"))
							{
								String[] split=address.split(",");
								state=split[2].replace(" ","");
							}
							//insert to db the values of the cmid
							writer.write("before");
							sqlDataBase.updateDecisionTable(eventID, cmid, x, y, state, region_type, medical_condition_description, age, radius);
							writer.write("after");
							
						}
						
						
						
						
						
						cmidAtRadius = sqlDataBase.getCMIDByRadius(cmid,radius, x, y);
						//write all cmid at radius to file for test
						for(int i=0;i<cmidAtRadius.size();i++)
						{
							writer.write(" "+cmidAtRadius.get(i)+" ");
						}
						JSONObject obj=new JSONObject();
						//create json to send
						try {
							obj.put("RequestID", "AroundLocation");
							obj.put("event_id", eventID);
							obj.put("state",state);
							obj.put("location_remark",address);
							obj.put("x", x);
							obj.put("y", y);
							obj.put("region_type", sqlDataBase.getregion_type());
							obj.put("radius", radius);
							for (int j=0; j<cmidAtRadius.size();j++) {
								obj.put(cmidAtRadius.get(j), "NULL");
							}
						//	con.sendJsonObject(obj, "http://mba4.ad.biu.ac.il/gisWebProject/test");
							//send answer to server
							con.sendJsonObject(obj, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (JSONException e) {
					
					e.printStackTrace();
				} catch (ParseException e) {
					
					e.printStackTrace();
				}

			}
		} catch (JSONException e1) {
			
			e1.printStackTrace();
		}
		finally
		{
			writer.close();
		}
	}
}


