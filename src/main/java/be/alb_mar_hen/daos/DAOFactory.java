//package be.alb_mar_hen.daos;
//
//import java.sql.Connection;
//
//import be.alb_mar_hen.models.*;
//
//public class DAOFactory {
//	protected static final Connection CONNECTION  = FactoryFlowConnection.getInstance();
//	
//	public DAO<Machine> getMachineDAO() {
//		return new MachineDAO(CONNECTION);
//	}
//	
//	public DAO<Machine> getMachineDAO() {
//		return new MachineDAO(CONNECTION);
//	}
//	
//	public DAO<Maintenance> getMaintenanceDAO() {
//		return new MaintenanceDAO(CONNECTION);
//	}
//	
//	public DAO<MaintenanceResponsable> getMaintenanceResponsableDAO() {
//		return new MaintenanceResponsableDAO(CONNECTION);
//	}
//	
//	public DAO<MaintenanceWorker> getMaintenanceWorkerDAO() {
//		return new MaintenanceWorkerDAO(CONNECTION);
//	}
//	
//	public DAO<Order> getOrderDAO() {
//		return new OrderDAO(CONNECTION);
//	}
//	
//	public DAO<PurchasingAgent> getPurchasingAgentDAO() {
//		return new PurchasingAgentDAO(CONNECTION);
//	}
//	
//	public DAO<Supplier> getSupplierDAO() {
//		return new SupplierDAO(CONNECTION);
//	}
//	
//	public DAO<Site> getSiteDAO() {
//		return new SiteDAO(CONNECTION);
//	}
//	
//	public DAO<Zone> getZoneDAO() {
//		return new ZoneDAO(CONNECTION);
//	}
//	
//	public TestDAO getTestDAO() {
//		return new TestDAO(CONNECTION);
//	}
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
