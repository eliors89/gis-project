package connectinWithServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import com.sun.xml.internal.ws.api.ha.HaInfo;

public class Connection {

	public static void main(String[] args) throws ParseException {

		JSONObject map=new JSONObject();
		try {
			map.put("RequestID", "closestEMS");
			map.put("event_id", "1315");
			map.put("community_member_id","10019");
			
			
			
		
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONArray mJSONArray = new JSONArray(Arrays.asList(map));
		String data = mJSONArray.toString();
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect("http://mba4.ad.biu.ac.il/gisWebProject/Mapping")
					.data("JSONFile", data)
					.ignoreContentType(true)
					.timeout(10 * 1000 )//milliseconds
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}
		catch (IOException e) {
			e.printStackTrace();
		}


		
		
		

	}

	//send json object to server
	public void sendJsonObject(org.json.JSONObject obj,String to)
	{
		//url we need to send 
		String url = to;
		//get the string from json object
		String data = obj.toString();
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect(url)
					.data("JSONFile", data)
					.ignoreContentType(true)
					.timeout(10 * 1000 )//milliseconds
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}

		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	//send json array to server
	public void sendJsonArray(JSONArray jsonToSend, String to) {
		
		//url we need to send 
		String url = to;
		//get the string from json array
		String data = jsonToSend.toString();
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect(url)
					.data("JSONFile", data)
					.ignoreContentType(true)
					.timeout(10 * 1000 )//milliseconds
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}

		catch (IOException e) {
			e.printStackTrace();
		} 

	}
}





