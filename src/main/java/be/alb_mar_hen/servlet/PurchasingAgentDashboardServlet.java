package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import be.alb_mar_hen.ViewModels.MachinePurchasingAgentDashboardViewModel;
import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.models.PurchasingAgent;
import be.alb_mar_hen.models.Zone;
import be.alb_mar_hen.serializers.CustomLocalDateTimeSerializer;
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
		    	request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
				return;
		    }
		    List<Machine> machines = Machine.findAll();
		    
		    List<MachinePurchasingAgentDashboardViewModel> machineViewModels = new ArrayList<>();
		    
		    ObjectMapper objectMapper = new ObjectMapper();
			
			SimpleModule module = new SimpleModule();
			module.addSerializer(new CustomLocalDateTimeSerializer());
		    
			objectMapper.registerModule(module);
		    objectMapper.registerModule(new Jdk8Module());
		    
		    for (Machine machine : machines) {
		    	boolean buy = "TO_BE_REPLACED".equals(machine.getStatus().toString());
		        Set<String> zoneColors = new HashSet<>();
		        
		        for (Zone zone : machine.getZones()) {
		            zoneColors.add(zone.getColor().toString()); 
		        }

		        String combinedZoneColors = String.join(", ", zoneColors);
		        
		        
		        String machineJson = objectMapper.writeValueAsString(machine);

		        MachinePurchasingAgentDashboardViewModel machineViewModel = new MachinePurchasingAgentDashboardViewModel(
		            machine.getId(),
		            machine.getName(),
		            machine.getMachineType().getType(),
		            machine.getStatus().toString(),
		            combinedZoneColors,
		            machine.getZones().iterator().next().getSite().getCity(),
		            buy,
		            machine.getMachineType().getPrice(),
		            machine.getMaintenances().size(),
		            machineJson
		        );
		        machineViewModels.add(machineViewModel);
		    }
		    
		    request.setAttribute("machineViewModels", machineViewModels);
		    request.getRequestDispatcher("PurchasingAgentDashboard.jsp").forward(request, response);
		}
	 
	 @Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    
		    String machineJson = request.getParameter("machineJson");
		    
		    PurchasingAgent purchasingAgent = (PurchasingAgent) request.getSession().getAttribute("employee");
		    
		    if (purchasingAgent == null) {
		        request.setAttribute("errorMessage", "You must be logged in as a Purchasing Agent to perform this action.");
		        request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);  
		        return;
		    }
		    
		    boolean result = purchasingAgent.buyMachine(machineJson);
		    
		    if (result) {
		        request.setAttribute("successMessage", "Machine purchase successful.");
		    } else {
		        request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
		    }
		    
		    doGet(request, response);
	    }

}
