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
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
public class Main {
	public static void main(String[] args) {
				SQL_db sqlDataBase = new SQL_db();
				sqlDataBase.updateLocation("1234", 5.5, 6.6);
				sqlDataBase.updateLocation("2345", 5.7, 6.8);
				sqlDataBase.updateLocation("3456", 5.9, 6.9);
				sqlDataBase.updateEmergency("1234", "1234");
				sqlDataBase.updateLocation("1234", 7.7, 8.7);
				sqlDataBase.updateRoutine("1234");
				sqlDataBase.updateLocation("1234", 8.8, 9.9);
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



