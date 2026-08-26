import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

public class FileOrganiser_1 {

    private static final Map<String, List<String>> FILE_CATEGORIES = new LinkedHashMap<>();
    static {
        FILE_CATEGORIES.put("Images", Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".svg", ".webp"));
        FILE_CATEGORIES.put("Documents", Arrays.asList(".pdf", ".docx", ".doc", ".txt", ".pptx", ".xlsx", ".csv"));
        FILE_CATEGORIES.put("Audio", Arrays.asList(".mp3", ".wav", ".aac", ".flac", ".ogg"));
        FILE_CATEGORIES.put("Video", Arrays.asList(".mp4", ".mov", ".avi", ".mkv", ".wmv"));
        FILE_CATEGORIES.put("Code", Arrays.asList(".py", ".js", ".html", ".css", ".java", ".cpp", ".ts"));
        FILE_CATEGORIES.put("Archives", Arrays.asList(".zip", ".tar", ".gz", ".rar", ".7z"));
        FILE_CATEGORIES.put("Others", Collections.emptyList());
    }

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

    static class SearchResult {
        String name;
        String path;
        long sizeBytes;
        String lastModified;

        SearchResult(String name, String path, long sizeBytes, String lastModified) {
            this.name = name;
            this.path = path;
            this.sizeBytes = sizeBytes;
            this.lastModified = lastModified;
        }
    }

    private static String getCategory(String extension) {
        String ext = extension.toLowerCase();
        for (Map.Entry<String, List<String>> entry : FILE_CATEGORIES.entrySet()) {
            if (entry.getValue().contains(ext)) {
                return entry.getKey();
            }
        }
        return "Others";
    }

    private static List<LogEntry> organiseFolder(String folderPath) {
        File folder = new File(folderPath);
        List<LogEntry> log = new ArrayList<>();

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println(" Error: '" + folderPath + "' is not a valid folder.");
            return log;
        }

        File[] files = folder.listFiles();
        if (files == null) {
            return log;
        }

        for (File file : files) {

            if (!file.isFile()) {
                continue;
            }

            String fileName = file.getName();
            String extension = getExtension(fileName);
            String category = getCategory(extension);

            File destinationDir = new File(folder, category);

            if (!destinationDir.exists()) {
                destinationDir.mkdirs();
            }

            File newPath = new File(destinationDir, fileName);

            if (newPath.exists()) {
                String stem = getStem(fileName);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
                String newName = stem + "_" + timestamp + extension;
                newPath = new File(destinationDir, newName);
            }

            try {
                Files.move(file.toPath(), newPath.toPath());
            } catch (IOException e) {
                System.out.println("  Failed to move " + fileName + ": " + e.getMessage());
                continue;
            }

            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.add(new LogEntry(fileName, category, newPath.toString(), time));

            System.out.println("Moved: " + fileName + " → " + category + "/");
        }

        return log;
    }

    private static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex <= 0 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex);
    }

    private static String getStem(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex <= 0) {
            return fileName;
        }
        return fileName.substring(0, dotIndex);
    }

    // ============================================================
    // SECTION 2B — RECURSIVE SEARCH
    // ============================================================

    /**
     * Recursively searches folderPath (and every subfolder) for files whose
     * name contains the given query, case-insensitively. The query can be
     * a whole filename, a partial name, or an extension like ".pdf".
     */
    private static List<SearchResult> searchFolder(String folderPath, String query) {
        List<SearchResult> results = new ArrayList<>();
        Path root = Paths.get(folderPath);

        if (!Files.exists(root) || !Files.isDirectory(root)) {
            System.out.println(" Error: '" + folderPath + "' is not a valid folder.");
            return results;
        }

        String needle = query.toLowerCase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().toLowerCase().contains(needle))
                .forEach(path -> {
                    try {
                        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                        String modified = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(attrs.lastModifiedTime().toMillis()),
                                ZoneId.systemDefault()
                        ).format(formatter);
                        results.add(new SearchResult(
                                path.getFileName().toString(),
                                path.toString(),
                                attrs.size(),
                                modified
                        ));
                    } catch (IOException e) {
                        System.out.println("  Could not read attributes for " + path + ": " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            System.out.println(" Error while searching '" + folderPath + "': " + e.getMessage());
        }

        return results;
    }

    private static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String unit = "KMGTPE".charAt(exp - 1) + "B";
        return String.format("%.1f %s", bytes / Math.pow(1024, exp), unit);
    }

    private static void printSearchResults(List<SearchResult> results, String query) {
        if (results.isEmpty()) {
            System.out.println("\n No files matching \"" + query + "\" were found.\n");
            return;
        }

        System.out.println("\n========== SEARCH RESULTS ==========");
        System.out.println("  Query: \"" + query + "\"  |  Matches: " + results.size());
        System.out.println("-------------------------------------");
        for (SearchResult r : results) {
            System.out.println("  " + r.name);
            System.out.println("    Path:     " + r.path);
            System.out.println("    Size:     " + formatSize(r.sizeBytes));
            System.out.println("    Modified: " + r.lastModified);
        }
        System.out.println("=====================================\n");
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

        System.out.println("\n Report saved: " + reportFile.getPath());
        return reportFile.getPath();
    }

    /**
     * Writes search results to a timestamped CSV report, mirroring
     * generateReport's format but for SearchResult entries.
     */
    private static String generateSearchReport(List<SearchResult> results, String reportFolder) throws IOException {
        File dir = new File(reportFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String reportName = "search_" + timestamp + ".csv";
        File reportFile = new File(dir, reportName);

        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
            writer.println("name,path,size_bytes,last_modified");
            for (SearchResult r : results) {
                writer.println(
                    escapeCsv(r.name) + "," +
                    escapeCsv(r.path) + "," +
                    r.sizeBytes + "," +
                    escapeCsv(r.lastModified)
                );
            }
        }

        System.out.println(" Report saved: " + reportFile.getPath());
        return reportFile.getPath();
    }

    private static String escapeCsv(String field) {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }


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

    private static String getReportFolder() {
        String jarDir = new File(FileOrganiser_1.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath()).getParent();
        return new File(jarDir, "reports").getPath();
    }

    private static void runOrganise(Scanner scanner) throws IOException {
        System.out.print("Enter the full path of the folder to organise:\n> ");
        String folderPath = scanner.nextLine().trim();

        System.out.println("\n Scanning '" + folderPath + "'...\n");

        List<LogEntry> log = organiseFolder(folderPath);

        if (log.isEmpty()) {
            System.out.println("  No files were moved. The folder may be empty or invalid.");
            return;
        }

        printSummary(log);
        generateReport(log, getReportFolder());
    }

    private static void runSearch(Scanner scanner) throws IOException {
        System.out.print("Enter the full path of the folder to search:\n> ");
        String folderPath = scanner.nextLine().trim();

        System.out.print("Enter a filename, partial name, or extension to search for (e.g. report or .pdf):\n> ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("  Search query cannot be empty.");
            return;
        }

        System.out.println("\n Searching '" + folderPath + "' and all subfolders for \"" + query + "\"...");

        List<SearchResult> results = searchFolder(folderPath, query);
        printSearchResults(results, query);

        if (!results.isEmpty()) {
            System.out.print("Save these results to a CSV report? (y/n)\n> ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y") || choice.equals("yes")) {
                generateSearchReport(results, getReportFolder());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("========================================");
        System.out.println("          FILE ORGANISER v2.0          ");
        System.out.println("========================================\n");

        Scanner scanner = new Scanner(System.in);

        System.out.println("What would you like to do?");
        System.out.println("  1. Organise a folder (sort files into categories)");
        System.out.println("  2. Search a folder (recursively find files by name/extension)");
        System.out.print("> ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                runOrganise(scanner);
                break;
            case "2":
                runSearch(scanner);
                break;
            default:
                System.out.println("  Invalid choice. Please enter 1 or 2.");
        }
    }
}
