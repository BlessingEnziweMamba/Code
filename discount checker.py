def check_price(price):
    if price >= 500:
       print(price)
    elif price >= 250 and price <= 500:
        print(price)
    elif price >= 100 and price <= 250: 
        print(price)
    else:
        print(price)
    return price
    
def check_discount(price):
    if price >= 500:
       print(price * 0.50)
    elif price >= 250 and price <= 500:
        print(price * 0.30)
    elif price >= 100 and price <= 250: 
        print(price * 0.15)
    else:
        print(price) 
    return price

def display_result(price, discount):
    get_price = check_price(price)
    get_discount = check_discount(price)
    print("price:", get_price )
    print("discount:", get_discount)

price = int(input("Enter the price of the customer: "))
disc_perc = float(input("Enter the percentage: "))
total = price * disc_perc

display_result(price, disc_perc)