package emergencyProcess;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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
import SQL_DataBase.SQL_db;

public class StopFollow extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StopFollow() {
		super();
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
		try {
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			String jfString = request.getParameter("JSONFile");
			JSONArray jarr = new JSONArray(jfString);
		
			// take each value from the json array separately
			
			int len = jarr.length();
			int j;
			ArrayList<String> cmidFromKey;
	        for(int curr=0;curr<len;curr++){
             	JSONObject innerObj;
				try {
					innerObj = (JSONObject) jarr.get(curr);
					if (innerObj.getString("RequestID").equals("stopFollow")){
	                	String eventID = innerObj.getString("eventID");
	                	cmidFromKey = new ArrayList<String>();
	                	cmidFromKey = sqlDataBase.getListOfKeys(innerObj);
						for(j=0; j < cmidFromKey.size(); j++) {
							sqlDataBase.updateRoutine(cmidFromKey.get(j));
						}
	               	}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}      
            }
		} catch (NullPointerException | JSONException ex) {
			ex.printStackTrace();
		}
	}
}



