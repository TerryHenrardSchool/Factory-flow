package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class FactoryFlowConnection {
	private static Connection instance = null;
	
	private FactoryFlowConnection(){
		try{
			Class.forName(DatabaseConstant.DRIVER);
			
			System.setProperty("oracle.jdbc.Trace", "true");
			instance = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
		} catch (ClassNotFoundException ex){
			System.out.println("Driver class is nowhere to be found."+ ex.getMessage());
			System.exit(0);			
		} catch (SQLException ex) {
			System.out.println("JDBC error : " + ex.getMessage());
			System.exit(0);			
		}
		
		if (instance == null) {
			System.out.println("The database isn't accessible, program closing.");
			System.exit(0);
		}
	}
	
	public static Connection getInstance() {
		if(instance == null){
			new FactoryFlowConnection();
		}
		
		return instance;
	}
	
	public static void closeConnection() {
        try {
            if (instance != null && !instance.isClosed()) {
                instance.close();
                instance = null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error closing the connection: " + ex.getMessage());
        }
    }
}
