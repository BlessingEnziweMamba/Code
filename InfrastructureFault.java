import java.time.LocalDate;

/**
 * InfraPriority - Infrastructure Inspection and Maintenance Planner
 * Group 12 - Milestone 2
 *
 * InfrastructureFault
 * --------------------
 * Extended per Milestone 1 feedback to include:
 *   - dateReported
 *   - currentStatus
 *   - estimatedRepairCost
 *   - actualRepairCost
 *
 * These fields let a fault be tracked from report through repair, and let
 * estimated vs actual cost be compared once work is complete.
 */
public class InfrastructureFault {

    // ==========================================================
    // Existing fields (adjust to match whatever the group already has)
    // ==========================================================
    private String faultId;
    private String description;
    private String location;

    // ==========================================================
    // NEW FIELDS (Milestone 1 feedback)
    // ==========================================================
    private LocalDate dateReported;
    private String currentStatus;          // e.g. "Reported", "In Progress", "Repaired", "Overdue"
    private double estimatedRepairCost;
    private double actualRepairCost;       // 0 or -1 until the repair is complete

    // ==========================================================
    // Constructor
    // ==========================================================
    public InfrastructureFault(String faultId, String description, String location,
                                LocalDate dateReported, double estimatedRepairCost) {
        this.faultId = faultId;
        this.description = description;
        this.location = location;
        this.dateReported = dateReported;
        this.currentStatus = "Reported";       // default status when a fault is first logged
        this.estimatedRepairCost = estimatedRepairCost;
        this.actualRepairCost = -1;            // -1 indicates "not yet repaired"
    }

    // ==========================================================
    // Getters and setters
    // ==========================================================
    public String getFaultId() {
        return faultId;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getDateReported() {
        return dateReported;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public double getEstimatedRepairCost() {
        return estimatedRepairCost;
    }

    public void setEstimatedRepairCost(double estimatedRepairCost) {
        this.estimatedRepairCost = estimatedRepairCost;
    }

    public double getActualRepairCost() {
        return actualRepairCost;
    }

    /**
     * Records the actual repair cost once work is complete, and updates
     * the status automatically.
     */
    public void completeRepair(double actualRepairCost) {
        this.actualRepairCost = actualRepairCost;
        this.currentStatus = "Repaired";
    }

    /**
     * Compares actual cost against the original estimate.
     * Returns 0 if the repair has not been completed yet.
     */
    public double getCostVariance() {
        if (actualRepairCost < 0) {
            return 0;
        }
        return actualRepairCost - estimatedRepairCost;
    }

    @Override
    public String toString() {
        return "Fault[" + faultId + "] " + description
                + " | Location: " + location
                + " | Reported: " + dateReported
                + " | Status: " + currentStatus
                + " | Estimated: R" + estimatedRepairCost
                + " | Actual: " + (actualRepairCost < 0 ? "Not yet repaired" : "R" + actualRepairCost);
    }

    // ==========================================================
    // Demo / test
    // ==========================================================
    public static void main(String[] args) {
        InfrastructureFault fault = new InfrastructureFault(
                "F001", "Burst water pipe on Main Road", "Main Road, Zone 3",
                LocalDate.of(2026, 8, 1), 5000.0);

        System.out.println(fault);

        fault.setCurrentStatus("In Progress");
        System.out.println(fault);

        fault.completeRepair(5750.0);
        System.out.println(fault);
        System.out.println("Cost variance: R" + fault.getCostVariance());
    }
}
