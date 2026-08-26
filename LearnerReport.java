package Blessing;

import java.util.Locale;
import java.util.Scanner;

public class LearnerReport {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		scanner.useLocale(Locale.US);

		// input
		System.out.println("====================== Learner REPORT ==============================");

		System.out.print("Enter your name: ");
		String name = scanner.nextLine();

		System.out.print("Enter your age: ");
		int age = scanner.nextInt();

		System.out.print("Enter mark 1: ");
		int mark1 = scanner.nextInt();

		System.out.print("Enter mark 2: ");
		int mark2 = scanner.nextInt();

		System.out.print("Enter mark 3: ");
		int mark3 = scanner.nextInt();

		System.out.println("=====================================================================");

		// processing
		if (age >= 20) {
			System.out.println("Excellent you are an adult now: " + age);
		} else if (age >= 15) {
			System.out.println("You are a teenager: " + age);
		} else {
			System.out.println("You are under age: " + age);
		}

		int learnerMarks = mark1 + mark2 + mark3;
		int totalMark = 300;
		double average = ((double) learnerMarks / 300) * 100;
		double gradeAve = (((double) learnerMarks / 300) * 100 * 458) / 1000;

		if (average >= 75) {
			System.out.println("Excellent score for the term: " + average);
		} else if (average >= 50) {
			System.out.println("You have passed but aim for distinction: " + average);
		} else {
			System.out.println("You have failed the term: " + average);
		}

		System.out.println("=====================================================================");

		String grade;
		if (average >= 75) {
			grade = "Distinction";
		} else if (average >= 50) {
			grade = "Pass";
		} else {
			grade = "Fail";
		}

		if (gradeAve >= 75) {
			System.out.println("Grade is passed with exception: " + gradeAve);
		} else if (gradeAve >= 50) {
			System.out.println("Grade is average: " + gradeAve);
		} else {
			System.out.println("Grade is failed: " + gradeAve);
		}

		System.out.println("=====================================================================");

		// output
		System.out.println("name: " + name);
		System.out.println("age: " + age);
		System.out.println("total learner marks: " + learnerMarks);
		System.out.println("total marks: " + totalMark);
		System.out.println("average: " + Math.round(average));
		System.out.println("grade: " + grade);
		System.out.println("grade_ave: " + Math.round(gradeAve));

		System.out.println("=====================================================================");

		scanner.close();
	}

}
