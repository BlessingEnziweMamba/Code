def introduce():
    def first_name():
        first_name = str(input("Enter your name: "))
        return first_name
    def age():
        age = int(input("Enter your age: "))
        return age

    name = first_name()
    years = age()

    print("i'm", name, "and i'm", years, "years old.")

introduce()