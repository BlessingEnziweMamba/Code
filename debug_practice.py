# ============================================
# DEBUG CHALLENGE - Find & Fix the Errors!
# Topics: if/else, for loops, while loops
# There are 6 bugs hidden in this code.
# ============================================


# ----------------------------
# SECTION 1: If / Else
# ----------------------------
# This checks if a student passed or failed

score = 45

if score >= 50
    print("You passed!")
elif score >= 30:
    print("You barely passed.")
else:
    print("You failed.")


# ----------------------------
# SECTION 2: For Loop
# ----------------------------
# This should print numbers 1 to 5 and their squares

for i in range(1, 6):
    square = i * i
    print(i, "squared is", Square)


# ----------------------------
# SECTION 3: While Loop
# ----------------------------
# This should count DOWN from 5 to 1

count = 5

while count > 0:
    print(count)
    count = count + 1  # Should be counting DOWN


# ----------------------------
# SECTION 4: If/Else inside a For Loop
# ----------------------------
# This should label each number as even or odd

numbers = [1, 2, 3, 4, 5]

for num in numbers:
    if num % 2 = 0:
        print(num, "is even")
    else:
        print(num, "is odd")


# ----------------------------
# SECTION 5: While Loop with break
# ----------------------------
# Keeps asking for a positive number, stops when user enters one

while True:
    number = int(input("Enter a positive number: "))
    if number > 0:
        print("Thanks! You entered:", number)
        Break
