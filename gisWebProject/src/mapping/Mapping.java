package mapping;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;


//@WebServlet("/Mapping")
//servlet that get all request from server and map them 
//to matching servlet
public class Mapping extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	
	public Mapping() {
		super();
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		logger.info("In First\n\n");
		System.out.println();
		Writer writer=null;
		try {
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("gistest2.txt"), "utf-8"));
				writer.write("enter1\n");
			} catch (IOException ex) {ex.printStackTrace();}
			
			//get the JSONFile with the request
			String jfString = request.getParameter("JSONFile");
			//create json array from the data we get
			JSONArray jarr = new JSONArray(jfString);

			System.out.println("JSONFile =" + jarr);
			writer.write(jarr.toString());
			//get requestID from json array
			String req=jarr.optJSONObject(0).opt("RequestID").toString();
			
			writer.write(jarr.optJSONObject(0).opt("RequestID").toString());
			writer.write(" "+jarr.toString());
			//servlet to check connection
			if (req.equals("test")){
				writer.write("if");
				request.getRequestDispatcher("test").forward(request, response);

			}
			//servlet to update the location of users
			else if(req.equals("routineLocation")){
				writer.write("routineLocation");
				request.getRequestDispatcher("routine").forward(request, response);
			}
			//servlet that map the user we get to emergency process
			else if(req.equals("followUser"))
			{
				writer.write("follow");
				request.getRequestDispatcher("followUser").forward(request, response);
			}
			//servlet that return all travel/walk times (at first time )
			else if (req.equals("Times"))
			{
				writer.write("Times");
				RequestDispatcher rd = request.getRequestDispatcher("arriveTime");
				rd.forward(request, response);
			}
			//remove user from emergency event(routine)
			else if(req.equals("stopFollow"))
			{
				writer.write("stopFollow");
				RequestDispatcher rd = request.getRequestDispatcher("stopFollow");
				rd.forward(request, response);
			}
			//servlet that open emergency event and find all users
			//around the sick person
			else if (req.equals("AroundLocation"))
			{
				writer.write("AroundLocation");
				RequestDispatcher rd = request.getRequestDispatcher("aroundLocation");
				rd.forward(request, response);
			}
			//cancel event 
			else if (req.equals("cancelEvent"))
			{
				writer.write("cancelEvent");
				RequestDispatcher rd = request.getRequestDispatcher("cancelEvent");
				rd.forward(request, response);
			}
			//return the closest EMS to sick person
			else if(req.equals("closestEMS"))
			{
				logger.info("go from mapping to closestEMS");
				writer.write("closestEMS");
				RequestDispatcher rd = request.getRequestDispatcher("closestEMS");
				rd.forward(request, response);
			}
			//return error message to server if we get request
			//that we have not
			else
			{
				JSONArray arr=new JSONArray();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("status", "dont have this RequestID");
				arr.put(jsonObject);
				response.setContentType("application/json"); 
				// Get the printwriter object from response to write the required json object to the output stream 
				PrintWriter out = response.getWriter(); 
				// Assuming your json object is **jsonObject**, perform the following, it will return your json object 
				out.print(arr);
				out.flush();
				
			}
		}catch (Exception ex){
			response.setStatus(403);
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
				ex.printStackTrace();
			}

		}
	}
}