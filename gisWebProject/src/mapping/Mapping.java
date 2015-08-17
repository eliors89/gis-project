package mapping;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;


@WebServlet("/Mapping")
public class Mapping extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	
	public Mapping() {
		super();
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		System.out.println("In First\n\n");
		System.out.println();
		Writer writer=null;
		try {
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("gistest2.txt"), "utf-8"));
				writer.write("enter1\n");
			} catch (IOException ex) {}
			String jfString = request.getParameter("JSONFile");
			JSONArray jarr = new JSONArray(jfString);

			System.out.println("JSONFile =" + jarr);
			writer.write(jarr.toString());
			String req=jarr.optJSONObject(0).opt("RequestID").toString();
			
			writer.write(jarr.optJSONObject(0).opt("RequestID").toString());
			writer.write(" "+jarr.toString());
			if (req.equals("test")){
				writer.write("if");
				request.getRequestDispatcher("test").forward(request, response);

			}
			else if(req.equals("routineLocation")){
				writer.write("routineLocation");
				request.getRequestDispatcher("routine").forward(request, response);
			}
			else if(req.equals("followUser"))
			{
				writer.write("follow");
				request.getRequestDispatcher("followUser").forward(request, response);
			}
			else if (req.equals("Times"))
			{
				writer.write("Times");
				RequestDispatcher rd = request.getRequestDispatcher("arriveTime");
				rd.forward(request, response);
			}
			else if(req.equals("stopFollow"))
			{
				writer.write("stopFollow");
				RequestDispatcher rd = request.getRequestDispatcher("stopFollow");
				rd.forward(request, response);
			}
			else if (req.equals("AroundLocation"))
			{
				writer.write("AroundLocation");
				RequestDispatcher rd = request.getRequestDispatcher("aroundLocation");
				rd.forward(request, response);
			}
			else if (req.equals("cancelEvent"))
			{
				writer.write("cancelEvent");
				RequestDispatcher rd = request.getRequestDispatcher("cancelEvent");
				rd.forward(request, response);
			}
			else if(req.equals("closestEMS"))
			{
				writer.write("closestEMS");
				RequestDispatcher rd = request.getRequestDispatcher("closestEMS");
				rd.forward(request, response);
			}
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
				//catch block
			}

		}
	}
}