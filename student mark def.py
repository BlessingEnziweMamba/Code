#processing
#at the beginning of the porgram we define the local functions that will be used in the program
def get_grade(mark):
    if mark >= 75:
        print("distinction:", mark)
    elif mark >= 60:
        print("merit:", mark)
    elif mark >= 50:
        print("pass:", mark)
    else:
        print("fail:", mark)
    return mark

def check_pass(mark):
    if mark >= 50:
        print("pass:", mark)
    else:
        print("fail:", mark)
    return mark

#output
#at end of the program we call the main function to execute the program.
def display_result(name, mark):
    grade = get_grade(mark)
    status = check_pass(mark)
    print(" ==================================")
    print("Name:", name)
    print("Mark:", mark)
    print("Grade:", grade)
    print("Status:", status)
    print(" ==================================")

#input
student_name = str(input("Enter student name: "))
student_mark = int(input("Enter student mark: "))
 
display_result(student_name, student_mark)