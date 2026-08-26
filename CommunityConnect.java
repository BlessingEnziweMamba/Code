import java.io.*;
import java.time.LocalDate;
import java.util.*;


public class CommunityConnect {

    private static Scanner scanner = new Scanner(System.in);
    private static ComplaintManager manager = new ComplaintManager();

    public static void main(String[] args) {
        try {
            manager.loadFromFile();
            System.out.println("Loaded " + manager.getAllComplaints().size() + " existing complaint(s).");
        } catch (Exception e) {
            System.out.println("Could not load saved data: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> createComplaint();
                    case "2" -> searchComplaint();
                    case "3" -> displayAll();
                    case "4" -> filterComplaints();
                    case "5" -> updateStatus();
                    case "6" -> addNotes();
                    case "7" -> System.out.println("Unresolved complaints: " + manager.countUnresolved());
                    case "8" -> System.out.println(manager.generateSummary());
                    case "9" -> saveData();
                    case "10" -> loadSampleData();
                    case "0" -> {
                        saveData();
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Invalid option, please try again.");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== CommunityConnect Menu =====");
        System.out.println("1. Report a new complaint");
        System.out.println("2. Search complaint by reference number");
        System.out.println("3. Display all complaints");
        System.out.println("4. Filter complaints");
        System.out.println("5. Update complaint status");
        System.out.println("6. Add resolution notes");
        System.out.println("7. Count unresolved complaints");
        System.out.println("8. Generate summary report");
        System.out.println("9. Save data to file");
        System.out.println("10. Load 20 sample complaints (demo data)");
        System.out.println("0. Save and exit");
        System.out.print("Enter your choice: ");
    }

    private static void createComplaint() {
        System.out.print("Is the complainant anonymous? (yes/no): ");
        boolean anonymous = scanner.nextLine().trim().equalsIgnoreCase("yes");

        String name = "";
        if (!anonymous) {
            System.out.print("Complainant name: ");
            name = scanner.nextLine().trim();
        }

        System.out.print("Contact info (optional, press Enter to skip): ");
        String contact = scanner.nextLine().trim();

        System.out.print("Community/location: ");
        String location = scanner.nextLine().trim();

        ServiceCategory category = chooseCategory();

        System.out.print("Description of the problem: ");
        String description = scanner.nextLine().trim();

        System.out.print("Urgency level (Low/Medium/High/Critical): ");
        String urgency = scanner.nextLine().trim();

        Complaint c = manager.createComplaint(name, anonymous, contact, location, category, description, urgency);
        System.out.println("Complaint recorded successfully! Reference number: " + c.getReferenceNumber());
    }

    private static ServiceCategory chooseCategory() {
        ServiceCategory[] values = ServiceCategory.values();
        System.out.println("Choose a service category:");
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        while (true) {
            System.out.print("Enter number: ");
            String input = scanner.nextLine().trim();
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < values.length) return values[index];
            } catch (NumberFormatException ignored) { }
            System.out.println("Invalid choice, try again.");
        }
    }

    private static void searchComplaint() {
        System.out.print("Enter reference number: ");
        String ref = scanner.nextLine().trim();
        Complaint c = manager.searchByReference(ref);
        System.out.println(c != null ? c : "No complaint found with that reference number.");
    }

    private static void displayAll() {
        List<Complaint> all = manager.getAllComplaints();
        if (all.isEmpty()) {
            System.out.println("No complaints recorded yet.");
            return;
        }
        for (Complaint c : all) {
            System.out.println(c);
            System.out.println("----------------------------------------");
        }
    }

    private static void filterComplaints() {
        System.out.println("Leave any field blank to skip that filter.");

        System.out.print("Category (e.g. WATER, ELECTRICITY) or blank: ");
        String catInput = scanner.nextLine().trim();
        ServiceCategory category = catInput.isEmpty() ? null : ServiceCategory.valueOf(catInput.toUpperCase());

        System.out.print("Location or blank: ");
        String locInput = scanner.nextLine().trim();
        String location = locInput.isEmpty() ? null : locInput;

        System.out.print("Urgency or blank: ");
        String urgInput = scanner.nextLine().trim();
        String urgency = urgInput.isEmpty() ? null : urgInput;

        System.out.print("Status (e.g. REPORTED, RESOLVED) or blank: ");
        String statInput = scanner.nextLine().trim();
        ComplaintStatus status = statInput.isEmpty() ? null : ComplaintStatus.valueOf(statInput.toUpperCase());

        List<Complaint> results = manager.filter(category, location, urgency, status);
        if (results.isEmpty()) {
            System.out.println("No matching complaints.");
        } else {
            for (Complaint c : results) System.out.println(c);
        }
    }

    private static void updateStatus() {
        System.out.print("Enter reference number: ");
        String ref = scanner.nextLine().trim();

        ComplaintStatus[] values = ComplaintStatus.values();
        System.out.println("Choose new status:");
        for (int i = 0; i < values.length; i++) System.out.println((i + 1) + ". " + values[i]);
        System.out.print("Enter number: ");
        int index = Integer.parseInt(scanner.nextLine().trim()) - 1;

        boolean success = manager.updateStatus(ref, values[index]);
        System.out.println(success ? "Status updated." : "Complaint not found.");
    }

    private static void addNotes() {
        System.out.print("Enter reference number: ");
        String ref = scanner.nextLine().trim();
        System.out.print("Enter resolution notes: ");
        String notes = scanner.nextLine().trim();
        boolean success = manager.addResolutionNotes(ref, notes);
        System.out.println(success ? "Notes added." : "Complaint not found.");
    }

    private static void saveData() {
        try {
            manager.saveToFile();
            System.out.println("Data saved to complaints.txt");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadSampleData() {
        manager.createComplaint("Thandeka Zulu", false, "0821234567", "Richards Bay", ServiceCategory.WATER, "Burst water pipe flooding Church Street", "High");
        manager.createComplaint("", true, "", "Empangeni", ServiceCategory.ELECTRICITY, "Transformer sparking near taxi rank", "Critical");
        manager.createComplaint("Sipho Ngcobo", false, "0731239988", "Esikhawini", ServiceCategory.REFUSE, "Refuse not collected for three weeks", "Medium");
        manager.createComplaint("Nomvula Dlamini", false, "0844567123", "Richards Bay CBD", ServiceCategory.DRAINAGE_SEWAGE, "Sewage overflowing onto pavement", "Critical");
        manager.createComplaint("", true, "", "Meerensee", ServiceCategory.ROADS_POTHOLES, "Large pothole damaging vehicles on main road", "High");
        manager.createComplaint("Bongani Khumalo", false, "0768892211", "Brackenham", ServiceCategory.STREETLIGHTS, "Streetlights off for two weeks, unsafe at night", "Medium");
        manager.createComplaint("Ayanda Mthembu", false, "0722345678", "Aquadene", ServiceCategory.PUBLIC_FACILITIES, "Community hall roof leaking badly", "Low");
        manager.createComplaint("Lindiwe Buthelezi", false, "0835551122", "Ngwelezane", ServiceCategory.WATER, "No water supply for four days", "Critical");
        manager.createComplaint("", true, "", "eSikhaleni", ServiceCategory.ELECTRICITY, "Frequent power outages every evening", "High");
        manager.createComplaint("Mandla Cele", false, "0715557890", "Richards Bay", ServiceCategory.REFUSE, "Illegal dumping site growing near school", "Medium");
        manager.createComplaint("Zanele Mkhize", false, "0823214567", "Empangeni", ServiceCategory.DRAINAGE_SEWAGE, "Blocked stormwater drain causing flooding", "High");
        manager.createComplaint("", true, "", "Meerensee", ServiceCategory.ROADS_POTHOLES, "Road subsidence forming a sinkhole", "Critical");
        manager.createComplaint("Thabo Nkosi", false, "0768871234", "Brackenham", ServiceCategory.STREETLIGHTS, "Broken streetlight pole leaning over walkway", "Medium");
        manager.createComplaint("Precious Zungu", false, "0847761234", "Esikhawini", ServiceCategory.PUBLIC_FACILITIES, "Public toilets vandalised and unusable", "Medium");
        manager.createComplaint("Sibusiso Ndlovu", false, "0731122334", "Richards Bay CBD", ServiceCategory.WATER, "Leaking pipe wasting water for a week", "Medium");
        manager.createComplaint("", true, "", "Ngwelezane", ServiceCategory.ELECTRICITY, "Exposed live wires near a playground", "Critical");
        manager.createComplaint("Nokuthula Mahlangu", false, "0822239988", "eSikhaleni", ServiceCategory.REFUSE, "Overflowing communal bins attracting pests", "Low");
        manager.createComplaint("Musa Sithole", false, "0761239900", "Aquadene", ServiceCategory.DRAINAGE_SEWAGE, "Manhole cover missing, safety hazard", "High");
        manager.createComplaint("", true, "", "Empangeni", ServiceCategory.ROADS_POTHOLES, "Multiple potholes on school route", "High");
        manager.createComplaint("Fikile Mbeki", false, "0845678321", "Richards Bay", ServiceCategory.PUBLIC_FACILITIES, "Broken swings and unsafe playground equipment", "Low");
        System.out.println("20 sample complaints loaded. Total now: " + manager.getAllComplaints().size());
    }
}

enum ServiceCategory {
    WATER,
    ELECTRICITY,
    REFUSE,
    DRAINAGE_SEWAGE,
    ROADS_POTHOLES,
    STREETLIGHTS,
    PUBLIC_FACILITIES
}

enum ComplaintStatus {
    REPORTED,
    VERIFIED,
    ASSIGNED,
    IN_PROGRESS,
    RESOLVED,
    CLOSED_OR_REJECTED
}

class Complaint {

    private String referenceNumber;
    private String complainantName;
    private boolean anonymous;
    private String contactInfo;
    private String location;
    private ServiceCategory category;
    private String description;
    private String urgencyLevel;
    private LocalDate dateReported;
    private ComplaintStatus status;
    private String resolutionNotes;

    public Complaint(String referenceNumber, String complainantName, boolean anonymous,
                    String contactInfo, String location, ServiceCategory category,
                    String description, String urgencyLevel) {
        this.referenceNumber = referenceNumber;
        this.complainantName = anonymous ? "Anonymous" : complainantName;
        this.anonymous = anonymous;
        this.contactInfo = contactInfo;
        this.location = location;
        this.category = category;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.dateReported = LocalDate.now();
        this.status = ComplaintStatus.REPORTED;
        this.resolutionNotes = "";
    }

    public String getReferenceNumber() { return referenceNumber; }
    public String getComplainantName() { return complainantName; }
    public boolean isAnonymous() { return anonymous; }
    public String getContactInfo() { return contactInfo; }
    public String getLocation() { return location; }
    public ServiceCategory getCategory() { return category; }
    public String getDescription() { return description; }
    public String getUrgencyLevel() { return urgencyLevel; }
    public LocalDate getDateReported() { return dateReported; }
    public ComplaintStatus getStatus() { return status; }
    public String getResolutionNotes() { return resolutionNotes; }

    public void setStatus(ComplaintStatus status) { this.status = status; }
    public void setResolutionNotes(String notes) { this.resolutionNotes = notes; }

    public boolean isUnresolved() {
        return status != ComplaintStatus.RESOLVED && status != ComplaintStatus.CLOSED_OR_REJECTED;
    }

    public String toFileString() {
        return referenceNumber + "|" + complainantName + "|" + anonymous + "|" + contactInfo + "|"
                + location + "|" + category + "|" + description + "|" + urgencyLevel + "|"
                + dateReported + "|" + status + "|" + resolutionNotes;
    }

    public static Complaint fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        Complaint c = new Complaint(p[0], p[1], Boolean.parseBoolean(p[2]), p[3], p[4],
                ServiceCategory.valueOf(p[5]), p[6], p[7]);
        c.dateReported = LocalDate.parse(p[8]);
        c.status = ComplaintStatus.valueOf(p[9]);
        c.resolutionNotes = p[10];
        return c;
    }

    @Override
    public String toString() {
        return String.format(
                "Ref: %s | %s | %s | Location: %s | Category: %s | Urgency: %s | Status: %s%n  Description: %s%n  Notes: %s",
                referenceNumber, dateReported, complainantName, location, category,
                urgencyLevel, status, description,
                resolutionNotes.isEmpty() ? "(none yet)" : resolutionNotes);
    }
}

class ComplaintManager {

    private List<Complaint> complaints;
    private int nextRefNumber;
    private static final String FILE_NAME = "complaints.txt";

    public ComplaintManager() {
        complaints = new ArrayList<>();
        nextRefNumber = 1;
    }

    public Complaint createComplaint(String name, boolean anonymous, String contact,
                                    String location, ServiceCategory category,
                                    String description, String urgency) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        String ref = String.format("CC-%04d", nextRefNumber++);
        Complaint c = new Complaint(ref, name, anonymous, contact, location, category, description, urgency);
        complaints.add(c);
        return c;
    }

    public Complaint searchByReference(String ref) {
        for (Complaint c : complaints) {
            if (c.getReferenceNumber().equalsIgnoreCase(ref)) {
                return c;
            }
        }
        return null;
    }

    public List<Complaint> getAllComplaints() {
        return complaints;
    }

    public List<Complaint> filter(ServiceCategory category, String location,
                                String urgency, ComplaintStatus status) {
        List<Complaint> result = new ArrayList<>();
        for (Complaint c : complaints) {
            boolean matches = true;
            if (category != null && c.getCategory() != category) matches = false;
            if (location != null && !c.getLocation().equalsIgnoreCase(location)) matches = false;
            if (urgency != null && !c.getUrgencyLevel().equalsIgnoreCase(urgency)) matches = false;
            if (status != null && c.getStatus() != status) matches = false;
            if (matches) result.add(c);
        }
        return result;
    }

    public boolean updateStatus(String ref, ComplaintStatus newStatus) {
        Complaint c = searchByReference(ref);
        if (c == null) return false;
        c.setStatus(newStatus);
        return true;
    }

    public boolean addResolutionNotes(String ref, String notes) {
        Complaint c = searchByReference(ref);
        if (c == null) return false;
        c.setResolutionNotes(notes);
        return true;
    }

    public int countUnresolved() {
        int count = 0;
        for (Complaint c : complaints) {
            if (c.isUnresolved()) count++;
        }
        return count;
    }

    public String generateSummary() {
        Map<ServiceCategory, Integer> byCategory = new LinkedHashMap<>();
        Map<ComplaintStatus, Integer> byStatus = new LinkedHashMap<>();

        for (ServiceCategory cat : ServiceCategory.values()) byCategory.put(cat, 0);
        for (ComplaintStatus st : ComplaintStatus.values()) byStatus.put(st, 0);

        for (Complaint c : complaints) {
            byCategory.put(c.getCategory(), byCategory.get(c.getCategory()) + 1);
            byStatus.put(c.getStatus(), byStatus.get(c.getStatus()) + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== COMPLAINT SUMMARY REPORT ===\n");
        sb.append("Total complaints: ").append(complaints.size()).append("\n");
        sb.append("Unresolved complaints: ").append(countUnresolved()).append("\n\n");

        sb.append("-- By Category --\n");
        for (var entry : byCategory.entrySet()) {
            sb.append(String.format("%-20s : %d%n", entry.getKey(), entry.getValue()));
        }

        sb.append("\n-- By Status --\n");
        for (var entry : byStatus.entrySet()) {
            sb.append(String.format("%-20s : %d%n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

    public void saveToFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Complaint c : complaints) {
                writer.println(c.toFileString());
            }
        }
    }

    public void loadFromFile() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        complaints.clear();
        int maxRef = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Complaint c = Complaint.fromFileString(line);
                complaints.add(c);
                String digits = c.getReferenceNumber().replaceAll("[^0-9]", "");
                if (!digits.isEmpty()) {
                    maxRef = Math.max(maxRef, Integer.parseInt(digits));
                }
            }
        }
        nextRefNumber = maxRef + 1;
    }
}
