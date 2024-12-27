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
		    // Récupérer la liste des machines via la méthode findAll
		    List<Machine> machines = Machine.findAll();
		    
		    // Créer une liste de ViewModels à partir des machines récupérées
		    List<MachinePurchasingAgentDashboardViewModel> machineViewModels = new ArrayList<>();
		    
		    for (Machine machine : machines) {
		        // Transformer chaque machine en un MachineViewModel
		        boolean buy = "TO_BE_REPLACED".equals(machine.getStatus());

		        // Accumuler les couleurs des zones
		        Set<String> zoneColors = new HashSet<>();
		        
		        for (Zone zone : machine.getZones()) {
		            zoneColors.add(zone.getColor().toString()); // Assurer que la méthode `getColor` existe et renvoie la couleur
		        }

		        // Combiner les couleurs des zones en une chaîne de caractères pour le ViewModel (ou adapter selon le modèle de ViewModel)
		        String combinedZoneColors = String.join(", ", zoneColors);

		        MachinePurchasingAgentDashboardViewModel machineViewModel = new MachinePurchasingAgentDashboardViewModel(
		            machine.getId(),
		            machine.getName(),
		            machine.getMachineType().getType(),
		            machine.getStatus().toString(),
		            combinedZoneColors, // Utiliser la chaîne de couleurs combinées
		            machine.getZones().iterator().next().getSite().getCity(),  // Assurer que le site contient la ville dans ton modèle
		            buy
		        );
		        machineViewModels.add(machineViewModel);
		    }
		    System.out.println("Machines: " + machineViewModels);
		    request.setAttribute("machineViewModels", machineViewModels);
		    request.getRequestDispatcher("PurchasingAgentDashboard.jsp").forward(request, response);
		}

}
