""""""

#PYTHON CALCULATOR

""""""
#...
import math
def add(x, y):
    return x + y

def subtract(x, y):
    return x - y

def multiply(x, y):
    return x * y

def divide(x, y):
    if y == 0:
        return "Error Division by zero"
    return x / y

def pwer(x, y):
    return(x ** y)

def modulus(x, y):
    if y == 0:
        return "Error Division by zero"
    return(x % y)

def square_root(x):
    if x < 0:
        return "Error Square root of negative number"
    return math.sqrt(x)

def logarithm(x, base):
    if x <= 0:
        return "Error Logarithm of non-positive number"
    if base <= 1:
        return "Error Logarithm base must be greater than 1"
    return math.log(x, base)

def display_menu():
    print("\n" + "=" * 40)
    print("PYHTON CALCULATOR")
    print("=" * 40)
    print("1. Addition                          ( + )")
    print("2. Subtraction                       ( - )")
    print("3. Multiplication                    ( * )")
    print("4. Division                          ( / )")
    print("5. Power                             ( ** )")
    print("6. Modulus                           ( % )")
    print("7. Square Root                       ( sqrt() )")
    print("8. Logarithm                         ( log() )")
    print("9. Exit                              ")
    print("=" * 40)
    
    def diplay_histroy(history):
        print("\n--- Calculation H istory ---")
        if not history:
            print("No history available.")
        else:
            print("\nCalculation History:")
            for entry in history:
                print(entry)
        print("-----------------------------")

def get_number(prompt):
    while True:
        try:
            return float(input(prompt))
        except ValueError:
            print("Invalid input. Please enter a number.")
        
def format_result(result):
    if result == int(result):
        return int(result)
    return round(result, 6)

def main():
    history = []

    print("\nWelcome to the Python Calculator!")

    while True:
        display_menu()
        choice = input("  Enter your choice (0-9): ").strip()

        if choice == "0":
            print("\nThanks for using the calculator. Goodbye!\n")
            break

        elif choice == "8":
            display_history(history)
            continue

        elif choice == "9":
            history.clear()
            print("  ✓ History cleared.")
            continue

        elif choice == "7":
            a = get_number("  Enter a number: ")
            try:
                result = square_root(a)
                formatted = format_result(result)
                entry = f"√{format_result(a)} = {formatted}"
                print(f"\n  Result: {entry}")
                history.append(entry)
            except ValueError as e:
                print(f"  ⚠ Error: {e}")

        elif choice in ("1", "2", "3", "4", "5", "6", "7", "8", "9"):
            a = get_number("  Enter first number:  ")
            b = get_number("  Enter second number: ")

            operations = {
                "1": ("+",  add),
                "2": ("-",  subtract),
                "3": ("*",  multiply),
                "4": ("/",  divide),
                "5": ("**", power),
                "6": ("%",  modulus),
                "7": ("sqrt", square_root),
                "8": ("log", logarithm)
            }

            symbol, func = operations[choice]

            try:
                result = func(a, b)
                formatted_result = format_result(result)
                formatted_a = format_result(a)
                formatted_b = format_result(b)
                entry = f"{formatted_a} {symbol} {formatted_b} = {formatted_result}"
                print(f"\n  Result: {entry}")
                history.append(entry)
            except (ZeroDivisionError, ValueError) as e:
                print(f"  ⚠ Error: {e}")

        else:
            print("  ⚠ Invalid choice. Please enter a number from 0 to 9.")

        again = input("\n  Do another calculation? (y/n): ").strip().lower()
        if again != "y":
            print("\nThanks for using the calculator. Goodbye!\n")
            break
        