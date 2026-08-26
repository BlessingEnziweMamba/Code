import math

def addition(a, b):
    return a + b

def multiplication(a, b):
    return a * b

def exponentation(a, b):
    return a ** b

def division(a, b):
    return a / b

def qoutient(a, b):
    return a // b

def subtraction(a, b):
    return a - b
           
def square_root(a, b):
    return a ** 1/2
    return b ** 1/2

while True:
    print("\nSelect an operation:")
    print("1 - Addition")
    print("2 - Subtraction")
    print("3 - Multiplication")
    print("4 - exponent")
    print("5 - Division")
    print("6 - square_root")
    print("7 - qoutient")
    print("8 - Quit")

    choice = input("Enter choice (1/2/3/4/5/6/7/8): ")

    if choice == "8":
        print("Thanks for using the calculator. Goodbye!")
        break
    
    num1 = int(input("Enter the first number: "))
    num2 = int(input("Enter the second number: "))

    if choice == "1":
        print("Result:", addition(num1, num2))
    elif choice == "2":
        print("Result:", subtraction(num1, num2))
    elif choice == "3":
        print("Result:", multiplication(num1, num2))
    elif choice == "4":
        print("Result:", exponentation(num1, num2))
    elif choice == "5":
        if num2 == 0:
            print("Error: Cannot divide by zero!")
        else:
            print("Result:", division(num1, num2))
    elif choice == "6":
        print("Result:", square_root(num1, num2))
    elif choice == "7":
        print("Result:", qoutient(num1, num2))
    else:
        print("Invalid choice. Please try again.")

print("==========================================================")