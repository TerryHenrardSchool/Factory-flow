package be.alb_mar_hen.utils;

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

    public Machine createMachine(JSONObject jsonData) {
        Zone zone = createZone(jsonData.getJSONObject("zone"));

        //Site site = createSite(jsonData.getJSONObject("site"));

        // MachineType machineType = createMachineType(jsonData);

        Set<Maintenance> maintenances = createMaintenances(jsonData.getJSONArray("maintenance"));

        MachineStatus status = MachineStatus.values()[jsonData.getInt("machineStatus") - 1];

        return new Machine(
            Optional.of(jsonData.getInt("machineId")),
            jsonData.getString("machineTypeName"),
            status,
            jsonData.getString("machineName"),
            zone,
            Optional.of(jsonData.getInt("machineTypeId")),
            jsonData.getString("machineTypeName"),
            jsonData.getDouble("machineTypePrice"),
            jsonData.getInt("machineTypeDaysBeforeMaintenance"),
            maintenances,
            new HashSet<>(), // Zones
            new NumericValidator(),
            new ObjectValidator(),
            new StringValidator()
        );
    }

    private Zone createZone(JSONObject jsonZone) {
        ZoneColor color = ZoneColor.values()[jsonZone.getInt("zoneColor") - 1]; // Direct mapping
        return new Zone(
            Optional.of(jsonZone.getInt("zoneId")),
            color,
            jsonZone.getString("zoneName"),
            Optional.of(jsonZone.getInt("siteId")),
            jsonZone.getString("siteName"),
            new NumericValidator(),
            new ObjectValidator(),
            new StringValidator()
        );
    }


    private Site createSite(JSONObject jsonSite) {
        return new Site(
            Optional.of(jsonSite.getInt("siteId")),
            jsonSite.getString("siteCity"),
            new StringValidator(),
            new NumericValidator(),
            new ObjectValidator()
        );
    }

    private MachineType createMachineType(JSONObject jsonData) {
        return new MachineType(
            Optional.of(jsonData.getInt("machineTypeId")),
            jsonData.getString("machineTypeName"),
            jsonData.getDouble("machineTypePrice"),
            jsonData.getInt("machineTypeDaysBeforeMaintenance"),
            new NumericValidator(),
            new StringValidator(),
            new ObjectValidator()
        );
    }

    private Set<Maintenance> createMaintenances(JSONArray jsonMaintenance) {
        Set<Maintenance> maintenances = new HashSet<>();
        for (int i = 0; i < jsonMaintenance.length(); i++) {
            JSONObject jsonM = jsonMaintenance.getJSONObject(i);
            Maintenance maintenance = new Maintenance(
                Optional.of(jsonM.getInt("maintenanceId")),
                LocalDateTime.parse(jsonM.getString("maintenanceStartDate")),
                Optional.of(LocalDateTime.parse(jsonM.getString("maintenanceEndDate"))),
                Optional.of(jsonM.getInt("maintenanceDuration")),
                Optional.of(jsonM.getString("maintenanceReport")),
                MaintenanceStatus.values()[jsonM.getInt("maintenanceStatus") - 1],
                null,
                createMaintenanceWorker(jsonM.getJSONObject("maintenanceWorker")),
                createMaintenanceResponsable(jsonM.getJSONObject("maintenanceResponsable")),
                new NumericValidator(),
                new StringValidator(),
                new ObjectValidator(),
                new DateValidator()
            );
            maintenances.add(maintenance);
        }
        return maintenances;
    }

    private MaintenanceWorker createMaintenanceWorker(JSONObject jsonWorker) {
        return new MaintenanceWorker(
            Optional.of(jsonWorker.getInt("maintenanceWorkerId")),
            jsonWorker.getString("maintenanceWorkerMatricule"),
            jsonWorker.getString("maintenanceWorkerPassword"),
            jsonWorker.getString("maintenanceWorkerFirstName"),
            jsonWorker.getString("maintenanceWorkerLastName"),
            new StringValidator(),
            new NumericValidator(),
            new StringFormatter(),
            new ObjectValidator()
        );
    }

    private MaintenanceResponsable createMaintenanceResponsable(JSONObject jsonResponsable) {
        return new MaintenanceResponsable(
            Optional.of(jsonResponsable.getInt("maintenanceResponsableId")),
            jsonResponsable.getString("maintenanceResponsableMatricule"),
            jsonResponsable.getString("maintenanceResponsablePassword"),
            jsonResponsable.getString("maintenanceResponsableFirstName"),
            jsonResponsable.getString("maintenanceResponsableLastName"),
            new ObjectValidator(),
            new StringValidator(),
            new NumericValidator(),
            new StringFormatter()
        );
    }
}
