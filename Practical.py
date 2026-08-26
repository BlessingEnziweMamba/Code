# Monthly Salary Calculator for Student Helper

# Get user input
hourly_rate = float(input("Enter your hourly rate: 15.00: "))
hours_per_week = float(input("Enter hours worked per week: 20.00: "))

# Define constants
weeks_per_month = 4
paye_rate = 0.15

# Calculate monthly gross salary
monthly_gross = hourly_rate * hours_per_week * weeks_per_month

# Apply PAYE at 15%
paye = monthly_gross * paye_rate

# Calculate monthly net salary
monthly_net = monthly_gross - paye

# Print neat summary
print("\n" + "=" * 45)
print("MONTHLY SALARY SUMMARY")
print("=" * 45)
print(f"Hourly Rate:              R{hourly_rate:.2f}")
print(f"Hours per Week:           {hours_per_week:.2f}")
print(f"Weeks per Month:          {weeks_per_month}")
print(f"Gross Monthly Salary:     R{monthly_gross:.2f}")
print(f"PAYE (15%):               R{paye:.2f}")
print(f"Net Monthly Salary:       R{monthly_net:.2f}")
print("=" * 45)

