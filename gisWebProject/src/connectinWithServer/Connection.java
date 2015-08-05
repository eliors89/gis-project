package connectinWithServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.*;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import SQL_DataBase.SQL_db;


// מוסיף משהו בשביל אליאור2

public class Connection {

	public static void main(String[] args) throws ParseException {
		//		SQL_db sqlDataBase = new SQL_db();


		//(String eventID, String cmid, double x, double y, String state, String region_type, String medical_condition_description, float age, int radius){
		//	sqlDataBase.updateDecisionTable("1234567", "1234", 5.5, 6.6, "israel", "0", "batta", (float) 18.5, 3);
		//public class connection {
		//		JSONArray arr=new JSONArray();
		JSONObject json=new JSONObject();

		try {
			json.put("RequestID", "stopFollow");
			json.put("eventID", "888");
			json.put("6666","NULL");
			json.put("4444","NULL");
//			json.put("x",34.783961);
//			json.put("y", 32.054121);
//			json.put("medical_condition_description", "2222");
//			json.put("age", 15.555);
//			json.put("radius", 5);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		JSONArray jsonarr= new JSONArray();
		jsonarr.put(json);
		//JSONObject js=new JSONObject();
		//js.put("JSONFile", jsonarr);

		JSONParser parser = new JSONParser();
		//JSONObject jsonn = (JSONObject) parser.parse(js.toString());
		HttpURLConnection httpcon;  
		String url = "http://mba4.ad.biu.ac.il/gisWebProject/mapping";
		String data = jsonarr.toString();
		String result = null;
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect("http://mba4.ad.biu.ac.il/gisWebProject/mapping")
					//            .data("username", targets.get(i+1))
					//            .data("password", targets.get(i+2))
					.data("JSONFile", data)
					.header("Content-Type", "Application/json")
			//		.timeout(100 * 1000 * 100) // milliseconds
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}

		//			try {
		//				JSONObject jso = (JSONObject)new JSONParser().parse(result);
		//				JSONArray jsonArrayOb=(JSONArray) jso.get("js");
		//				System.out.println(jsonArrayOb);
		//				// take each value from the json array separately
		//				Iterator i = jsonArrayOb.iterator();
		//				while (i.hasNext()) {
		//					JSONObject innerObj =  (JSONObject) parser.parse(i.next().toString());
		//
		//					
		//				}
		//
		//
		//			} catch (ParseException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public JSONObject getRequest(HttpServletRequest request)
	{
		StringBuffer jb = new StringBuffer();
		String stringToParse = null;
		//get data of requset from server
		try {
			BufferedReader reader = request.getReader();
			while ((stringToParse = reader.readLine()) != null){
				jb.append(stringToParse);
			}
		} catch (Exception e) { /*report an error*/ }
		JSONParser parser = new JSONParser();
		//convert the data to json object
		JSONObject jsonObject = new JSONObject();;
		try {
			jsonObject = (JSONObject) parser.parse(jb.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	public void sendJsonObject(org.json.JSONObject obj,String to)
	{

		//		JSONObject json=new JSONObject();
		//
		//		json.put("RequestID", "test");
		//		json.put("community_member_id","8888");
		//		json.put("x",5.5);
		//		json.put("y", 6.6);
		//
		//		JSONArray jsonarr= new JSONArray();
		//		jsonarr.add(json.toString());
		//		JSONObject js=new JSONObject();
		//		js.put("JSONFile", jsonarr);
		//
		//		JSONParser parser = new JSONParser();
		//		//JSONObject jsonn = (JSONObject) parser.parse(js.toString());
		//		HttpURLConnection httpcon;  
		//		String url = "http://mba4.ad.biu.ac.il/gisWebProject/mapping";
		//		String data = js.toString();
		//		String result = null;
		//		try{
		//			//Connect
		//			httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
		//			httpcon.setDoOutput(true);
		//			httpcon.setRequestProperty("Content-Type", "application/json");
		//			httpcon.setRequestProperty("Accept", "application/json");
		//			httpcon.setRequestMethod("POST");
		//			httpcon.connect();
		//
		//			//Write         
		//			OutputStream os = httpcon.getOutputStream();
		//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		//			writer.write(data);
		//			writer.close();
		//			os.close();


		//		String strJson=obj.toString();
		//		HttpURLConnection httpcon;  
		//		String url = to;
		//		String data = strJson;
		//		String result = null;
		//		try{
		//			//Connect
		//			httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
		//			httpcon.setDoOutput(true);
		//			httpcon.setRequestProperty("Content-Type", "application/json");
		//			httpcon.setRequestProperty("Accept", "application/json");
		//			httpcon.setRequestMethod("POST");
		//			httpcon.connect();
		////			
		//
		//			//Write         
		//			OutputStream os = httpcon.getOutputStream();
		//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		//			writer.write(data);
		//			writer.close();
		//			os.close();
		//			
		//
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		} 
		//
		//	}
		//
		//	
		//	public void sendJsonObject(org.json.simple.JSONArray jsonToSend, String to) {
		//		// TODO Auto-generated method stub
		//		HttpURLConnection httpcon; 
		//		String strJson=jsonToSend.toString();
		//		String url = to;
		//		String data = strJson;
		//		String result = null;
		//		try{
		//			//Connect
		//			httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
		//			httpcon.setDoOutput(true);
		//			httpcon.setRequestProperty("Content-Type", "application/json");
		//			httpcon.setRequestProperty("Accept", "application/json");
		//			httpcon.setRequestMethod("POST");
		//			httpcon.connect();
		//
		//			//Write         
		//			OutputStream os = httpcon.getOutputStream();
		//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		//			writer.write(data);
		//			writer.close();
		//			os.close();
		//
		//
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		} 
		//
		//
		//
		//	}
		SQL_db sqlDataBase = new SQL_db();


		//(String eventID, String cmid, double x, double y, String state, String region_type, String medical_condition_description, float age, int radius){
		//	sqlDataBase.updateDecisionTable("1234567", "1234", 5.5, 6.6, "israel", "0", "batta", (float) 18.5, 3);
		//public class connection {
		//		JSONArray arr=new JSONArray();
		
		//JSONObject js=new JSONObject();
		//js.put("JSONFile", jsonarr);

//		JSONParser parser = new JSONParser();
		//JSONObject jsonn = (JSONObject) parser.parse(js.toString());
//		HttpURLConnection httpcon;  
		String url = to;
		String data = obj.toString();
		String result = null;
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect(url)
					//            .data("username", targets.get(i+1))
					//            .data("password", targets.get(i+2))
					.data("JSONFile", data)
					.header("Content-Type", "Application/json")
					//            .timeout(100 * 1000 * 100) // milliseconds
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}

		//			try {
		//				JSONObject jso = (JSONObject)new JSONParser().parse(result);
		//				JSONArray jsonArrayOb=(JSONArray) jso.get("js");
		//				System.out.println(jsonArrayOb);
		//				// take each value from the json array separately
		//				Iterator i = jsonArrayOb.iterator();
		//				while (i.hasNext()) {
		//					JSONObject innerObj =  (JSONObject) parser.parse(i.next().toString());
		//
		//					
		//				}
		//
		//
		//			} catch (ParseException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
}





