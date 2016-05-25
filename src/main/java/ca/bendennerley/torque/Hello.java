package ca.bendennerley.torque;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class Hello extends HttpServlet implements Servlet {
       
    public Hello() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        Enumeration<String> params = request.getParameterNames();
        out.println("<html>");
        out.println("<head><title>Servlet Example</title></head>");
        out.println("<body>");
        while(params.hasMoreElements()){
        	String param = params.nextElement();
        	out.write("<p>" + param + "</p>");
        }
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}