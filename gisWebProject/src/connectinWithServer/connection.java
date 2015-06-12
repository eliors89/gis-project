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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




public class connection {

	public static void main(String[] args) throws ParseException {


		//public class connection {
		JSONObject json=new JSONObject();
		json.put("kk", 5);
		System.out.println(json.get("kk"));
		System.out.println(json.toString());
		JSONArray jsonarr= new JSONArray();
		jsonarr.add(json.toString());
		JSONObject js=new JSONObject();
		js.put("js", jsonarr);

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

					System.out.println(innerObj.get("kk"));
				}


			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 


	}
}





