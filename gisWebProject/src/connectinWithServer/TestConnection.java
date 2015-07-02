package connectinWithServer;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * Servlet implementation class testConnection
 */
//@WebServlet("/testConnection")
public class TestConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
    public TestConnection() {
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
		Writer writer=null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("testclass.txt"), "utf-8"));
		    writer.write("enter 112 ");
		    StringBuffer jb = new StringBuffer();
			String stringToParse = null;
			
			BufferedReader reader = request.getReader();
			while ((stringToParse = reader.readLine()) != null){
				writer.write("enter 122 ");
				jb.append(stringToParse);
			}
			writer.write("enter 132 ");
			writer.write(reader.toString());
			writer.write("enter 14 ");
		    writer.write(request.getInputStream().toString());
		    writer.write("enter 15 ");
		} catch (IOException ex) {}
		finally
		{
			try 
			{
				writer.close();
			} 
			catch (Exception ex)
			{
				//catch block
			}
		}
	InputStream is=request.getInputStream();
	OutputStream os=response.getOutputStream();
	byte[] buf = new byte[1000];
	for (int nChunk = is.read(buf); nChunk!=-1; nChunk = is.read(buf)){
	    os.write(buf, 0, nChunk);
	    } 
	}
}

