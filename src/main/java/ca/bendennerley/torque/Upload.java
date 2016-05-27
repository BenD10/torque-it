package ca.bendennerley.torque;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class Upload extends HttpServlet implements Servlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		Database.uploadData(request.getParameterMap());
        PrintWriter out = response.getWriter();
        out.println("OK!");
        out.close();
    }
}
