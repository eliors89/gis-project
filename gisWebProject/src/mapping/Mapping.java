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
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("gistest2.txt"), "utf-8"));
		    //writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "utf-8"));

		    writer.write("enter1\n");
		} catch (IOException ex) {}
		  // report
//		finally
//		{
//			try 
//			{
//				writer.close();
//			} 
//			catch (Exception ex)
//			{
//				//catch block
//			}
//		}
		
		writer.write("enter 2\n");
		StringBuffer jb = new StringBuffer();
		String stringToParse = null;
		try {
			writer.write("enter 3\n");
			BufferedReader reader = request.getReader();
			writer.write("enter 4\n");
			while ((stringToParse = reader.readLine()) != null){
				writer.write("enter 5\n");
				jb.append(stringToParse);
			}
		} catch (Exception e) { /*report an error*/ }
		writer.write("enter 6\n");
		JSONParser parser = new JSONParser();
		writer.write("enter 7\n");
		JSONObject jsonObject = new JSONObject();
		writer.write("enter 8\n");
		try {
			writer.write("enter 9 \n");
			jsonObject = (JSONObject) parser.parse(jb.toString());
			writer.write("enter 10 \n");
			writer.write(jsonObject.toString());
			writer.write("enter 11 \n");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			writer.write("enter 12 \n");
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				writer.write("enter 13 \n");
				//writer.close();
			} 
			catch (Exception ex)
			{
				//catch block
			}
		}
		JSONArray jsonArrayOb=(JSONArray) jsonObject.get("JSONFile");
		writer.write(jsonArrayOb.toString());
		// take each value from the json array separately
		Iterator i = null;
		try {
			writer.write("jsonfile");
			i = jsonArrayOb.iterator();
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
				RequestDispatcher rd = request.getRequestDispatcher("ArriveTime");
				rd.forward(request, response);
			}
			else if(reqID.equals("stopFollow"))
			{
				RequestDispatcher rd = request.getRequestDispatcher("StopFollow");
				rd.forward(request, response);
			}
			else if (reqID.equals("AroundLocation"))
			{
				RequestDispatcher rd = request.getRequestDispatcher("Emergency");
				rd.forward(request, response);
			}
			else if (reqID.equals("test")){
				writer.write("enter 19\n");
				writer.close();
				RequestDispatcher rd = request.getRequestDispatcher("test");
				rd.forward(request, response);
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
}
	

