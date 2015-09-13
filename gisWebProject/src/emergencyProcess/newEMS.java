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
 * Servlet implementation class newEMS
 */
//@WebServlet("/newEMS")
//servlet for adding new ems
public class newEMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public newEMS() {
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
			String emsID="";
			double x,y;
			//create instance of database and connection
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			//get the string of json array
			String jfString = request.getParameter("JSONFile");
			//create json array from string
			JSONArray jarr = new JSONArray(jfString);
			//length of array
			int arrLen = jarr.length();


			for (int curr=0;curr<arrLen;curr++) {
				JSONObject innerObj;

				try {
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					//check if requestID is currect
					if (innerObj.getString("RequestID").equals("newEMS")){
						//get data about ems
						emsID=innerObj.getString("ems_id");
						x=innerObj.getDouble("x");
						y=innerObj.getDouble("y");
						//update sql table
						sqlDataBase.insertNewEMS(emsID, x, y);
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

