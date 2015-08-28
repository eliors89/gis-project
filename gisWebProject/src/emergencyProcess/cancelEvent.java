package emergencyProcess;

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

import org.json.JSONArray;
import org.json.JSONObject;

import SQL_DataBase.SQL_db;
import connectinWithServer.Connection;

/**
 * Servlet implementation class cancelEvent
 */
//@WebServlet("/cancelEvent")
public class cancelEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public cancelEvent() {
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
		Writer writer=null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("cancel.txt"), "utf-8"));
			writer.write("enter 112 ");



		} catch (IOException ex) {}
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
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			String jfString = request.getParameter("JSONFile");
			JSONArray jarr = new JSONArray(jfString);
			writer.write(jarr.toString());
			writer.close();
			int arrLen = jarr.length();
			for (int curr =0;curr < arrLen;curr++) {
				JSONObject innerObj;
				try {
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					if (innerObj.get("RequestID").equals("cancelEvent")){
						String eventID=innerObj.getString("event_id");
						sqlDataBase.routineAllMembersByEventID(eventID);
						sqlDataBase.deleteEvent(eventID);

					}
				}
				catch (Exception e){e.printStackTrace();}
			}
		}
		catch (Exception ed){ed.printStackTrace();}
		
	}
}



