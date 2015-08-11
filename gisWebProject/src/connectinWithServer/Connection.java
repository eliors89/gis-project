package connectinWithServer;

import java.io.BufferedReader;
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

		HashMap<String, String> map=new HashMap<String, String>();
		map.put("RequestID", "Times");
		map.put("event_id", "1045");

		map.put("6666","NULL");
		map.put("7777", "NULL");
		JSONArray mJSONArray = new JSONArray(Arrays.asList(map));
		String data = mJSONArray.toString();
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect("http://mba4.ad.biu.ac.il/gisWebProject/mapping")
					.data("JSONFile", data)
					.ignoreContentType(true)
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}
		catch (IOException e) {
			e.printStackTrace();
		}


		
		
		

	}

	//	public JSONObject getRequest(HttpServletRequest request)
	//	{
	//		StringBuffer jb = new StringBuffer();
	//		String stringToParse = null;
	//		//get data of requset from server
	//		try {
	//			BufferedReader reader = request.getReader();
	//			while ((stringToParse = reader.readLine()) != null){
	//				jb.append(stringToParse);
	//			}
	//		} catch (Exception e) { /*report an error*/ }
	//		JSONParser parser = new JSONParser();
	//		//convert the data to json object
	//		JSONObject jsonObject = new JSONObject();;
	//		try {
	//			jsonObject = (JSONObject) parser.parse(jb.toString());
	//		} catch (ParseException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return jsonObject;
	//	}

	public void sendJsonObject(org.json.JSONObject obj,String to)
	{
		String url = to;
		String data = obj.toString();
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect(url)
					.data("JSONFile", data)
					.ignoreContentType(true)
					.timeout(10 * 1000 * 20)//milliseconds
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}

		catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void sendJsonArray(JSONArray jsonToSend, String to) {
		// TODO Auto-generated method stub

		String url = to;
		String data = jsonToSend.toString();
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect(url)
					.data("JSONFile", data)
					.ignoreContentType(true)
					.timeout(10 * 1000 * 20)//milliseconds
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}

		catch (IOException e) {
			e.printStackTrace();
		} 

	}
}





