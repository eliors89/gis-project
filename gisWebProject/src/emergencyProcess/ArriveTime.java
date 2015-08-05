package emergencyProcess;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import org.json.simple.parser.ParseException;

import connectinWithServer.Connection;
import SQL_DataBase.SQL_db;

public class ArriveTime extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArriveTime() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			org.json.JSONObject jsonObject = con.getRequest(request);

			JSONArray jsonArrayOb = null;
			try {
				jsonArrayOb = (JSONArray) jsonObject.get("JSONFile");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// take each value from the json array separately
			int arrLen = jsonArrayOb.length();
			String walking="", driving="",location_remark="",  eventID = "";
			int j;
			double[] sickPoint = new double[2]; 
			double[] cmidPoint = new double[2];
			ArrayList<String> cmidFromKey;
			String sickCmid;
			org.json.JSONArray jsonToSend=new org.json.JSONArray();
			JSONObject obj=new JSONObject();
			for (int curr =0;curr < arrLen;curr++) {
				JSONObject innerObj;
				try {
					innerObj = (JSONObject) jsonArrayOb.get(curr);
					if (innerObj.get("RequestID").equals("Times")){
						
						eventID = innerObj.get("eventID").toString();
						sickCmid = sqlDataBase.getCmidByEventId(eventID);
						sickPoint = sqlDataBase.getPointByCmid(sickCmid);
						RequestGoogle googleReq = new RequestGoogle();
						cmidFromKey = new ArrayList<String>();
						cmidFromKey=sqlDataBase.getListOfKeys(jsonObject);
						for(j=0; j < cmidFromKey.size(); j++) {
							if(!(cmidFromKey.get(j).equals("eventID"))) {
								JSONObject cmidJson = new JSONObject();
								cmidJson.put("subRequest", "cmid");
								cmidPoint = sqlDataBase.getPointByCmid(cmidFromKey.get(j));
								try{
									walking = googleReq.sendGet("walking", cmidPoint[1], cmidPoint[0],sickPoint[1], sickPoint[0]);
									driving = googleReq.sendGet("driving", cmidPoint[1], cmidPoint[0],sickPoint[1], sickPoint[0]);
								}
								catch(Exception ex){}
								location_remark = googleReq.getAddress(cmidPoint[0], cmidPoint[1]);
								cmidJson.put("eta_by_foot",walking);
								cmidJson.put("eta_by_car",driving);
								cmidJson.put("location_remark",location_remark);
								obj.put(sickCmid, cmidJson.toString());
								jsonToSend.put(obj);
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					obj.put("RequestID", "UsersArrivalTimes");
					obj.put("eventID", eventID);
					int radius = sqlDataBase.getRadiusByEventID(eventID);
					obj.put("radius",radius);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonToSend.put(obj);
	//			send.put("JSONFile", jsonToSend.toString());
			//	con.sendJsonObject(jsonToSend, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis-times");
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
}



