package emergencyProcess;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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
public class Emergency extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			String jfString = request.getParameter("JSONFile");
			writer.write("aa");

			JSONArray jarr = new JSONArray(jfString);
			// take each value from the json array separately
			int arrLen = jarr.length();
			List<String> cmidAtRadius = new ArrayList<String>();
			writer.write(jarr.toString());

			for (int curr=0;curr<arrLen;curr++) {
				JSONObject innerObj;
				int region_type;
				double x ,y ;
				int radius  ;
				String eventID = "",cmid = "",state = "",medical_condition_description = "";
				double age;
				try {
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					if (innerObj.getString("RequestID").equals("AroundLocation")){
						//get from Json the data
						eventID = innerObj.getString("event_id");
						cmid  = innerObj.getString("community_member_id");
						x = innerObj.getDouble("x");
						y = innerObj.getDouble("y");
						RequestGoogle req=new RequestGoogle();
						String address=req.getAddress(x, y);
						//need to implement the function
						region_type = sqlDataBase.getregion_type();
						medical_condition_description  = innerObj.getString("medical_condition_description");
						age = innerObj.getDouble("age");
						if(!address.equals("wrong address"))
						{
							String[] split=address.split(",");
							state=split[2].replace(" ","");
						}
						
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
						writer.write("before");
						sqlDataBase.updateDecisionTable(eventID, cmid, x, y, state, region_type, medical_condition_description, age, radius);
						writer.write("after");
						cmidAtRadius = sqlDataBase.getCMIDByRadius(cmid,radius, x, y);
						for(int i=0;i<cmidAtRadius.size();i++)
						{
							writer.write(" "+cmidAtRadius.get(i)+" ");
						}
						JSONObject obj=new JSONObject();
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
							response.setContentType("application/json"); 
							// Get the printwriter object from response to write the required json object to the output stream 
							PrintWriter out = response.getWriter(); 
							// Assuming your json object is **jsonObject**, perform the following, it will return your json object 
							out.print(arr);
							out.flush();
						//	con.sendJsonObject(obj, "http://mba4.ad.biu.ac.il/gisWebProject/test");
							con.sendJsonObject(obj, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			writer.close();
		}
	}
}


