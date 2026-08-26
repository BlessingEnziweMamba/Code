def add(a, b):
    return a + b

def subtract(a, b):
    return a - b

def multiply(a, b):
    return a * b
 
def exponent(a, b):
    return a ** b

def divide(a, b):
    return a / b

def square_root(a, b):
    return a ** 1/2
    return b ** 1/2

print("===== Python Calculator =====")

while True:
    print("\nSelect an operation:")
    print("1 - Addition")
    print("2 - Subtraction")
    print("3 - Multiplication")
    print("4 - exponent")
    print("5 - Division")
    print("6 - square_root")
    print("7 - Quit")

    choice = input("Enter choice (1/2/3/4/5/6/7): ")

    if choice == "7":
        print("Thanks for using the calculator. Goodbye!")
        break
    
    num1 = int(input("Enter the first number: "))
    num2 = int(input("Enter the second number: "))

    if choice == "1":
        print("Result:", add(num1, num2))
    elif choice == "2":
        print("Result:", subtract(num1, num2))
    elif choice == "3":
        print("Result:", multiply(num1, num2))
    elif choice == "4":
        print("Result:", exponent(num1, num2))
    elif choice == "5":
        if num2 == 0:
            print("Error: Cannot divide by zero!")
        else:
            print("Result:", divide(num1, num2))
    elif choice == "6":
        print("Result:", square_root(num1, num2))
    else:
        print("Invalid choice. Please try again.")

print("==========================================================")
