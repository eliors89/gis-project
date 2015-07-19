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









//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.*;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
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