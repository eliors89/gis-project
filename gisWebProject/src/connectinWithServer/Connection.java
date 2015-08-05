package connectinWithServer;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

public class Connection {

	public static void main(String[] args) throws ParseException {
		JSONObject json=new JSONObject();
		try {
			json.put("RequestID", "stopFollow");
			json.put("eventID", "888");
			json.put("6666","NULL");
			json.put("4444","NULL");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		JSONArray jsonarr= new JSONArray();
		jsonarr.put(json);
		String data = jsonarr.toString();
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect("http://mba4.ad.biu.ac.il/gisWebProject/mapping")
					.data("JSONFile", data)
					.header("Content-Type", "Application/json")
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}
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
		String url = to;
		String data = obj.toString();
		try{
			org.jsoup.Connection.Response resp = Jsoup.connect(url)
					.data("JSONFile", data)
					.header("Content-Type", "Application/json")
					.method(org.jsoup.Connection.Method.POST)
					.execute();

			System.out.println(resp.body());
		}

		catch (IOException e) {
			e.printStackTrace();
		} 
	}
}





