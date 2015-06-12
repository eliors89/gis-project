//package responseURL;
//
//
//import org.json.simple.JSONObject;
//import org.jsoup.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//
//
//public class InitiatedHTTPCommunication {
//	//get data and target- this is the URL of the server
//    public void sendResponse () {
//    	//TODO 1 iteration
//        //communicate the JSON file to each target URL provided
//    	try {
//    		JSONObject jsonToSend=new JSONObject();
//    		jsonToSend.put("aa", 5);
//    		Jsoup.connect(
//    				"http://ercserver1.j.layershift.co.uk/erc-server/requests/test")
//            .data("username", "GIS_TEAM")
//            .data("password", "9999999")
//            .data("JSONFile", jsonToSend.toString())
//            .method(Connection.Method.POST).execute();
//    	} catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
