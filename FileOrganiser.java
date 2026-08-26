// ============================================================
//   FILE ORGANISER v1.0 (Java version)
//   A Java automation tool that sorts files into folders
//   and generates a CSV report of every action taken.
// ============================================================

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileOrganiser {

    // ============================================================
    // SECTION 1 — CONFIGURATION
    // Add or remove file types here to customise sorting rules.
    // ============================================================

    private static final Map<String, List<String>> FILE_CATEGORIES = new LinkedHashMap<>();
    static {
        FILE_CATEGORIES.put("Images", Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".svg", ".webp"));
        FILE_CATEGORIES.put("Documents", Arrays.asList(".pdf", ".docx", ".doc", ".txt", ".pptx", ".xlsx", ".csv"));
        FILE_CATEGORIES.put("Audio", Arrays.asList(".mp3", ".wav", ".aac", ".flac", ".ogg"));
        FILE_CATEGORIES.put("Video", Arrays.asList(".mp4", ".mov", ".avi", ".mkv", ".wmv"));
        FILE_CATEGORIES.put("Code", Arrays.asList(".py", ".js", ".html", ".css", ".java", ".cpp", ".ts"));
        FILE_CATEGORIES.put("Archives", Arrays.asList(".zip", ".tar", ".gz", ".rar", ".7z"));
        FILE_CATEGORIES.put("Others", Collections.emptyList()); // Catch-all for unrecognised file types
    }

    // A simple record-like class to store one log entry
    // (an "action" is one file being moved)
    static class LogEntry {
        String file;
        String category;
        String movedTo;
        String time;

        LogEntry(String file, String category, String movedTo, String time) {
            this.file = file;
            this.category = category;
            this.movedTo = movedTo;
            this.time = time;
        }
    }

    // ============================================================
    // SECTION 2 — ORGANISER LOGIC
    // ============================================================

    /**
     * Takes a file extension (e.g. ".pdf") and returns
     * the matching category name (e.g. "Documents").
     * Returns "Others" if no match is found.
     */
    private static String getCategory(String extension) {
        String ext = extension.toLowerCase();
        for (Map.Entry<String, List<String>> entry : FILE_CATEGORIES.entrySet()) {
            if (entry.getValue().contains(ext)) {
                return entry.getKey();
            }
        }
        return "Others";
    }

    /**
     * Scans the given folder, sorts files into subfolders
     * by category, and returns a log of all actions taken.
     */
    private static List<LogEntry> organiseFolder(String folderPath) {
        File folder = new File(folderPath);
        List<LogEntry> log = new ArrayList<>();

        // Check that the folder actually exists
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("❌ Error: '" + folderPath + "' is not a valid folder.");
            return log;
        }

        File[] files = folder.listFiles();
        if (files == null) {
            return log;
        }

        for (File file : files) {

            // Skip subfolders — we only want to move files
            if (!file.isFile()) {
                continue;
            }

            String fileName = file.getName();
            String extension = getExtension(fileName);
            String category = getCategory(extension);

            File destinationDir = new File(folder, category);

            // Create the subfolder if it doesn't exist yet
            if (!destinationDir.exists()) {
                destinationDir.mkdirs();
            }

            File newPath = new File(destinationDir, fileName);

            // If a file with the same name already exists, rename it with a timestamp
            if (newPath.exists()) {
                String stem = getStem(fileName);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
                String newName = stem + "_" + timestamp + extension;
                newPath = new File(destinationDir, newName);
            }

            // Move the file
            try {
                Files.move(file.toPath(), newPath.toPath());
            } catch (IOException e) {
                System.out.println("⚠️  Failed to move " + fileName + ": " + e.getMessage());
                continue;
            }

            // Record the action
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.add(new LogEntry(fileName, category, newPath.toString(), time));

            System.out.println("✅ Moved: " + fileName + " → " + category + "/");
        }

        return log;
    }

    /** Returns the extension of a filename, including the dot (e.g. ".pdf"). */
    private static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex <= 0 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex);
    }

    /** Returns the filename without its extension (e.g. "report" from "report.pdf"). */
    private static String getStem(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex <= 0) {
            return fileName;
        }
        return fileName.substring(0, dotIndex);
    }

    // ============================================================
    // SECTION 3 — REPORT GENERATION
    // ============================================================

    /**
     * Takes the action log and writes it to a
     * timestamped CSV file inside the reports folder.
     */
    private static String generateReport(List<LogEntry> log, String reportFolder) throws IOException {
        File dir = new File(reportFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String reportName = "report_" + timestamp + ".csv";
        File reportFile = new File(dir, reportName);

        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
            writer.println("file,category,moved_to,time");
            for (LogEntry entry : log) {
                writer.println(
                    escapeCsv(entry.file) + "," +
                    escapeCsv(entry.category) + "," +
                    escapeCsv(entry.movedTo) + "," +
                    escapeCsv(entry.time)
                );
            }
        }

        System.out.println("\n📊 Report saved: " + reportFile.getPath());
        return reportFile.getPath();
    }

    /** Wraps a CSV field in quotes if it contains a comma, quote, or newline. */
    private static String escapeCsv(String field) {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    /**
     * Prints a quick summary to the terminal showing
     * how many files landed in each category.
     */
    private static void printSummary(List<LogEntry> log) {
        Map<String, Integer> summary = new LinkedHashMap<>();

        for (LogEntry entry : log) {
            summary.merge(entry.category, 1, Integer::sum);
        }

        System.out.println("\n========== SUMMARY ==========");
        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.printf("  %-12s → %d file(s)%n", entry.getKey(), entry.getValue());
        }
        System.out.printf("  %-12s → %d file(s)%n", "TOTAL", log.size());
        System.out.println("==============================\n");
    }

    // ============================================================
    // SECTION 4 — MAIN ENTRY POINT
    // ============================================================

    public static void main(String[] args) throws IOException {
        System.out.println("========================================");
        System.out.println("       🗂️  FILE ORGANISER v1.0          ");
        System.out.println("========================================\n");

        // Ask the user which folder to organise
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the full path of the folder to organise:\n> ");
        String folderPath = scanner.nextLine().trim();

        System.out.println("\n🔍 Scanning '" + folderPath + "'...\n");

        // Run the organiser and get the action log
        List<LogEntry> log = organiseFolder(folderPath);

        if (log.isEmpty()) {
            System.out.println("⚠️  No files were moved. The folder may be empty or invalid.");
            return;
        }

        // Print summary and save the CSV report
        printSummary(log);

        String jarDir = new File(FileOrganiser.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath()).getParent();
        String reportFolder = new File(jarDir, "reports").getPath();
        generateReport(log, reportFolder);
    }
}
