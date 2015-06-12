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

public class Main {
	public static void main(String[] args) throws Exception {
//		SQL_db sqlDataBase = new SQL_db();
		RequestGoogle req=new RequestGoogle();
		String timeWalk=req.sendGet("walking",34.731161,31.880611,34.663870,31.812951);
		System.out.println(timeWalk);
		String timeDrive=req.sendGet("driving",34.731161,31.880611,34.663870,31.812951);
		System.out.println(timeDrive);
		String addressByXY=req.getAddresss(34.731161,31.880611);
		System.out.println(addressByXY);
	}
}
