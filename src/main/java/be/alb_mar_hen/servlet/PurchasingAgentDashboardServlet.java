package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.utils.ObjectCreator;

/**
 * Servlet implementation class PurchasingAgentDashboardServlet
 */
@WebServlet("/PurchasingAgentDashboardServlet")
public class PurchasingAgentDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchasingAgentDashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        // Lire les données JSON depuis la requête
	        String jsonString = request.getReader().lines().reduce("", (acc, line) -> acc + line);

	        try {
	            // Parse le JSON en tableau
	            JSONArray machinesJsonArray = new JSONArray(jsonString);

	            // Liste pour stocker les objets Machine
	            List<Machine> machines = new ArrayList<>();

	            // Utilitaire pour créer les objets
	            ObjectCreator creator = new ObjectCreator();

	            // Parcourir le tableau JSON et créer les objets Machine
	            for (int i = 0; i < machinesJsonArray.length(); i++) {
	                JSONObject machineJson = machinesJsonArray.getJSONObject(i);
	                Machine machine = creator.createMachine(machineJson);
	                machines.add(machine);
	            }

	            // Log ou traitement des objets créés
	            for (Machine machine : machines) {
	                System.out.println(machine);
	            }

	            // Réponse au client
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("Machines created successfully.");

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write("Error processing machines JSON.");
	        }
	 }

}
