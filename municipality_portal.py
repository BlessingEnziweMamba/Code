# ============================================================
#   MUNICIPALITY GENERAL INFO & SERVICES PORTAL
#   For Civilians — Python Console Application
# ============================================================

import datetime

# ─────────────────────────────────────────────────────────────
#  DATA — Services, contacts, FAQs
# ─────────────────────────────────────────────────────────────

SERVICES = {
    "1": {
        "name": "Water & Sanitation",
        "description": "Report leaks, request new connections, check water quality.",
        "contact": "0800-123-001",
        "hours": "Mon–Fri, 07:00–16:00",
        "steps": [
            "Visit the municipal offices or call the helpline.",
            "Bring your ID and proof of residence.",
            "Fill in the Service Request Form (Form MW-01).",
            "Receive a reference number for tracking.",
        ],
    },
    "2": {
        "name": "Electricity & Loadshedding",
        "description": "Report outages, apply for connections, get loadshedding schedules.",
        "contact": "0800-123-002",
        "hours": "24/7 for emergencies; Mon–Fri 07:00–16:00 for applications",
        "steps": [
            "Call the 24/7 outage hotline for emergencies.",
            "For new connections, bring ID, proof of address, and title deed.",
            "Complete Form EL-02 at the municipal office.",
            "An inspector will be scheduled within 5 working days.",
        ],
    },
    "3": {
        "name": "Refuse & Waste Collection",
        "description": "Collection schedules, report missed pickups, bulk waste disposal.",
        "contact": "0800-123-003",
        "hours": "Mon–Fri, 08:00–15:00",
        "steps": [
            "Check your area's collection day on the notice board or website.",
            "Place bins outside by 06:00 on collection day.",
            "For missed collections, call within 24 hours with your address.",
            "Bulk waste: book a slot at least 3 days in advance.",
        ],
    },
    "4": {
        "name": "Roads & Stormwater",
        "description": "Report potholes, road damage, blocked stormwater drains.",
        "contact": "0800-123-004",
        "hours": "Mon–Fri, 07:30–16:30",
        "steps": [
            "Note the exact street name and nearest landmark.",
            "Call the hotline or complete Form RD-04 online.",
            "Provide photos if possible to speed up assessment.",
            "Repairs are scheduled within 10 working days for non-emergencies.",
        ],
    },
    "5": {
        "name": "Housing & Land Affairs",
        "description": "Apply for RDP housing, title deeds, and land enquiries.",
        "contact": "0800-123-005",
        "hours": "Mon–Fri, 08:00–15:00",
        "steps": [
            "Collect Form HL-05 from the Housing Department.",
            "Submit with: ID, proof of income, proof of residence (3 months).",
            "Application is reviewed within 30 working days.",
            "You will be contacted by post or phone with the outcome.",
        ],
    },
    "6": {
        "name": "Health & Clinics",
        "description": "Clinic locations, operating hours, health campaigns.",
        "contact": "0800-123-006",
        "hours": "Mon–Sat, 07:00–19:00",
        "steps": [
            "Bring your clinic card or ID for all visits.",
            "Arrive early — queues are processed in order of arrival.",
            "For immunisation campaigns, check the community notice board.",
            "Referrals to hospitals are handled by the attending nurse/doctor.",
        ],
    },
}

FAQS = [
    ("How do I pay my municipal bill?",
     "You can pay at any municipal cashier, via EFT using your account number, "
     "or at selected banks and ATMs. Reference your municipal account number on all payments."),
    ("What do I do in a water/electricity emergency after hours?",
     "Call our 24/7 emergency line: 0800-999-999. Keep your stand number or "
     "account number ready when you call."),
    ("How do I report a streetlight that is out?",
     "Call 0800-123-002 or complete the online fault report form. "
     "Note the pole number (printed on the pole) to help us locate it faster."),
    ("Where is the main municipal office?",
     "123 Civic Drive, Town Centre. Open Mon–Fri, 07:30–16:00. "
     "Closed on public holidays."),
    ("How do I get an indigent (free basic services) subsidy?",
     "Visit the Revenue Department with your ID and proof of income. "
     "Households earning below R3 500/month may qualify for free basic water, "
     "electricity, and refuse removal."),
]

EMERGENCY_CONTACTS = {
    "General Emergency":    "10111",
    "Ambulance / Medical":  "10177",
    "Fire Department":      "0800-333-111",
    "Municipal Emergency":  "0800-999-999",
    "SAPS":                 "10111",
    "Eskom (national grid)":"0800-111-722",
}


# ─────────────────────────────────────────────────────────────
#  HELPERS
# ─────────────────────────────────────────────────────────────

def divider(char="─", width=60):
    print(char * width)

def header(title):
    divider("═")
    print(f"  {title}")
    divider("═")

def pause():
    input("\n  Press ENTER to return to the main menu...")

def get_current_time():
    return datetime.datetime.now().strftime("%A, %d %B %Y  |  %H:%M")


# ─────────────────────────────────────────────────────────────
#  MENU SCREENS
# ─────────────────────────────────────────────────────────────

def display_main_menu():
    header("🏛  MUNICIPALITY GENERAL INFO & SERVICES PORTAL")
    print(f"  {get_current_time()}\n")
    print("  Welcome, Resident! How can we help you today?\n")
    print("  [1]  Browse Municipal Services")
    print("  [2]  Frequently Asked Questions (FAQs)")
    print("  [3]  Emergency Contacts")
    print("  [4]  About This Portal")
    print("  [0]  Exit")
    divider()
    return input("  Enter your choice: ").strip()


def browse_services():
    while True:
        header("📋  MUNICIPAL SERVICES")
        for key, svc in SERVICES.items():
            print(f"  [{key}]  {svc['name']}")
        print("  [0]  Back to Main Menu")
        divider()
        choice = input("  Select a service to learn more: ").strip()

        if choice == "0":
            break
        elif choice in SERVICES:
            display_service_detail(SERVICES[choice])
        else:
            print("\n  ⚠  Invalid choice. Please try again.")
            pause()


def display_service_detail(svc):
    header(f"🔧  {svc['name'].upper()}")
    print(f"  Description : {svc['description']}")
    print(f"  Contact     : {svc['contact']}")
    print(f"  Office Hours: {svc['hours']}\n")
    print("  HOW TO ACCESS THIS SERVICE:")
    for i, step in enumerate(svc["steps"], 1):
        print(f"    Step {i}: {step}")
    pause()


def display_faqs():
    header("❓  FREQUENTLY ASKED QUESTIONS")
    for i, (question, answer) in enumerate(FAQS, 1):
        print(f"\n  Q{i}: {question}")
        print(f"      {answer}")
    pause()


def display_emergency_contacts():
    header("🚨  EMERGENCY CONTACTS")
    print("  In case of emergency, contact:\n")
    for service, number in EMERGENCY_CONTACTS.items():
        print(f"  {service:<25} {number}")
    print("\n  ⚠  These lines are available 24 hours a day, 7 days a week.")
    pause()


def display_about():
    header("ℹ  ABOUT THIS PORTAL")
    print("  This portal provides residents with quick access to:")
    print("  • Municipal service information and how-to guides")
    print("  • Contact numbers for all departments")
    print("  • Emergency hotlines")
    print("  • Frequently asked questions\n")
    print("  For online services, visit: www.municipality.gov.za")
    print("  Email general enquiries to: info@municipality.gov.za\n")
    print("  This system is maintained by the ICT Department.")
    print("  Last updated: January 2025")
    pause()


# ─────────────────────────────────────────────────────────────
#  MAIN — Entry Point & Main Loop
# ─────────────────────────────────────────────────────────────

def main():
    """
    ALGORITHM (summary):
    1. START
    2. Display welcome screen / main menu
    3. Read civilian's choice
    4. IF choice == 1 → Browse Services sub-menu
         a. Show list of services
         b. Civilian selects a service
         c. Display service details (description, contact, steps)
         d. Return to services list or main menu
    5. ELSE IF choice == 2 → Display FAQs
    6. ELSE IF choice == 3 → Display Emergency Contacts
    7. ELSE IF choice == 4 → Display About
    8. ELSE IF choice == 0 → Exit with farewell message
    9. ELSE → Show error, repeat menu
    10. GOTO step 2 (loop until exit)
    11. END
    """

    print("\n" + "=" * 60)
    print("   Loading Municipality Portal...")
    print("=" * 60)

    while True:
        choice = display_main_menu()

        if choice == "1":
            browse_services()
        elif choice == "2":
            display_faqs()
        elif choice == "3":
            display_emergency_contacts()
        elif choice == "4":
            display_about()
        elif choice == "0":
            divider("═")
            print("  Thank you for using the Municipality Portal.")
            print("  Stay safe and have a great day! ")
            divider("═")
            break
        else:
            print("\n  ⚠  Invalid choice. Please enter a number from the menu.")
            pause()


if __name__ == "__main__":
    main()