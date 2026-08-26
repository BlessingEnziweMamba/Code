package Blessing;

import java.util.Scanner;
import java.util.Locale;

public class interest {

	public static void main(String[] args) {

		double principal = 0;
		double rate = 0;
		double interest = 0;

		Scanner scanner = new Scanner(System.in);
		scanner.useLocale(Locale.US);

		System.out.print("Enter the current principal: ");
		principal = scanner.nextDouble();

		System.out.print("Enter the current rate: ");
		rate = scanner.nextDouble();

		interest = principal * rate;
		principal = principal + interest;

		System.out.println("The rate is: " + rate + "%");
		System.out.println("The interest is: " + "R" + interest );
		System.out.println("The FUTURE principal is: " + "R" + principal);

		scanner.close();

	}

}
