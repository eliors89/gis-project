package test;

import emergencyProcess.RequestGoogle;

//import java.io.IOException;
//import java.net.MalformedURLException;
//
//import org.json.JSONException;
//import org.json.simple.parser.ParseException;

import SQL_DataBase.SQL_db;
public class Main {
	public static void main(String[] args) throws Exception {
				SQL_db sqlDataBase = new SQL_db();
				sqlDataBase.updateDecisionTable("1111", "111", 78.3232, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1112", "112", 78.3235, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1113", "113", 78.3237, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1114", "114", 78.3232, 65.3236, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1115", "115", 78.3235, 65.3237, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1116", "116", 78.3237, 65.3250, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1117", "117", 78.3232, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1118", "118", 78.3235, 65.3534, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1119", "119", 78.3237, 65.3244, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1120", "120", 78.3732, 65.3634, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1121", "121", 78.3235, 65.3235, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1122", "122", 78.3237, 65.3238, "israel", 1, "aaa", 15, 3);
				double[] point = sqlDataBase.getPointByCmid("122");
				sqlDataBase.updateLocation("1234", 78.3237, 65.3264);
				sqlDataBase.updateRoutine("111");
				sqlDataBase.updateRoutine("118");
//				sqlDataBase.updateRoutine("1234");
				//sqlDataBase.updateLocation("1234", 78.3238, 65.3234);
				//sqlDataBase.getCMIDByPoint(8.8, 65.3234);
				System.out.println(sqlDataBase.getEventID("1234"));
				//sqlDataBase.updateDecisionTable("2222", "112", 5.5, 6.6, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1111", "111", 78.3232, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.setRadiusDecisionTable(6, "1111");
				sqlDataBase.updateDecisionTable("1112", "112", 78.3235, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1113", "113", 78.3237, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1114", "114", 78.3232, 65.3236, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1115", "115", 78.3235, 65.3237, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1116", "116", 78.3237, 65.3250, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1117", "117", 78.3232, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1118", "118", 78.3235, 65.3534, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1119", "119", 78.3237, 65.3244, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1120", "120", 78.3732, 65.3634, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1121", "121", 78.3235, 65.3235, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1122", "122", 78.3237, 65.3238, "israel", 1, "aaa", 15, 3);
				sqlDataBase.routineAllMembersByEventID("1113");
				sqlDataBase.routineAllMembersByEventID("1114");
				sqlDataBase.routineAllMembersByEventID("1115");
				sqlDataBase.routineAllMembersByEventID("1116");
				sqlDataBase.deleteEvent("1113");
				sqlDataBase.deleteEvent("1114");
				sqlDataBase.deleteEvent("1115");
				sqlDataBase.deleteEvent("1116");
				sqlDataBase.updateEmergency("112","1112");
				sqlDataBase.updateEmergency("114","2222");
				sqlDataBase.updateDecisionTable("1111", "111", 78.3231, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1112", "112", 78.3232, 65.3234, "israel", 1, "aaa", 15, 3);
//				sqlDataBase.updateDecisionTable("1113", "113", 78.3233, 65.3234, "israel", 1, "aaa", 15, 3);
//				sqlDataBase.updateDecisionTable("1114", "114", 78.3234, 65.3236, "israel", 1, "aaa", 15, 3);
//				sqlDataBase.updateDecisionTable("1115", "115", 78.3236, 65.3237, "israel", 1, "aaa", 15, 3);
//				sqlDataBase.updateDecisionTable("1116", "116", 78.3235, 65.3250, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1117", "117", 78.3237, 65.3234, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1118", "118", 78.3238, 65.3534, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1119", "119", 78.3239, 65.3244, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1120", "120", 78.3734, 65.3634, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1121", "121", 78.3234, 65.3235, "israel", 1, "aaa", 15, 3);
				sqlDataBase.updateDecisionTable("1122", "122", 78.3236, 65.3238, "israel", 1, "aaa", 15, 3);
				
//				sqlDataBase.updateDecisionTable("2222", "112", 5.5, 6.6, "israel", 1, "222", 15, 3);
//				sqlDataBase.updateDecisionTable("2222", "112", 5.5, 6.6, "israel", 1, "222", 15, 3);
//				sqlDataBase.updateDecisionTable("2223", "113", 5.5, 6.6, "israel", 1, "222", 15, 3);
				sqlDataBase.routineAllMembersByEventID("2222");
//				sqlDataBase.updateEmergency("1234", "1234");
//				System.out.println(sqlDataBase.getCmidByEventId("1234"));
//				sqlDataBase.getPointByCmid("1234");
//				sqlDataBase.getPointByCmid("1234");
				RequestGoogle req=new RequestGoogle();
				String time = "3 hours 28 mins";
				req.getTimeInInt(time);
				time = "3 hours 28 min";
				req.getTimeInInt(time);
				time = "28 min";
				req.getTimeInInt(time);
				time = "1 min";
				req.getTimeInInt(time);
				time = "4 hour 28 min";
				req.getTimeInInt(time);
				time = "1 hour";
				req.getTimeInInt(time);
				//req.getTimeInInt(time);
			    String add=req.getAddress(32.036233,34.881993);
//				System.out.println(req.getAddress(34.729937,31.879838));
//				String[] split=add.split(",");
//				String state=split[2];
				
			    System.out.println(req.sendGet("walking",31.880611,34.731161,31.812951,34.663870));
				
				sqlDataBase.deleteEvent("2222");
				
				sqlDataBase.getCmidByEventId("2222").equals("null");
				System.out.println(sqlDataBase.checkRoutineOrEmerg("1234"));
				System.out.println(req.getNearbyEMS(31.879650,34.749927 , 10000));
	}
}
		//		RequestGoogle req=new RequestGoogle();
		
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



