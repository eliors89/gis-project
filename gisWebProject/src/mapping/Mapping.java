package mapping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connectinWithServer.connection;

/**
 * Servlet implementation class Mapping
 */
@WebServlet("/Mapping")
public class Mapping extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Mapping() {
		super();
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
		// TODO Auto-generated method stub
		Writer writer=null; {
//
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("gistest2.txt"), "utf-8"));
		    writer.write("enter");
		} catch (IOException ex) {}
		  // report
		
		
		
		StringBuffer jb = new StringBuffer();
		String stringToParse = null;
		try {
			BufferedReader reader = request.getReader();
			while ((stringToParse = reader.readLine()) != null){
				jb.append(stringToParse);
			}
		} catch (Exception e) { /*report an error*/ }
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = (JSONObject) parser.parse(stringToParse);
			writer.write(stringToParse);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonArrayOb=(JSONArray) jsonObject.get("JSONFile");
		// take each value from the json array separately
		writer.write("jsonfile");
		Iterator i = jsonArrayOb.iterator();
		writer.write("finish");
		
//		while (i.hasNext()) {
//			JSONObject innerObj = (JSONObject) i.next();
//			//		//convert the data to json object
//
//			if (innerObj.get("RequestID").equals("routineLocation"))
//			{
//				RequestDispatcher rd = request.getRequestDispatcher("routineLocation");
//				rd.forward(request, response);
//			}
//			else if (innerObj.get("RequestID").equals("followUser"))
//			{
//				RequestDispatcher rd = request.getRequestDispatcher("followUser");
//				rd.forward(request, response);
//			}
//			else if (innerObj.get("RequestID").equals("Times"))
//			{
//				RequestDispatcher rd = request.getRequestDispatcher("ArriveTime");
//				rd.forward(request, response);
//			}
//			else if(innerObj.get("RequestID").equals("stopFollow"))
//			{
//				RequestDispatcher rd = request.getRequestDispatcher("StopFollow");
//				rd.forward(request, response);
//			}
//			else if (innerObj.get("RequestID").equals("AroundLocation"))
//			{
//				RequestDispatcher rd = request.getRequestDispatcher("Emergency");
//				rd.forward(request, response);
//			}
//			else if (innerObj.get("RequestID").equals("test")){
//				InputStream is=request.getInputStream();
//				OutputStream os=response.getOutputStream();
//				byte[] buf = new byte[1000];
//				for (int nChunk = is.read(buf); nChunk!=-1; nChunk = is.read(buf))
//				{
//				    os.write(buf, 0, nChunk);
//				} 
//				RequestDispatcher rd = request.getRequestDispatcher("test");
//				rd.forward(request, response);
//			}
//		}

	}
	}
}
	

