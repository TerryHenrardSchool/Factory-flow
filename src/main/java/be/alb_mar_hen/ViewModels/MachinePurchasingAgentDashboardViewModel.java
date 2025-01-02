package be.alb_mar_hen.ViewModels;

import java.util.List;
import java.util.Optional;
import be.alb_mar_hen.models.Machine;

public class MachinePurchasingAgentDashboardViewModel {
	private Optional<Integer> machineId;
    private String machineName;
    private String machineTypeName;
    private String machineStatus;
    private String zoneColor;
    private String siteCity;
    private boolean buy;
    private String machineJson;
    private double price;
    private int numberOfMaintenances;

    public MachinePurchasingAgentDashboardViewModel(Optional<Integer> machineId, String machineName, String machineTypeName, 
                            String machineStatus, String zoneColor, String siteCity, boolean buy, 
                            double price, int numberOfMaintenances, String machineJson) {
        this.machineId = machineId;
        this.machineName = machineName;
        this.machineTypeName = machineTypeName;
        this.machineStatus = machineStatus;
        this.zoneColor = zoneColor;
        this.siteCity = siteCity;
        this.buy = buy;
        this.price = price;
        this.numberOfMaintenances = numberOfMaintenances;
        this.machineJson = machineJson;
    }

    // Getters and Setters
    public Optional<Integer> getMachineId() {
        return machineId;
    }

    public void setMachineId(Optional<Integer> machineId) {
        this.machineId = machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }

    public String getZoneColor() {
        return zoneColor;
    }

    public void setZoneColor(String zoneColor) {
        this.zoneColor = zoneColor;
    }

    public String getSiteCity() {
        return siteCity;
    }

    public void setSiteCity(String siteCity) {
        this.siteCity = siteCity;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }
 
	public String getMachineJson() {
		return machineJson;
	}
	
	public  void setMachineJson(String json) {
		this.machineJson = json;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setNumberOfMaintenances(int numberOfMaintenances) {
		this.numberOfMaintenances = numberOfMaintenances;
	}
	
	public int getNumberOfMaintenances() {
		return numberOfMaintenances;
	}
}
