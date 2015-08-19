package test;

import emergencyProcess.RequestGoogle;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import SQL_DataBase.SQL_db;
public class Main {
	public static void main(String[] args) throws MalformedURLException, IOException, ParseException, JSONException {
				SQL_db sqlDataBase = new SQL_db();
				sqlDataBase.updateLocation("111", 78.3232, 65.3234);
				sqlDataBase.updateLocation("112", 78.3235, 65.3234);
				sqlDataBase.updateLocation("113", 78.3237, 65.3234);
				sqlDataBase.updateLocation("114", 78.3232, 65.3236);
				sqlDataBase.updateLocation("115", 78.3235, 65.3237);
				sqlDataBase.updateLocation("116", 78.3237, 65.3250);
				sqlDataBase.updateLocation("117", 78.3232, 65.3234);
				sqlDataBase.updateLocation("118", 78.3235, 65.3534);
				sqlDataBase.updateLocation("119", 78.3237, 65.3244);
				sqlDataBase.updateLocation("120", 78.3732, 65.3634);
				sqlDataBase.updateLocation("121", 78.3235, 65.3235);
				sqlDataBase.updateLocation("122", 78.3237, 65.3238);
				
				sqlDataBase.updateLocation("1234", 7.7, 65.3234);
				sqlDataBase.updateRoutine("1234");
				sqlDataBase.updateLocation("1234", 8.8, 65.3234);
				sqlDataBase.getCMIDByPoint(8.8, 65.3234);
				System.out.println(sqlDataBase.getCmidByEventId("1234"));
				sqlDataBase.updateDecisionTable("2222", "112", 5.5, 6.6, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateEmergency("115","2223");
				sqlDataBase.updateEmergency("114","2222");
				sqlDataBase.updateDecisionTable("2222", "112", 5.5, 6.6, "israel", 1, "222", 15, 3);
				sqlDataBase.updateDecisionTable("2222", "112", 5.5, 6.6, "israel", 1, "222", 15, 3);
				sqlDataBase.updateDecisionTable("2223", "113", 5.5, 6.6, "israel", 1, "222", 15, 3);
				sqlDataBase.updateEmergency("116","2222");
				sqlDataBase.routineAllMembersByEventID("2222");
//				sqlDataBase.updateEmergency("1234", "1234");
//				System.out.println(sqlDataBase.getCmidByEventId("1234"));
//				sqlDataBase.getPointByCmid("1234");
//				sqlDataBase.getPointByCmid("1234");
				RequestGoogle req=new RequestGoogle();
			    String add=req.getAddress(34.881993,32.036233);
//				System.out.println(req.getAddress(34.729937,31.879838));
//				String[] split=add.split(",");
//				String state=split[2];
				sqlDataBase.deleteEvent("2222");
				System.out.println(sqlDataBase.checkRoutineOrEmerg("1234"));
				System.out.println(req.getNearbyEMS(34.749927, 31.879650, 10000));
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



