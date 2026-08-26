#input
print("============ Login System ==============")
username = input("Enter username: ")
password = input("Enter password: ")

#processing and output
if username == "blessing":
    if password == "Blessing@2007":
        print("Access granted. Welcome Blessing!")
    elif password == "4979":
        print("Access granted. Password is very weak,please reset it.")
    else:
        print("Incorrect password. Please try again.")
elif username == "guest":
    if password == "Guest@2007":
        print("Access granted. Welcome Guest!")
    else:
        print("ERROR: Incorrect password. Guest access denied.")
else:
    print("ERROR: Account does not EXIST.")
