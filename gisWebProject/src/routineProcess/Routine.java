package routineProcess;
import java.io.BufferedReader;
import java.io.IOException;
//import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




//import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connectinWithServer.connection;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SQL_db sqlDataBase = new SQL_db();
			StringBuffer jb = new StringBuffer();
			String stringToParse = null;
			try {
				BufferedReader reader = request.getReader();
				while ((stringToParse = reader.readLine()) != null){
					jb.append(stringToParse);
				}
			} catch (Exception e) { /*report an error*/ }
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);
			JSONArray jsonToSend=new JSONArray();
			JSONObject obj=new JSONObject();
			JSONObject send =new JSONObject();
			RequestGoogle req=new RequestGoogle();
			JSONArray jsonArrayOb=(JSONArray) jsonObject.get("JSONFile");
			// take each value from the json array separately
			int routineOrEmerg;
			Iterator i = jsonArrayOb.iterator();
			double x, y;
			String cmid;
			String[] split;
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				if (innerObj.get("RequestID").equals("routineLocation")){
					cmid  = innerObj.get("comunity_member_id").toString();
					x = Double.parseDouble(innerObj.get("x").toString());
					y = Double.parseDouble(innerObj.get("y").toString());
					sqlDataBase.updateLocation(cmid, x, y);
					String address="";
					routineOrEmerg = sqlDataBase.checkRoutineOrEmerg(cmid);
					//enum for emergency 
					if(routineOrEmerg == 1) {
						address=req.getAddresss(x, y);
						split=address.split(",");
						obj.put("RequestID", "followUser");
						obj.put("location_remark",address);
						obj.put("comunity_member_id", cmid);
						//TODO   michal need to check how to get sick person address
						try{
							String driving=req.sendGet("driving",x,y,34.663870,31.812951);
							obj.put("eta_by_car",driving);
							String walking=req.sendGet("walking",x,y,34.663870,31.812951);
							obj.put("eta_by_foot", walking);
							obj.put("event_id", sqlDataBase.getEventID(cmid));
							jsonToSend.add(obj);
							send.put("JSONFile", jsonToSend.toString());
							connection con= new connection();
							//need to check with server url for this
							con.sendJsonObject(jsonToSend, url);
						}
						catch (Exception ex){}

					}
				}
			}
		} catch (ParseException ex) {

			ex.printStackTrace();

		} catch (NullPointerException ex) {

			ex.printStackTrace();

		}

	}
}



