import java.io.*;
import java.time.LocalDate;
import java.util.*;

// ============================================================
// CommunityConnect
// This is my project for the Service Delivery Technology
// Challenge. It lets a community member report a problem
// (like no water or a broken streetlight) and lets an official
// update the status of that problem.
//
// I put everything in one file to make it easier for me to
// keep track of while I am still learning Java.
// ============================================================

public class CommunityConnect {

    // I made these static so I can use them inside my static
    // methods without passing them around everywhere.
    static Scanner input = new Scanner(System.in);
    static ArrayList<Complaint> complaintList = new ArrayList<Complaint>();
    static int refCounter = 1; // used to make reference numbers like CC-0001

    public static void main(String[] args) {

        // Try to load any complaints that were saved before
        loadComplaints();

        int userChoice = -1;

        // This is my main menu loop. It keeps running until the
        // user picks 0 to exit.
        while (userChoice != 0) {

            System.out.println("");
            System.out.println("========== CommunityConnect Menu ==========");
            System.out.println("1. Report a new complaint");
            System.out.println("2. Search for a complaint by reference number");
            System.out.println("3. Show all complaints");
            System.out.println("4. Filter complaints");
            System.out.println("5. Update the status of a complaint");
            System.out.println("6. Add resolution notes to a complaint");
            System.out.println("7. Count unresolved complaints");
            System.out.println("8. Show summary report");
            System.out.println("9. Save complaints to file");
            System.out.println("10. Load 20 sample complaints (for testing/demo)");
            System.out.println("0. Save and Exit");
            System.out.print("Enter your choice: ");

            // I wrapped this in a try-catch because if the user types
            // letters instead of a number, the program would crash
            // without it (this is called exception handling).
            try {
                userChoice = Integer.parseInt(input.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Please type a number from the menu.");
                userChoice = -1;
                continue; // skip the rest of the loop and show menu again
            }

            if (userChoice == 1) {
                reportComplaint();
            } else if (userChoice == 2) {
                searchComplaint();
            } else if (userChoice == 3) {
                showAllComplaints();
            } else if (userChoice == 4) {
                filterComplaints();
            } else if (userChoice == 5) {
                updateComplaintStatus();
            } else if (userChoice == 6) {
                addNotes();
            } else if (userChoice == 7) {
                countUnresolved();
            } else if (userChoice == 8) {
                showSummary();
            } else if (userChoice == 9) {
                saveComplaints();
            } else if (userChoice == 10) {
                loadSampleData();
            } else if (userChoice == 0) {
                saveComplaints();
                System.out.println("Goodbye!");
            } else {
                System.out.println("That is not a valid option, try again.");
            }
        }
    }

    // ------------------------------------------------------
    // Option 1: Create a new complaint
    // ------------------------------------------------------
    static void reportComplaint() {

        System.out.print("Is this complaint anonymous? (yes/no): ");
        String anonAnswer = input.nextLine().trim();
        boolean isAnonymous = anonAnswer.equalsIgnoreCase("yes");

        String complainantName = "Anonymous";
        if (isAnonymous == false) {
            System.out.print("Enter your name: ");
            complainantName = input.nextLine().trim();
        }

        System.out.print("Enter your contact number (or leave blank): ");
        String contact = input.nextLine().trim();

        System.out.print("Enter your community or location: ");
        String location = input.nextLine().trim();

        // simple validation - I learned this is important so the
        // program does not save empty/blank data
        if (location.length() == 0) {
            System.out.println("Location cannot be empty. Complaint was not saved.");
            return;
        }

        System.out.println("Choose a service category:");
        System.out.println("1. Water");
        System.out.println("2. Electricity");
        System.out.println("3. Refuse (garbage)");
        System.out.println("4. Drainage / Sewage");
        System.out.println("5. Roads / Potholes");
        System.out.println("6. Streetlights");
        System.out.println("7. Public Facilities");
        System.out.print("Enter number 1-7: ");

        String category = "";
        int categoryChoice = 0;
        try {
            categoryChoice = Integer.parseInt(input.nextLine().trim());
        } catch (Exception e) {
            categoryChoice = 0;
        }

        switch (categoryChoice) {
            case 1:
                category = "Water";
                break;
            case 2:
                category = "Electricity";
                break;
            case 3:
                category = "Refuse";
                break;
            case 4:
                category = "Drainage/Sewage";
                break;
            case 5:
                category = "Roads/Potholes";
                break;
            case 6:
                category = "Streetlights";
                break;
            case 7:
                category = "Public Facilities";
                break;
            default:
                category = "Other";
                break;
        }

        System.out.print("Describe the problem: ");
        String description = input.nextLine().trim();

        if (description.length() == 0) {
            System.out.println("Description cannot be empty. Complaint was not saved.");
            return;
        }

        System.out.print("Urgency level (Low, Medium, High, Critical): ");
        String urgency = input.nextLine().trim();

        // build the reference number, something like CC-0001
        String refNumber = "CC-" + String.format("%04d", refCounter);
        refCounter = refCounter + 1;

        Complaint newComplaint = new Complaint(refNumber, complainantName, isAnonymous,
                contact, location, category, description, urgency);

        complaintList.add(newComplaint);

        System.out.println("Thank you! Your complaint was recorded.");
        System.out.println("Your reference number is: " + refNumber);
    }

    // ------------------------------------------------------
    // Option 2: Search for one complaint
    // ------------------------------------------------------
    static void searchComplaint() {
        System.out.print("Enter the reference number to search for: ");
        String ref = input.nextLine().trim();

        boolean found = false;
        for (int i = 0; i < complaintList.size(); i++) {
            Complaint c = complaintList.get(i);
            if (c.referenceNumber.equalsIgnoreCase(ref)) {
                System.out.println(c.toDisplayString());
                found = true;
                break; // stop looking once we found it
            }
        }

        if (found == false) {
            System.out.println("No complaint found with that reference number.");
        }
    }

    // ------------------------------------------------------
    // Option 3: Show every complaint
    // ------------------------------------------------------
    static void showAllComplaints() {
        if (complaintList.size() == 0) {
            System.out.println("There are no complaints yet.");
            return;
        }

        for (int i = 0; i < complaintList.size(); i++) {
            System.out.println(complaintList.get(i).toDisplayString());
            System.out.println("-----------------------------------");
        }
    }

    // ------------------------------------------------------
    // Option 4: Filter complaints by category/location/status
    // ------------------------------------------------------
    static void filterComplaints() {
        System.out.println("Leave a field blank if you do not want to filter by it.");

        System.out.print("Category (e.g. Water, Electricity): ");
        String category = input.nextLine().trim();

        System.out.print("Location: ");
        String location = input.nextLine().trim();

        System.out.print("Status (Reported, Verified, Assigned, In Progress, Resolved, Closed): ");
        String status = input.nextLine().trim();

        boolean anyMatch = false;

        for (int i = 0; i < complaintList.size(); i++) {
            Complaint c = complaintList.get(i);

            boolean matchesCategory = category.length() == 0 || c.category.equalsIgnoreCase(category);
            boolean matchesLocation = location.length() == 0 || c.location.equalsIgnoreCase(location);
            boolean matchesStatus = status.length() == 0 || c.status.equalsIgnoreCase(status);

            if (matchesCategory && matchesLocation && matchesStatus) {
                System.out.println(c.toDisplayString());
                System.out.println("-----------------------------------");
                anyMatch = true;
            }
        }

        if (anyMatch == false) {
            System.out.println("No complaints matched your filter.");
        }
    }

    // ------------------------------------------------------
    // Option 5: Update the status of a complaint
    // ------------------------------------------------------
    static void updateComplaintStatus() {
        System.out.print("Enter the reference number: ");
        String ref = input.nextLine().trim();

        Complaint found = null;
        for (int i = 0; i < complaintList.size(); i++) {
            if (complaintList.get(i).referenceNumber.equalsIgnoreCase(ref)) {
                found = complaintList.get(i);
            }
        }

        if (found == null) {
            System.out.println("Complaint not found.");
            return;
        }

        System.out.println("Choose new status:");
        System.out.println("1. Reported");
        System.out.println("2. Verified");
        System.out.println("3. Assigned");
        System.out.println("4. In Progress");
        System.out.println("5. Resolved");
        System.out.println("6. Closed or Rejected");
        System.out.print("Enter number 1-6: ");

        int statusChoice = 0;
        try {
            statusChoice = Integer.parseInt(input.nextLine().trim());
        } catch (Exception e) {
            statusChoice = 0;
        }

        switch (statusChoice) {
            case 1:
                found.status = "Reported";
                break;
            case 2:
                found.status = "Verified";
                break;
            case 3:
                found.status = "Assigned";
                break;
            case 4:
                found.status = "In Progress";
                break;
            case 5:
                found.status = "Resolved";
                break;
            case 6:
                found.status = "Closed or Rejected";
                break;
            default:
                System.out.println("Invalid choice, status was not changed.");
                return;
        }

        System.out.println("Status updated to: " + found.status);
    }

    // ------------------------------------------------------
    // Option 6: Add resolution notes
    // ------------------------------------------------------
    static void addNotes() {
        System.out.print("Enter the reference number: ");
        String ref = input.nextLine().trim();

        Complaint found = null;
        for (int i = 0; i < complaintList.size(); i++) {
            if (complaintList.get(i).referenceNumber.equalsIgnoreCase(ref)) {
                found = complaintList.get(i);
            }
        }

        if (found == null) {
            System.out.println("Complaint not found.");
            return;
        }

        System.out.print("Enter resolution notes: ");
        String notes = input.nextLine().trim();
        found.resolutionNotes = notes;
        System.out.println("Notes saved.");
    }

    // ------------------------------------------------------
    // Option 7: Count how many complaints are still unresolved
    // ------------------------------------------------------
    static void countUnresolved() {
        int count = 0;
        for (int i = 0; i < complaintList.size(); i++) {
            Complaint c = complaintList.get(i);
            if (!c.status.equalsIgnoreCase("Resolved") && !c.status.equalsIgnoreCase("Closed or Rejected")) {
                count = count + 1;
            }
        }
        System.out.println("Unresolved complaints: " + count);
    }

    // ------------------------------------------------------
    // Option 8: Show a summary report (counts per category and status)
    // ------------------------------------------------------
    static void showSummary() {
        // I used a HashMap here to count how many complaints are
        // in each category, and how many are in each status.
        HashMap<String, Integer> categoryCounts = new HashMap<String, Integer>();
        HashMap<String, Integer> statusCounts = new HashMap<String, Integer>();

        for (int i = 0; i < complaintList.size(); i++) {
            Complaint c = complaintList.get(i);

            // category counting
            if (categoryCounts.containsKey(c.category)) {
                categoryCounts.put(c.category, categoryCounts.get(c.category) + 1);
            } else {
                categoryCounts.put(c.category, 1);
            }

            // status counting
            if (statusCounts.containsKey(c.status)) {
                statusCounts.put(c.status, statusCounts.get(c.status) + 1);
            } else {
                statusCounts.put(c.status, 1);
            }
        }

        System.out.println("=== SUMMARY REPORT ===");
        System.out.println("Total complaints: " + complaintList.size());

        System.out.println("");
        System.out.println("By Category:");
        for (String key : categoryCounts.keySet()) {
            System.out.println(key + " : " + categoryCounts.get(key));
        }

        System.out.println("");
        System.out.println("By Status:");
        for (String key : statusCounts.keySet()) {
            System.out.println(key + " : " + statusCounts.get(key));
        }
    }

    // ------------------------------------------------------
    // Option 9: Save complaints to a text file
    // ------------------------------------------------------
    static void saveComplaints() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("complaints.txt"));
            for (int i = 0; i < complaintList.size(); i++) {
                writer.println(complaintList.get(i).toFileLine());
            }
            writer.close();
            System.out.println("Complaints saved to complaints.txt");
        } catch (IOException e) {
            System.out.println("Something went wrong while saving: " + e.getMessage());
        }
    }

    // Loads complaints.txt when the program starts (if it exists)
    static void loadComplaints() {
        File file = new File("complaints.txt");
        if (!file.exists()) {
            return; // nothing to load, that's ok for a first run
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                if (line.trim().length() > 0) {
                    Complaint c = Complaint.fromFileLine(line);
                    complaintList.add(c);

                    // keep our reference counter ahead of loaded data
                    String numberPart = c.referenceNumber.replace("CC-", "");
                    try {
                        int num = Integer.parseInt(numberPart);
                        if (num >= refCounter) {
                            refCounter = num + 1;
                        }
                    } catch (Exception e) {
                        // ignore if it can't be parsed
                    }
                }
                line = reader.readLine();
            }
            reader.close();
            System.out.println("Loaded " + complaintList.size() + " complaint(s) from file.");
        } catch (IOException e) {
            System.out.println("Could not load complaints: " + e.getMessage());
        }
    }

    // ------------------------------------------------------
    // Option 10: Quickly load 20 sample complaints for testing
    // ------------------------------------------------------
    static void loadSampleData() {
        String[][] samples = {
            {"Thandeka Zulu", "0821234567", "Richards Bay", "Water", "Burst pipe flooding Church Street", "High"},
            {"Anonymous", "", "Empangeni", "Electricity", "Transformer sparking near taxi rank", "Critical"},
            {"Sipho Ngcobo", "0731239988", "Esikhawini", "Refuse", "Refuse not collected for three weeks", "Medium"},
            {"Nomvula Dlamini", "0844567123", "Richards Bay CBD", "Drainage/Sewage", "Sewage overflowing onto pavement", "Critical"},
            {"Anonymous", "", "Meerensee", "Roads/Potholes", "Large pothole damaging vehicles", "High"},
            {"Bongani Khumalo", "0768892211", "Brackenham", "Streetlights", "Streetlights off for two weeks", "Medium"},
            {"Ayanda Mthembu", "0722345678", "Aquadene", "Public Facilities", "Community hall roof leaking", "Low"},
            {"Lindiwe Buthelezi", "0835551122", "Ngwelezane", "Water", "No water supply for four days", "Critical"},
            {"Anonymous", "", "eSikhaleni", "Electricity", "Frequent power outages every evening", "High"},
            {"Mandla Cele", "0715557890", "Richards Bay", "Refuse", "Illegal dumping site near school", "Medium"},
            {"Zanele Mkhize", "0823214567", "Empangeni", "Drainage/Sewage", "Blocked stormwater drain flooding road", "High"},
            {"Anonymous", "", "Meerensee", "Roads/Potholes", "Road subsidence forming a sinkhole", "Critical"},
            {"Thabo Nkosi", "0768871234", "Brackenham", "Streetlights", "Broken pole leaning over walkway", "Medium"},
            {"Precious Zungu", "0847761234", "Esikhawini", "Public Facilities", "Public toilets vandalised", "Medium"},
            {"Sibusiso Ndlovu", "0731122334", "Richards Bay CBD", "Water", "Leaking pipe wasting water", "Medium"},
            {"Anonymous", "", "Ngwelezane", "Electricity", "Exposed live wires near a playground", "Critical"},
            {"Nokuthula Mahlangu", "0822239988", "eSikhaleni", "Refuse", "Overflowing bins attracting pests", "Low"},
            {"Musa Sithole", "0761239900", "Aquadene", "Drainage/Sewage", "Manhole cover missing, safety hazard", "High"},
            {"Anonymous", "", "Empangeni", "Roads/Potholes", "Multiple potholes on school route", "High"},
            {"Fikile Mbeki", "0845678321", "Richards Bay", "Public Facilities", "Broken playground equipment", "Low"}
        };

        for (int i = 0; i < samples.length; i++) {
            String name = samples[i][0];
            boolean anon = name.equals("Anonymous");
            String refNumber = "CC-" + String.format("%04d", refCounter);
            refCounter = refCounter + 1;

            Complaint c = new Complaint(refNumber, name, anon, samples[i][1], samples[i][2],
                    samples[i][3], samples[i][4], samples[i][5]);
            complaintList.add(c);
        }

        System.out.println("20 sample complaints loaded. Total complaints now: " + complaintList.size());
    }
}


// ============================================================
// This is my Complaint class. It just holds the information
// about ONE complaint. I made the fields public to keep things
// simple for now since this is one of my first Java projects.
// ============================================================
class Complaint {

    String referenceNumber;
    String complainantName;
    boolean anonymous;
    String contactInfo;
    String location;
    String category;
    String description;
    String urgencyLevel;
    String dateReported;
    String status;
    String resolutionNotes;

    // constructor - this runs automatically when I create a new Complaint
    public Complaint(String referenceNumber, String complainantName, boolean anonymous,
                      String contactInfo, String location, String category,
                      String description, String urgencyLevel) {
        this.referenceNumber = referenceNumber;
        this.complainantName = complainantName;
        this.anonymous = anonymous;
        this.contactInfo = contactInfo;
        this.location = location;
        this.category = category;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.dateReported = LocalDate.now().toString();
        this.status = "Reported"; // every new complaint starts as Reported
        this.resolutionNotes = "";
    }

    // Turns this complaint into a nice looking block of text to print
    public String toDisplayString() {
        String text = "Reference: " + referenceNumber + "\n";
        text = text + "Date Reported: " + dateReported + "\n";
        text = text + "Name: " + complainantName + "\n";
        text = text + "Location: " + location + "\n";
        text = text + "Category: " + category + "\n";
        text = text + "Urgency: " + urgencyLevel + "\n";
        text = text + "Status: " + status + "\n";
        text = text + "Description: " + description + "\n";

        if (resolutionNotes.length() == 0) {
            text = text + "Notes: (none yet)";
        } else {
            text = text + "Notes: " + resolutionNotes;
        }

        return text;
    }

    // Turns this complaint into one line of text so I can save it
    // to a file. I used "|" to separate the fields.
    public String toFileLine() {
        return referenceNumber + "|" + complainantName + "|" + anonymous + "|" + contactInfo
                + "|" + location + "|" + category + "|" + description + "|" + urgencyLevel
                + "|" + dateReported + "|" + status + "|" + resolutionNotes;
    }

    // Rebuilds a Complaint object from one saved line of text.
    // This is the opposite of toFileLine().
    public static Complaint fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);

        Complaint c = new Complaint(parts[0], parts[1], Boolean.parseBoolean(parts[2]),
                parts[3], parts[4], parts[5], parts[6], parts[7]);
        c.dateReported = parts[8];
        c.status = parts[9];
        c.resolutionNotes = parts[10];
        return c;
    }
}
