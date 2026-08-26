#processing
def calculate_average(mark1, mark2, mark3):
    average = (mark1 + mark2 + mark3) / 3
    return round(average, 2)

def get_grade(average):
    if average >= 75:
        return "distinction"
    elif average >= 60:
        return "merit"
    elif average >= 50:
        return "pass"
    else:
        return "fail"
    
 #output   
def display_report(name, mark1, mark2, mark3):
    average = calculate_average(mark1, mark2, mark3)
    grade = get_grade(average)
    print(" ==================================")
    print("Name:", name)
    print("Mark 1:", mark1)
    print("Mark 2:", mark2)
    print("Mark 3:", mark3)
    print("Average:", average)
    print("Grade:", grade)
    print(" ==================================")

#input
student_name = str(input("Enter student name: "))
mark1 = int(input("Enter mark 1: "))
mark2 = int(input("Enter mark 2: "))
mark3 = int(input("Enter mark 3: "))
average = calculate_average(mark1, mark2, mark3)

display_report(student_name, mark1, mark2, mark3)