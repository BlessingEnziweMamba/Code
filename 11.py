# ============================================================
# Student Grade Tracker
# Description: A CLI app to track subjects, marks,
#              averages, and GPA using percentage-based grading
# ============================================================

import json
import os

# ---- Constants ----
DATA_FILE = "grades.json"
GRADE_SCALE = [
    (80, "A"),
    (70, "B"),
    (60, "C"),
    (50, "D"),
    (0,  "F"),
]
GPA_SCALE = [
    (80, 4.0),
    (70, 3.0),
    (60, 2.0),
    (50, 1.0),
    (0,  0.0),
]


# ============================================================
# FILE FUNCTIONS
# ============================================================

def load_data():
    # input: reads from JSON file if it exists
    if os.path.exists(DATA_FILE):
        file = open(DATA_FILE, "r")
        data = json.load(file)
        file.close()
        return data
    return {}


def save_data(data):
    # output: writes current data to JSON file
    file = open(DATA_FILE, "w")
    json.dump(data, file, indent=4)
    file.close()


# ============================================================
# GRADE HELPER FUNCTIONS
# ============================================================

def get_letter_grade(average):
    # processing: converts percentage to letter grade
    for threshold, letter in GRADE_SCALE:
        if average >= threshold:
            return letter
    return "F"


def get_gpa_points(average):
    # processing: converts percentage to GPA points
    for threshold, points in GPA_SCALE:
        if average >= threshold:
            return points
    return 0.0


def calculate_average(marks):
    # processing: calculates average from a list of marks
    if len(marks) == 0:
        return 0.0
    total = sum(marks)
    average = total / len(marks)
    return round(average, 2)


# ============================================================
# DISPLAY FUNCTIONS
# ============================================================

def print_divider():
    print("=" * 55)


def print_summary(data):
    # output: prints a full summary table of all subjects
    print_divider()
    print("          STUDENT GRADE TRACKER - SUMMARY")
    print_divider()

    if len(data) == 0:
        print("  No subjects found. Add a subject first.")
        print_divider()
        return

    total_gpa = 0.0
    subject_count = 0

    print("  {:<20} {:<10} {:<6} {:<5}".format("Subject", "Average", "Grade", "GPA"))
    print("-" * 55)

    for subject, marks in data.items():
        average = calculate_average(marks)
        letter = get_letter_grade(average)
        gpa = get_gpa_points(average)
        total_gpa = total_gpa + gpa
        subject_count = subject_count + 1
        print("  {:<20} {:<10} {:<6} {:<5}".format(subject, str(average) + "%", letter, str(gpa)))

    print("-" * 55)

    if subject_count > 0:
        overall_gpa = round(total_gpa / subject_count, 2)
        print("  Overall GPA:", overall_gpa, "out of 4.0")

    print_divider()


def print_subject_detail(subject, marks):
    # output: prints all marks for a single subject
    print_divider()
    print("  Subject:", subject)
    print_divider()

    if len(marks) == 0:
        print("  No marks recorded yet.")
    else:
        for i in range(len(marks)):
            print("  Assessment", i + 1, ":", str(marks[i]) + "%")

        average = calculate_average(marks)
        letter = get_letter_grade(average)
        gpa = get_gpa_points(average)
        print("-" * 55)
        print("  Average :", str(average) + "%")
        print("  Grade   :", letter)
        print("  GPA     :", gpa)

    print_divider()


# ============================================================
# CORE MENU FUNCTIONS
# ============================================================

def add_subject(data):
    # input: prompts user to add a new subject
    print_divider()
    subject_name = input("  Enter subject name: ").strip()

    if subject_name == "":
        print("  [!] Subject name cannot be empty.")
        return data

    if subject_name in data:
        print("  [!] Subject already exists.")
        return data

    # processing
    data[subject_name] = []
    save_data(data)

    # output
    print("  [+] Subject '", subject_name, "' added successfully.")
    return data


def remove_subject(data):
    # input: prompts user to remove a subject
    print_divider()
    subject_name = input("  Enter subject name to remove: ").strip()

    if subject_name not in data:
        print("  [!] Subject not found.")
        return data

    confirm = input("  Are you sure you want to remove '" + subject_name + "'? (yes/no): ").strip().lower()

    # processing
    if confirm == "yes":
        del data[subject_name]
        save_data(data)
        print("  [-] Subject removed.")
    else:
        print("  [x] Removal cancelled.")

    return data


def add_mark(data):
    # input: prompts user to add a mark to a subject
    print_divider()

    if len(data) == 0:
        print("  [!] No subjects available. Add a subject first.")
        return data

    print("  Available subjects:")
    for subject in data:
        print("    -", subject)

    subject_name = input("  Enter subject name: ").strip()

    if subject_name not in data:
        print("  [!] Subject not found.")
        return data

    mark_input = input("  Enter mark (0 - 100): ").strip()

    # processing: validate mark
    if not mark_input.replace(".", "").isdigit():
        print("  [!] Invalid mark. Please enter a number.")
        return data

    mark = float(mark_input)

    if mark < 0 or mark > 100:
        print("  [!] Mark must be between 0 and 100.")
        return data

    data[subject_name].append(mark)
    save_data(data)

    # output
    print("  [+] Mark of", str(mark) + "% added to", subject_name)
    return data


def view_subject(data):
    # input: prompts user to select a subject to view
    print_divider()

    if len(data) == 0:
        print("  [!] No subjects available.")
        return

    print("  Available subjects:")
    for subject in data:
        print("    -", subject)

    subject_name = input("  Enter subject name to view: ").strip()

    if subject_name not in data:
        print("  [!] Subject not found.")
        return

    # output
    print_subject_detail(subject_name, data[subject_name])


def clear_subject_marks(data):
    # input: prompts user to clear all marks for a subject
    print_divider()

    if len(data) == 0:
        print("  [!] No subjects available.")
        return data

    print("  Available subjects:")
    for subject in data:
        print("    -", subject)

    subject_name = input("  Enter subject name to clear marks: ").strip()

    if subject_name not in data:
        print("  [!] Subject not found.")
        return data

    confirm = input("  Clear all marks for '" + subject_name + "'? (yes/no): ").strip().lower()

    # processing
    if confirm == "yes":
        data[subject_name] = []
        save_data(data)
        print("  [~] All marks cleared for", subject_name)
    else:
        print("  [x] Action cancelled.")

    return data


# ============================================================
# MAIN MENU
# ============================================================

def print_menu():
    print_divider()
    print("          STUDENT GRADE TRACKER")
    print_divider()
    print("  1. View Summary (All Subjects)")
    print("  2. View Subject Detail")
    print("  3. Add Subject")
    print("  4. Remove Subject")
    print("  5. Add Mark to Subject")
    print("  6. Clear Marks for Subject")
    print("  0. Exit")
    print_divider()


def main():
    # input: load existing data
    data = load_data()

    print("\n  Welcome to the Student Grade Tracker!")

    running = True

    while running:
        print_menu()
        choice = input("  Enter your choice: ").strip()

        # processing: route to correct function
        if choice == "1":
            print_summary(data)

        elif choice == "2":
            view_subject(data)

        elif choice == "3":
            data = add_subject(data)

        elif choice == "4":
            data = remove_subject(data)

        elif choice == "5":
            data = add_mark(data)

        elif choice == "6":
            data = clear_subject_marks(data)

        elif choice == "0":
            print_divider()
            print("  Goodbye! Keep studying hard!")
            print_divider()
            running = False

        else:
            print("  [!] Invalid option. Please choose from the menu.")


# ============================================================
# ENTRY POINT
# ============================================================

main()