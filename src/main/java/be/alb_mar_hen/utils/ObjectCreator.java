package be.alb_mar_hen.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import be.alb_mar_hen.enumerations.MachineStatus;
import be.alb_mar_hen.enumerations.MaintenanceStatus;
import be.alb_mar_hen.enumerations.ZoneColor;
import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.models.MachineType;
import be.alb_mar_hen.models.Maintenance;
import be.alb_mar_hen.models.MaintenanceResponsable;
import be.alb_mar_hen.models.MaintenanceWorker;
import be.alb_mar_hen.models.Site;
import be.alb_mar_hen.models.Zone;
import be.alb_mar_hen.validators.DateValidator;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

import java.time.LocalDateTime;

public class ObjectCreator {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    public Machine createMachine(JSONObject jsonData) {
        System.out.println("jsonData: " + jsonData);
        
        Set<Maintenance> maintenances = new HashSet<>();
        Site site = createSite(jsonData.optJSONObject("site"));
        Zone zone = createZone(jsonData.optJSONObject("zone"), site);
        MachineStatus status = MachineStatus.values()[jsonData.optInt("machineStatus") - 1]; 
        MachineType machineType = createMachineType(jsonData);
        Machine machine = new Machine(
                Optional.of(jsonData.optInt("machineId")),
                jsonData.optString("machineTypeName"),
                status,
                jsonData.optString("machineName"),
                zone,
                Optional.of(jsonData.optInt("machineTypeId")),
                jsonData.optString("machineTypeName"),
                jsonData.optDouble("machineTypePrice"),
                jsonData.optInt("machineTypeDaysBeforeMaintenance"),
                maintenances,
                new HashSet<>(), // Zones
                new NumericValidator(),
                new ObjectValidator(),
                new StringValidator()
            );
        
        maintenances = createMaintenances(jsonData.optJSONObject("maintenance"), machine, jsonData);
        machine.setMaintenances(maintenances);

        return machine;
    }

    private Zone createZone(JSONObject jsonZone, Site site) {
        if (jsonZone == null) {
            return null;
        }
        ZoneColor color = ZoneColor.values()[jsonZone.optInt("zoneColor") - 1]; // Direct mapping
        return new Zone(
            Optional.of(jsonZone.optInt("zoneId")),
            color,
            jsonZone.optString("zoneName"),
            Optional.of(site.getId().get()),
            site.getCity(),
            new NumericValidator(),
            new ObjectValidator(),
            new StringValidator()
        );
    }

    private Site createSite(JSONObject jsonSite) {
        if (jsonSite == null) {
            return null;
        }
        return new Site(
            Optional.of(jsonSite.optInt("siteId")),
            jsonSite.optString("siteCity"),
            new StringValidator(),
            new NumericValidator(),
            new ObjectValidator()
        );
    }

    private MachineType createMachineType(JSONObject jsonData) {
        return new MachineType(
            Optional.of(jsonData.optInt("machineTypeId")),
            jsonData.optString("machineTypeName"),
            jsonData.optDouble("machineTypePrice"),
            jsonData.optInt("machineTypeDaysBeforeMaintenance"),
            new NumericValidator(),
            new StringValidator(),
            new ObjectValidator()
        );
    }

    private Set<Maintenance> createMaintenances(JSONObject jsonMaintenance, Machine machine, JSONObject jsonData) {
        Set<Maintenance> maintenances = new HashSet<>();
        if (jsonMaintenance == null) {
            return maintenances;
        }
        Maintenance maintenance = new Maintenance(
            Optional.of(jsonMaintenance.optInt("maintenanceId")),
            LocalDateTime.parse(jsonMaintenance.optString("maintenanceStartDate"), formatter),
            Optional.of(LocalDateTime.parse(jsonMaintenance.optString("maintenanceEndDate"), formatter)),
            Optional.of(jsonMaintenance.optInt("maintenanceDuration")),
            Optional.of(jsonMaintenance.optString("maintenanceReport")),
            
            MaintenanceStatus.values()[jsonMaintenance.optInt("maintenanceStatus") - 1],
            machine,
            createMaintenanceWorker(jsonData.optJSONObject("maintenanceWorker")),
            createMaintenanceResponsable(jsonData.optJSONObject("maintenanceResponsable")),
            new NumericValidator(),
            new StringValidator(),
            new ObjectValidator(),
            new DateValidator()
        );
        maintenances.add(maintenance);
        return maintenances;
    }

    private MaintenanceWorker createMaintenanceWorker(JSONObject jsonWorker) {
        if (jsonWorker == null) {
        	System.out.println("jsonResponsable is null");
            return null;
        }
        return new MaintenanceWorker(
            Optional.of(jsonWorker.optInt("maintenanceWorkerId")),
            jsonWorker.optString("maintenanceWorkerMatricule"),
            jsonWorker.optString("maintenanceWorkerPassword"),
            jsonWorker.optString("maintenanceWorkerFirstName"),
            
            jsonWorker.optString("maintenanceWorkerLastName"),
            new StringValidator(),
            new NumericValidator(),
            new StringFormatter(),
            new ObjectValidator()
        );
    }

    private MaintenanceResponsable createMaintenanceResponsable(JSONObject jsonResponsable) {
        if (jsonResponsable == null) {
        	System.out.println("jsonResponsable is null");
            return null;
        }
        return new MaintenanceResponsable(
            Optional.of(jsonResponsable.optInt("maintenanceResponsableId")),
            jsonResponsable.optString("maintenanceResponsableMatricule"),
            jsonResponsable.optString("maintenanceResponsablePassword"),
            jsonResponsable.optString("maintenanceResponsableFirstName"),
            jsonResponsable.optString("maintenanceResponsableLastName"),
            new ObjectValidator(),
            new StringValidator(),
            new NumericValidator(),
            new StringFormatter()
        );
    }
    
    private LocalDateTime parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        try {
            return LocalDateTime.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Erreur lors du parsing de la date : " + e.getMessage());
            return null; 
        }
    }
}
