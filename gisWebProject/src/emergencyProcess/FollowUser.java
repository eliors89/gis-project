package emergencyProcess;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;






import SQL_DataBase.SQL_db;


//@WebServlet("/followUser")
public class FollowUser extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	public FollowUser() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			String jfString = request.getParameter("JSONFile");
			

			JSONArray jarr = new JSONArray(jfString);
			Writer writer=null;
			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("follow.txt"), "utf-8"));
			    writer.write("enter 112 ");
				writer.write(jfString);
			} catch (IOException ex) {ex.printStackTrace();}
			// take each value from the json array separately
			int arrLen = jarr.length();
			for(int curr=0;curr<arrLen;curr++) {
             	JSONObject innerObj;
             	try {
             		innerObj = (JSONObject) jarr.getJSONObject(curr);
             		writer.write(innerObj.toString());
             		writer.close();
					if (innerObj.getString("RequestID").equals("followUser")){
						//get from Json the data
						String eventID = innerObj.getString("event_id");
						String cmid  = innerObj.getString("community_member_id");	                	
						sqlDataBase.updateEmergency(cmid, eventID);
						logger.info(cmid + "move to emergency");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}