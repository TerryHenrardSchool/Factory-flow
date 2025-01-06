package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.alb_mar_hen.ViewModels.OrderViewModel;
import be.alb_mar_hen.daos.MachineDAO;
import be.alb_mar_hen.daos.OrderDAO;
import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.Order;
import be.alb_mar_hen.validators.ObjectValidator;

@WebServlet("/ViewOrdersServlet")
public class ViewOrdersServlet extends HttpServlet {
	private final OrderDAO orderDAO = new OrderDAO();
	private static final long serialVersionUID = 1L;
       
	public ViewOrdersServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ObjectValidator objValidator = new ObjectValidator();
		
		Employee employee = (Employee) session.getAttribute("employee");
		
		if (!objValidator.hasValue(employee)) {
			request.setAttribute("errorMessage", "Access denied. Please log in to access this page.");
			request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
			return;
		}
				
		List<Order>orders = orderDAO.findAll();
		
		if (orders.isEmpty()) {
			request.setAttribute("errorMessage", "No machines found.");
			request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
			return;
		}
		
		List<OrderViewModel> orderViewModels = new ArrayList<>();
		
		for (Order order : orders) {
            String purchasingAgentName = order.getPurchasingAgent().getFirstName() + " " + order.getPurchasingAgent().getLastName();
            LocalDateTime orderDate = order.getOrder();
            Double price = order.getMachine().getMachineType().getPrice();
            String supplierName = order.getSupplier().getName();
            String machineName = order.getMachine().getName();
            String machineType = order.getMachine().getMachineType().getType();
            
            OrderViewModel viewModel = new OrderViewModel(
                    purchasingAgentName,
                    orderDate,
                    price,
                    supplierName,
                    machineName,
                    machineType
                );
            orderViewModels.add(viewModel);
        }
		
		request.setAttribute("orders", orderViewModels);
		request.getRequestDispatcher("/WEB-INF/jsps/ViewOrders.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
