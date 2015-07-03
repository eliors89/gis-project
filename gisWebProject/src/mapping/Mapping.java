package mapping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;








import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.*;
import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import com.sun.javafx.collections.MappingChange.Map;









import connectinWithServer.Connection;

/**
 * Servlet implementation class Mapping
 */
@WebServlet("/Mapping")
public class Mapping extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection m_connection;
	private String m_url;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Mapping() {
		super();
		m_connection = new Connection();
		m_url = "http://mba4.ad.biu.ac.il/gisWebProject";
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Writer writer=null;
		
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("gistest2.txt"), "utf-8"));
		    writer.write("enter1\n");
		} catch (IOException ex) {}
		  // report
		
		writer.write("enter 2\n");
		StringBuffer stringBuf = new StringBuffer();
		String stringToParse = null;
		JSONArray jsonArrayOb = null;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		
		//String stringToParse = null;
		try {
			writer.write("enter 3 \n");
			jsonObject = new JSONObject(request.getParameterMap());
			writer.write("enter 4 \n");
			writer.write(jsonObject.toString()+"  ");
			jsonArrayOb=(JSONArray) jsonObject.get("JSONFile");
			writer.write("enter 42 \n");
			writer.write(jsonArrayOb.toString());
			writer.write("enter 43 \n");
			writer.write(jsonArrayOb.getString(0));
			writer.write("enter 44 \n");
			//Map<String,String[]> prMap = request.getParameterMap();
			
			//String str = prMap.get("JSONFile").toString();
			//writer.write(str);
			//(reversePalindrome);
//			(JSONObject) parser.parse(i.next().toString());
			stringToParse = jsonObject.get("JSONFile").toString();
			jsonArrayOb = (JSONArray) parser.parse(stringBuf.toString());
			//jsonArrayOb = (JSONArray) parser.parse(new InputStreamReader(new FileInputStream(jsonObject.get("JSONFile").toString())))
			writer.write(jsonArrayOb.toString());
			//int parSize = reqParameter.size();
			//writer.write(parSize);
			
//			for(int i=0;i<parSize;i++){
//				writer.write("enter 5\n");
//				//writer.write(reqParameter.toString());
//				writer.write("enter 51\n");
//				//writer.write(reqParameter.get("JSONFile").toString());
//				writer.write("enter 52\n");
//				jsonObject = (JSONObject) parser.parse(jb.toString());
//				writer.write("enter 53\n");
//				jsonArrayOb=(JSONArray) jsonObject.get("JSONFile");
//			}
		} catch (Exception e) { writer.write(e.toString()); /*report an error*/ }
		finally{
			writer.write("finally 55");
			writer.close();
		}
		try {
			writer.write("enter 9 \n");
			//jsonObject = (JSONObject) parser.parse(stringBuf.toString()); // בעיית פארסינג
			writer.write("enter 10 \n");
			writer.write(jsonObject.toString());
			writer.write("enter 11 \n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			writer.write("enter 12 \n");
			e.printStackTrace();
		}
		finally
		{
			writer.write("enter 13 \n");
			writer.close();
		}
		// take each value from the json array separately
		Iterator i = null;
		try {
			writer.write("jsonfile");
			writer.write("finish");
		} catch (IOException ex) {}
		  // report
//		finally
//		{
//			try 
//			{
//				//writer.close();
//			} 
//			catch (Exception ex)
//			{
//				//catch block
//			}
//		}
		JSONObject innerObj = null;
		String reqID = null;
		while (i.hasNext()) {
			try{
				writer.write("enter 14 \n");
				innerObj = (JSONObject) parser.parse(i.next().toString());
				reqID = innerObj.get("RequestID").toString();
				writer.write("enter 15 \n");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try 
				{
					writer.write("enter 16\n");
					//writer.close();
				} 
				catch (Exception ex)
				{
					//catch block
				}
			}
			try{
				writer.write("enter 17\n");
			}
			finally
			{
				try 
				{
					writer.write("enter 18\n");
					//writer.close();
				} 
				catch (Exception ex)
				{
					//catch block
				}
			}
			//		//convert the data to json object

			if (reqID.equals("routineLocation"))
			{
				RequestDispatcher rd = request.getRequestDispatcher("routineLocation");
				rd.forward(request, response);
			}
			else if (reqID.equals("followUser"))
			{
				RequestDispatcher rd = request.getRequestDispatcher("followUser");
				rd.forward(request, response);
			}
			else if (reqID.equals("Times"))
			{
				RequestDispatcher rd = request.getRequestDispatcher("arriveTime");
				rd.forward(request, response);
			}
			else if(reqID.equals("stopFollow"))
			{
				RequestDispatcher rd = request.getRequestDispatcher("stopFollow");
				rd.forward(request, response);
			}
			else if (reqID.equals("AroundLocation"))
			{
				RequestDispatcher rd = request.getRequestDispatcher("emergency");
				rd.forward(request, response);
			}
			else if (reqID.equals("test")){
				try{
					writer.write("enter 192\n");
					RequestDispatcher rd = request.getRequestDispatcher("test");
					//String to = m_url+"/mapping/test";
					//writer.write(to.toString());
					writer.close();
					//m_connection.sendJsonObject(jsonObject, to);
					rd.forward(request, response);
					writer.write("enter 193\n");
					writer.close();
				}
				catch (Exception ex){
					writer.write("ex in forward");
				}
				finally{
					//writer.write("ex in forward2");
					//writer.close();
				}
			}
		}
		try{
			writer.write("enter 20\n");
		}
		finally
		{
			try 
			{
				writer.write("enter 21\n");
				writer.close();
			} 
			catch (Exception ex)
			{
				//catch block
			}
		}
	}
}
	

