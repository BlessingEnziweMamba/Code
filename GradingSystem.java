
import java.util.Scanner;

public class GradingSystem {

    public static String getGrade(double average) {
        if (average >= 90) {
            return "A+";
        } else if (average >= 80) {
            return "A";
        } else if (average >= 70) {
            return "B";
        } else if (average >= 60) {
            return "C";
        } else if (average >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("       STUDENT GRADING SYSTEM");
        System.out.println("========================================");

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("How many subjects? ");
        int numSubjects = scanner.nextInt();

        double[] marks = new double[numSubjects];
        double total = 0;

        for (int i = 1; i <= numSubjects; i++) {
            while (true) {
                System.out.printf("  Enter mark for subject %d: ", i);
                double mark = scanner.nextDouble();
                if (mark >= 0 && mark <= 100) {
                    marks[i - 1] = mark;
                    total += mark;
                    break;
                } else {
                    System.out.println("  Invalid! Please enter a mark between 0 and 100.");
                }
            }
        }

        double average = total / numSubjects;
        String grade = getGrade(average);

        System.out.println();
        System.out.println("========================================");
        System.out.println("           RESULTS");
        System.out.println("========================================");
        System.out.printf("  Student : %s%n", name);
        System.out.printf("  Subjects: %d%n", numSubjects);

        System.out.print("  Marks   : [");
        for (int i = 0; i < marks.length; i++) {
            System.out.printf("%.1f", marks[i]);
            if (i < marks.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        System.out.printf("  Average : %.2f%n", average);
        System.out.printf("  Grade   : %s%n", grade);
        System.out.println("========================================");

        if (grade.equals("F")) {
            System.out.println("  Status  : FAIL");
        } else {
            System.out.println("  Status  : PASS");
        }
        System.out.println("========================================");

        scanner.close();
    }
}
