package test;

import emergencyProcess.RequestGoogle;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Iterator;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import routineProcess.Routine;
import SQL_DataBase.SQL_db;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;




//import org.json.simple.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
public class Main {
	public static void main(String[] args) throws MalformedURLException, IOException, ParseException, JSONException {
//				SQL_db sqlDataBase = new SQL_db();
//				sqlDataBase.updateLocation("1234", 5.5, 6.6);
//				sqlDataBase.updateLocation("2345", 5.7, 6.8);
//				sqlDataBase.updateLocation("3456", 5.9, 6.9);
//				sqlDataBase.updateEmergency("1234", "1234");
//				sqlDataBase.updateLocation("1234", 7.7, 8.7);
//				sqlDataBase.updateRoutine("1234");
//				sqlDataBase.updateLocation("1234", 8.8, 9.9);
//				sqlDataBase.getCMIDByPoint(8.8, 9.9);
//				System.out.println(sqlDataBase.getCmidByEventId("1234"));
//				sqlDataBase.updateDecisionTable("1234", "1234", 5.5, 6.6, "israel", 1, "aaa", 15, 3);
//				sqlDataBase.updateEmergency("1234", "1234");
//				System.out.println(sqlDataBase.getCmidByEventId("1234"));
//				sqlDataBase.getPointByCmid("1234");
//				sqlDataBase.getPointByCmid("1234");
				RequestGoogle req=new RequestGoogle();
			    String add=req.getAddress(34.729817,31.879638);
				System.out.println(req.getAddress(34.729817,31.879638));
				String[] split=add.split(",");
				String state=split[2];
				
	}
}
		//		RequestGoogle req=new RequestGoogle();
		//		String timeWalk=req.sendGet("walking",34.731161,31.880611,34.663870,31.812951);
		//		System.out.println(timeWalk);
		//		String timeDrive=req.sendGet("driving",34.731161,31.880611,34.663870,31.812951);
		//		System.out.println(timeDrive);
		//		String addressByXY=req.getAddresss(34.731161,31.880611);
		//		System.out.println(addressByXY);



//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("a", "aaa");
//		jsonObject.put("b", "bbb");
//
//		Set keys = jsonObject.keySet();
//		Iterator a = keys.iterator();
//		while(a.hasNext()) {
//			String key = (String)a.next();
//			// loop to get the dynamic key
//			String value = (String)jsonObject.get(key);
//			System.out.print("key : "+key);
//			System.out.println(" value :"+value);
//		}
//	}



