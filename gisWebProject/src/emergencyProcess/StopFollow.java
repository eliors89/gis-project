package emergencyProcess;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import SQL_DataBase.SQL_db;



//@WebServlet("/stopFollow")
//servlet to stop follow after user
public class StopFollow extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	public StopFollow() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		try {
			Writer writer=null;
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("stop.txt"), "utf-8"));
				writer.write("enter1\n");
			} catch (IOException ex) {}
			logger.info("enter stop");
			JSONArray arr=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("status", "success");
			arr.put(jsonObject);
			response.setContentType("application/json"); 
			// Get the printwriter object from response to write the required json object to the output stream 
			PrintWriter out = response.getWriter(); 
			// Assuming your json object is **jsonObject**, perform the following, it will return your json object 
			out.print(arr);
			out.flush();
			//create instance od sql database
			SQL_db sqlDataBase = new SQL_db();
			//get string of json
			String jfString = request.getParameter("JSONFile");
			//create json from string
			JSONArray jarr = new JSONArray(jfString);
			writer.write(jarr.toString());
			logger.info(jarr.toString());
			//length of array
			int len = jarr.length();
			int j;
			ArrayList<String> cmidFromKey;
			for(int curr=0;curr<len;curr++){
				JSONObject innerObj;
				try {
					innerObj = (JSONObject) jarr.get(curr);
					//check if request id is ok
					if (innerObj.getString("RequestID").equals("stopFollow")){
						writer.write("if");
						cmidFromKey = new ArrayList<String>();
						//get all keys of json 
						cmidFromKey = sqlDataBase.getListOfKeys(innerObj);
						writer.write(cmidFromKey.toString());

						for(j=0; j < cmidFromKey.size(); j++) {
							//ignore event id and requst id keys
							if(!cmidFromKey.get(j).equals("event_id")&&
							  (!cmidFromKey.get(j).equals("RequestID")))
							{
								String cmid=cmidFromKey.get(j);
								writer.write(cmid);
								//update the status of this cmid to routine
								sqlDataBase.updateRoutine(cmid);
								logger.info(cmid + "move to routine");
							}

						}
						writer.close();
					}
				} catch (JSONException e) {
					
					e.printStackTrace();
				}      
			}
		} catch (NullPointerException | JSONException ex) {
			ex.printStackTrace();
		}
	}
}



