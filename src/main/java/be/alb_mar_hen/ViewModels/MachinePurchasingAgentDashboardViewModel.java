package be.alb_mar_hen.ViewModels;

public class MachinePurchasingAgentDashboardViewModel {
	private int machineId;
    private String machineName;
    private String machineTypeName;
    private String machineStatus;
    private String zoneColor;
    private String siteCity;
    private boolean buy;

    public MachinePurchasingAgentDashboardViewModel(int machineId, String machineName, String machineTypeName, 
                            String machineStatus, String zoneColor, String siteCity, boolean buy) {
        this.machineId = machineId;
        this.machineName = machineName;
        this.machineTypeName = machineTypeName;
        this.machineStatus = machineStatus;
        this.zoneColor = zoneColor;
        this.siteCity = siteCity;
        this.buy = buy;
    }

    // Getters and Setters
    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
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
}
