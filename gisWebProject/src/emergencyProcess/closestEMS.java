package emergencyProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
@WebServlet("/closestEMS")
public class closestEMS extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			
			
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			String jfString = request.getParameter("JSONFile");


			JSONArray jarr = new JSONArray(jfString);
			// take each value from the json array separately
			int arrLen = jarr.length();
			

			for (int curr=0;curr<arrLen;curr++) {
				JSONObject innerObj;

				try {
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					if (innerObj.getString("RequestID").equals("closestEMS")){
						String eventID=innerObj.getString("event_id");
						JSONObject jsonobj=new JSONObject();
						jsonobj.put("RequestID", "closestEMS");
						jsonobj.put("event_id", eventID);
						jsonobj.put("x", 31.892754);
						jsonobj.put("y", 34.811201);
						jsonobj.put("region_type", 1);
						jsonobj.put("state","israel");
						jsonobj.put("radius",0);
						jsonobj.put("ems_id","10000");
						con.sendJsonObject(jsonobj, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis");
					}
				}catch (JSONException e) {
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


