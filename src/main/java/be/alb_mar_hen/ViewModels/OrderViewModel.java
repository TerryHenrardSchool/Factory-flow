package be.alb_mar_hen.ViewModels;

import java.time.LocalDateTime;

public class OrderViewModel {
    private String purchasingAgentName;
    private LocalDateTime orderDate;
    private Double price;
    private String supplierName;
    private String machineName;
    private String machineType;

    // Constructeur
    public OrderViewModel(String purchasingAgentName, LocalDateTime orderDate, Double price, String supplierName, String machineName, String machineType) {
        this.purchasingAgentName = purchasingAgentName;
        this.orderDate = orderDate;
        this.price = price;
        this.supplierName = supplierName;
        this.machineName = machineName;
        this.machineType = machineType;
    }

    // Getters et Setters
    public String getPurchasingAgentName() {
        return purchasingAgentName;
    }

    public void setPurchasingAgentName(String purchasingAgentName) {
        this.purchasingAgentName = purchasingAgentName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }
}
