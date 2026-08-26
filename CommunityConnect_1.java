import java.util.ArrayList;
import java.util.Scanner;

// ---------- Complaint class ----------
class Complaint {
    private String reference;
    private String location;
    private String category;   // water, electricity, refuse, sanitation, roads, streetlights, public facilities
    private String description;
    private String urgency;    // low, medium, high
    private String date;
    private String status;     // open, in progress, resolved
    private String resolutionNotes;

    public Complaint(String reference, String location, String category,
            String description, String urgency, String date) {
        this.reference = reference;
        this.location = location;
        this.category = category;
        this.description = description;
        this.urgency = urgency;
        this.date = date;
        this.status = "Open";
        this.resolutionNotes = "";
    }

    public String getReference() { return reference; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
    public void setResolutionNotes(String notes) { this.resolutionNotes = notes; }

    @Override
    public String toString() {
        return "Ref: " + reference
        + " | Location: " + location
        + " | Category: " + category
        + " | Urgency: " + urgency
        + " | Date: " + date
        + " | Status: " + status
        + " | Notes: " + resolutionNotes
        + "\n  Description: " + description;
    }
}

// ---------- Main tracker application ----------
public class CommunityConnect {
    private static ArrayList<Complaint> complaints = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static int refCounter = 1;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addComplaint(); break;
                case "2": listAllComplaints(); break;
                case "3": searchByReference(); break;
                case "4": filterByCategory(); break;
                case "5": updateStatus(); break;
                case "6": showSummary(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid option, try again.\n");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("===== CommunityConnect Menu =====");
        System.out.println("1. Register new complaint");
        System.out.println("2. List all complaints");
        System.out.println("3. Search complaint by reference");
        System.out.println("4. Filter complaints by category");
        System.out.println("5. Update complaint status");
        System.out.println("6. Show summary report");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addComplaint() {
        System.out.print("Location: ");
        String location = sc.nextLine();
        System.out.print("Category (water/electricity/refuse/sanitation/roads/streetlights/public facilities): ");
        String category = sc.nextLine();
        System.out.print("Description: ");
        String description = sc.nextLine();
        System.out.print("Urgency (low/medium/high): ");
        String urgency = sc.nextLine();
        System.out.print("Date (e.g. 2026-07-21): ");
        String date = sc.nextLine();

        String reference = "CC-" + String.format("%04d", refCounter++);
        complaints.add(new Complaint(reference, location, category, description, urgency, date));
        System.out.println("Complaint registered with reference: " + reference + "\n");
    }

    private static void listAllComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("No complaints on record.\n");
            return;
        }
        for (Complaint c : complaints) {
            System.out.println(c);
        }
        System.out.println();
    }

    private static void searchByReference() {
        System.out.print("Enter reference number: ");
        String ref = sc.nextLine().trim();
        for (Complaint c : complaints) {
            if (c.getReference().equalsIgnoreCase(ref)) {
                System.out.println(c + "\n");
                return;
            }
        }
        System.out.println("No complaint found with that reference.\n");
    }

    private static void filterByCategory() {
        System.out.print("Enter category to filter by: ");
        String category = sc.nextLine().trim();
        boolean found = false;
        for (Complaint c : complaints) {
            if (c.getCategory().equalsIgnoreCase(category)) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) System.out.println("No complaints found in that category.");
        System.out.println();
    }

    private static void updateStatus() {
        System.out.print("Enter reference number: ");
        String ref = sc.nextLine().trim();
        for (Complaint c : complaints) {
            if (c.getReference().equalsIgnoreCase(ref)) {
                System.out.print("New status (Open/In Progress/Resolved): ");
                c.setStatus(sc.nextLine());
                System.out.print("Resolution notes (optional): ");
                c.setResolutionNotes(sc.nextLine());
                System.out.println("Status updated.\n");
                return;
            }
        }
        System.out.println("No complaint found with that reference.\n");
    }

    private static void showSummary() {
        int open = 0, inProgress = 0, resolved = 0;
        for (Complaint c : complaints) {
            switch (c.getStatus().toLowerCase()) {
                case "open": open++; break;
                case "in progress": inProgress++; break;
                case "resolved": resolved++; break;
            }
        }
        System.out.println("===== Summary Report =====");
        System.out.println("Total complaints: " + complaints.size());
        System.out.println("Open: " + open);
        System.out.println("In Progress: " + inProgress);
        System.out.println("Resolved: " + resolved);
        System.out.println();
    }
}
