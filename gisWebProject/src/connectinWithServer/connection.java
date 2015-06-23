package connectinWithServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;










import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import SQL_DataBase.SQL_db;


// מוסיף משהו בשביל אליאור

public class connection {

	public static void main(String[] args) throws ParseException {
		SQL_db sqlDataBase = new SQL_db();

		
		//(String eventID, String cmid, double x, double y, String state, String region_type, String medical_condition_description, float age, int radius){
	//	sqlDataBase.updateDecisionTable("1234567", "1234", 5.5, 6.6, "israel", "0", "batta", (float) 18.5, 3);
		//public class connection {
//		JSONArray arr=new JSONArray();
		JSONObject json=new JSONObject();

		json.put("RequestID", "test");
		json.put("comunity_member_id","8888");
		json.put("x",5.5);
		json.put("y", 6.6);

		JSONArray jsonarr= new JSONArray();
		jsonarr.add(json.toString());
		JSONObject js=new JSONObject();
		js.put("JSONFile", jsonarr);

		JSONParser parser = new JSONParser();
		JSONObject jsonn = (JSONObject) parser.parse(js.toString());
		HttpURLConnection httpcon;  
		String url = "http://mba4.ad.biu.ac.il/gisWebProject/test";
		String data = js.toString();
		String result = null;
		try{
			//Connect
			httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestMethod("POST");
			httpcon.connect();

			//Write         
			OutputStream os = httpcon.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(data);
			writer.close();
			os.close();

			//Read      
			BufferedReader in = new BufferedReader(
					new InputStreamReader(httpcon.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}              

			in.close();  
			result = response.toString();
			System.out.println(result);
			try {
				JSONObject jso = (JSONObject)new JSONParser().parse(result);
				JSONArray jsonArrayOb=(JSONArray) jso.get("js");
				System.out.println(jsonArrayOb);
				// take each value from the json array separately
				Iterator i = jsonArrayOb.iterator();
				while (i.hasNext()) {
					JSONObject innerObj =  (JSONObject) parser.parse(i.next().toString());

					
				}


			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
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
			jsonObject = (JSONObject) parser.parse(stringToParse);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	public void sendJsonObject(JSONObject sendJson,String to)
	{
		String strJson=sendJson.toString();
		HttpURLConnection httpcon;  
		String url = to;
		String data = strJson;
		String result = null;
		try{
			//Connect
			httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestMethod("POST");
			httpcon.connect();

			//Write         
			OutputStream os = httpcon.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(data);
			writer.close();
			os.close();


		} catch (IOException e) {
			e.printStackTrace();
		} 

	}


	public void sendJsonObject(JSONArray jsonToSend, String to) {
		// TODO Auto-generated method stub
		HttpURLConnection httpcon; 
		String strJson=jsonToSend.toString();
		String url = to;
		String data = strJson;
		String result = null;
		try{
			//Connect
			httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestMethod("POST");
			httpcon.connect();

			//Write         
			OutputStream os = httpcon.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(data);
			writer.close();
			os.close();


		} catch (IOException e) {
			e.printStackTrace();
		} 



	}
}





