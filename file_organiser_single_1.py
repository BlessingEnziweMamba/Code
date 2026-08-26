# ============================================================
#   FILE ORGANISER v1.0
#   A Python automation tool that sorts files into folders
#   and generates a CSV report of every action taken.
# ============================================================

import os
import csv
import shutil
from pathlib import Path
from datetime import datetime


# ============================================================
# SECTION 1 — CONFIGURATION
# Add or remove file types here to customise sorting rules.
# ============================================================

FILE_CATEGORIES = {
    "Images":     [".jpg", ".jpeg", ".png", ".gif", ".bmp", ".svg", ".webp"],
    "Documents":  [".pdf", ".docx", ".doc", ".txt", ".pptx", ".xlsx", ".csv"],
    "Audio":      [".mp3", ".wav", ".aac", ".flac", ".ogg"],
    "Video":      [".mp4", ".mov", ".avi", ".mkv", ".wmv"],
    "Code":       [".py", ".js", ".html", ".css", ".java", ".cpp", ".ts"],
    "Archives":   [".zip", ".tar", ".gz", ".rar", ".7z"],
    "Others":     []  # Catch-all for unrecognised file types
}


# ============================================================
# SECTION 2 — ORGANISER LOGIC
# ============================================================

def get_category(file_extension):
    """
    Takes a file extension (e.g. '.pdf') and returns
    the matching category name (e.g. 'Documents').
    Returns 'Others' if no match is found.
    """
    for category, extensions in FILE_CATEGORIES.items():
        if file_extension.lower() in extensions:
            return category
    return "Others"


def organise_folder(folder_path):
    """
    Scans the given folder, sorts files into subfolders
    by category, and returns a log of all actions taken.
    """
    folder = Path(folder_path)

    # Check that the folder actually exists
    if not folder.exists() or not folder.is_dir():
        print(f"❌ Error: '{folder_path}' is not a valid folder.")
        return []

    log = []  # Stores every action for the report

    for file in folder.iterdir():

        # Skip subfolders — we only want to move files
        if not file.is_file():
            continue

        extension   = file.suffix           # e.g. '.pdf'
        category    = get_category(extension)
        destination = folder / category     # e.g. Downloads/Documents

        # Create the subfolder if it doesn't exist yet
        destination.mkdir(exist_ok=True)

        new_path = destination / file.name

        # If a file with the same name already exists, rename it with a timestamp
        if new_path.exists():
            timestamp = datetime.now().strftime("%H%M%S")
            new_name  = f"{file.stem}_{timestamp}{file.suffix}"
            new_path  = destination / new_name

        # Move the file
        shutil.move(str(file), str(new_path))

        # Record the action
        log.append({
            "file":     file.name,
            "category": category,
            "moved_to": str(new_path),
            "time":     datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        })

        print(f"✅ Moved: {file.name} → {category}/")

    return log


# ============================================================
# SECTION 3 — REPORT GENERATION
# ============================================================

def generate_report(log, report_folder="reports"):
    """
    Takes the action log and writes it to a
    timestamped CSV file inside the reports folder.
    """
    os.makedirs(report_folder, exist_ok=True)

    timestamp   = datetime.now().strftime("%Y%m%d_%H%M%S")
    report_name = f"report_{timestamp}.csv"
    report_path = os.path.join(report_folder, report_name)

    with open(report_path, "w", newline="") as csvfile:
        fieldnames = ["file", "category", "moved_to", "time"]
        writer     = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(log)

    print(f"\n📊 Report saved: {report_path}")
    return report_path


def print_summary(log):
    """
    Prints a quick summary to the terminal showing
    how many files landed in each category.
    """
    summary = {}

    for entry in log:
        category = entry["category"]
        summary[category] = summary.get(category, 0) + 1

    print("\n========== SUMMARY ==========")
    for category, count in summary.items():
        print(f"  {category:<12} → {count} file(s)")
    print(f"  {'TOTAL':<12} → {len(log)} file(s)")
    print("==============================\n")


# ============================================================
# SECTION 4 — MAIN ENTRY POINT
# ============================================================

def main():
    print("========================================")
    print("       🗂️  FILE ORGANISER v1.0          ")
    print("========================================\n")

    # Ask the user which folder to organise
    folder_path = input("Enter the full path of the folder to organise:\n> ").strip()

    print(f"\n🔍 Scanning '{folder_path}'...\n")

    # Run the organiser and get the action log
    log = organise_folder(folder_path)

    if not log:
        print("⚠️  No files were moved. The folder may be empty or invalid.")
        return

    # Print summary and save the CSV report
    print_summary(log)

    report_folder = os.path.join(os.path.dirname(os.path.abspath(__file__)), "reports")
    generate_report(log, report_folder=report_folder)


if __name__ == "__main__":
    main()
