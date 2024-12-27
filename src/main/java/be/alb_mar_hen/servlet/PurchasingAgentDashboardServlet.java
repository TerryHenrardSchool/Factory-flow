package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import be.alb_mar_hen.ViewModels.MachinePurchasingAgentDashboardViewModel;
import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.models.PurchasingAgent;
import be.alb_mar_hen.models.Zone;
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
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		    Object employee = request.getSession().getAttribute("employee");

		    if (employee == null || !(employee instanceof PurchasingAgent)) {
		    	request.setAttribute("errorMessage", "Access denied. Please log in as a Purchasing Agent to access this page.");
		    	response.sendRedirect("login.jsp");
		        return;
		    }
		    List<Machine> machines = Machine.findAll();
		    
		    List<MachinePurchasingAgentDashboardViewModel> machineViewModels = new ArrayList<>();
		    
		    for (Machine machine : machines) {
		        boolean buy = "TO_BE_REPLACED".equals(machine.getStatus());

		        Set<String> zoneColors = new HashSet<>();
		        
		        for (Zone zone : machine.getZones()) {
		            zoneColors.add(zone.getColor().toString()); 
		        }

		        String combinedZoneColors = String.join(", ", zoneColors);

		        MachinePurchasingAgentDashboardViewModel machineViewModel = new MachinePurchasingAgentDashboardViewModel(
		            machine.getId(),
		            machine.getName(),
		            machine.getMachineType().getType(),
		            machine.getStatus().toString(),
		            combinedZoneColors,
		            machine.getZones().iterator().next().getSite().getCity(),
		            buy
		        );
		        machineViewModels.add(machineViewModel);
		    }
		    System.out.println("Machines: " + machineViewModels);
		    request.setAttribute("machineViewModels", machineViewModels);
		    request.getRequestDispatcher("PurchasingAgentDashboard.jsp").forward(request, response);
		}

}
