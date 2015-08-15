package emergencyProcess;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
		Writer writer=null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("arrive.txt"), "utf-8"));
		    writer.write("enter 112 ");
			
			
			
		} catch (IOException ex) {}
		try {
			
			SQL_db sqlDataBase = new SQL_db();
			Connection con=new Connection();
			String jfString = request.getParameter("JSONFile");
			JSONArray jarr = new JSONArray(jfString);
			writer.write(jarr.toString());
			RequestGoogle req=new RequestGoogle();
			// take each value from the json array separately
			int arrLen = jarr.length();
			String address="",walking="", driving="",location_remark="",  eventID = "";
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
					innerObj = (JSONObject) jarr.getJSONObject(curr);
					if (innerObj.get("RequestID").equals("Times")){
						
						eventID = innerObj.getString("event_id");
						sickCmid = sqlDataBase.getCmidByEventId(eventID);
						sickPoint = sqlDataBase.getPointByCmid(sickCmid);
						writer.write(sickPoint.toString()+" ");
						writer.write(" "+sickCmid+" ");
						
						cmidFromKey = new ArrayList<String>();
						cmidFromKey=sqlDataBase.getListOfKeys(innerObj);
						for(j=0; j < cmidFromKey.size(); j++) {
							if(!(cmidFromKey.get(j).equals("event_id"))&&
							   !(cmidFromKey.get(j).equals("RequestID"))) {
								writer.write("if");
								writer.write(cmidFromKey.get(j));
								String cmidHelper=cmidFromKey.get(j);
								JSONObject cmidJson = new JSONObject();
								cmidJson.put("subRequest", "cmid");
								cmidPoint = sqlDataBase.getPointByCmid(cmidFromKey.get(j));
								cmidJson.put("community_member_id", cmidHelper);
								try {
									obj.put("RequestID", "UsersArrivalTimes");
									obj.put("event_id", eventID);
									int radius = sqlDataBase.getRadiusByEventID(eventID);
									obj.put("radius",radius);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								jsonToSend.put(obj);
								
								try{
									writer.write("try");
									location_remark=req.getAddress(cmidPoint[0], cmidPoint[1]);
									writer.write(address);
									driving=req.sendGet("driving", cmidPoint[0], cmidPoint[1], sickPoint[0], sickPoint[1]);
									String drive=driving.replace(" min", "");
									cmidJson.put("eta_by_car",drive);
									walking=req.sendGet("walking",cmidPoint[0], cmidPoint[1], sickPoint[0], sickPoint[1]);
									String walk=walking.replace(" min", "");
									cmidJson.put("eta_by_foot", walk);
									cmidJson.put("location_remark",location_remark);
									//need to check with server url for this
									jsonToSend.put(cmidJson);
								}catch (Exception ex){ex.printStackTrace();}
//								try{
//									writer.write("try");
//									//writer.write(googleReq.getAddress(cmidPoint[0], cmidPoint[1]));
//									//location_remark=googleReq.getAddress(cmidPoint[0], cmidPoint[1]);
//									walking = googleReq.sendGet("walking", cmidPoint[1], cmidPoint[0],sickPoint[1], sickPoint[0]);
//									writer.write(walking);
//									driving = googleReq.sendGet("driving", cmidPoint[1], cmidPoint[0],sickPoint[1], sickPoint[0]);
//									writer.write(driving);
//								}
//								catch(Exception ex){writer.write("catch");}
								
								
								
								
								
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				writer.write(obj.toString());
				writer.close();
				//con.sendJsonArray(jsonToSend, "http://mba4.ad.biu.ac.il/gisWebProject/test");
	//			send.put("JSONFile", jsonToSend.toString());
				con.sendJsonArray(jsonToSend, "http://mba4.ad.biu.ac.il/Erc-Server/requests/emergency-gis-times");
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}



