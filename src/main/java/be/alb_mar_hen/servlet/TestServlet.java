package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.alb_mar_hen.daos.FactoryFlowConnection;
import be.alb_mar_hen.daos.TestDAO;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TestDAO testDAO = new TestDAO(FactoryFlowConnection.getInstance());       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    List<String> test = testDAO.findAll();
    	
    	String name = request.getParameter("name");
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.write("<html><head><title>Bienvenue</title></head>"
	    + "<body><h1>Bienvenue</h1>"
	    + "<p>Bonjour ");
	    if(name != null && name.length() > 0)
	    	out.write(name);
	    else out.write("cher inconnu");
	    	out.write("</p></body></html>");
	    	
	    for(String string:test) {
	    	out.write("<p>" + string + "</p>");
	    }
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
