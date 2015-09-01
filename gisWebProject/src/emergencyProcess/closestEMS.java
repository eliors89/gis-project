package emergencyProcess;

import java.io.IOException;


import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import SQL_DataBase.SQL_db;
import connectinWithServer.Connection;

/**
 * Servlet implementation class closetEMS
 */
//@WebServlet("/closestEMS")
//servlet to return closest EMS
//this servlet need to improve at next ver
public class closestEMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public closestEMS() {
		super();
		// TODO Auto-generated constructor stub
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
			logger.info(" "+"enter");
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
			double[] sickPoint = new double[2]; 
			String address="",cmid="";
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			String jfString = request.getParameter("JSONFile");
			RequestGoogle req=new RequestGoogle();

			JSONArray jarr = new JSONArray(jfString);
			// take each value from the json array separately
			int arrLen = jarr.length();
			

			for (int curr=0;curr<arrLen;curr++) {
				JSONObject innerObj;
				
				try {
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					if (innerObj.getString("RequestID").equals("closestEMS")){
						String eventID=innerObj.getString("event_id");
						logger.info(" "+eventID);
						cmid  = innerObj.getString("community_member_id");
						//get the location of the sick user by cmid
						sickPoint=sqlDataBase.getPointByCmid(cmid);
						address=req.getAddress(sickPoint[0], sickPoint[1]);
						logger.info(" "+address);
						//create a new json to return 
						JSONObject jsonobj=new JSONObject();
						jsonobj.put("RequestID", "closestEMS");
						jsonobj.put("event_id", eventID);
						jsonobj.put("x", 31.892754);
						jsonobj.put("y", 34.811201);
						jsonobj.put("region_type", 1);
						jsonobj.put("state","israel");
						jsonobj.put("radius",0);
						jsonobj.put("ems_id","10000");
						jsonobj.put("location_remark",address);
						//con.sendJsonObject(jsonobj, "http://mba4.ad.biu.ac.il/gisWebProject/test");
						con.sendJsonObject(jsonobj, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis");
					}
				}catch (JSONException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{

		}
	}
}


