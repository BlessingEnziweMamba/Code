import java.util.Scanner;

/**
 * Advanced Calculator
 * -------------------
 * Supports basic arithmetic, scientific functions (trig, log, power, roots),
 * numeric integration (definite integrals via Simpson's Rule), and a simple
 * ASCII graph plotter that mimics what a function's curve looks like.
 */
public class Calculator {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                System.out.print("Choose an option: ");
                String choice = scanner.next();

                switch (choice) {
                    case "1" -> basicArithmetic(scanner);
                    case "2" -> scientificFunctions(scanner);
                    case "3" -> definiteIntegral(scanner);
                    case "4" -> graphFunction(scanner);
                    case "5" -> running = false;
                    default -> System.out.println("Invalid option. Try again.\n");
                }
            }

            System.out.println("Goodbye!");
        }
    }

    private static void printMenu() {
        System.out.println("=============================================");
        System.out.println(" ADVANCED CALCULATOR");
        System.out.println("=============================================");
        System.out.println("1. Basic arithmetic (+, -, *, /)");
        System.out.println("2. Scientific functions (sin, cos, tan, log, sqrt, pow)");
        System.out.println("3. Definite integral (Simpson's Rule)");
        System.out.println("4. Graph a function (ASCII plot)");
        System.out.println("5. Exit");
    }

    // ---------------------------------------------------------------
    // 1. Basic arithmetic
    // ---------------------------------------------------------------
    private static void basicArithmetic(Scanner scanner) {
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();

        System.out.print("Enter an operator (+, -, *, /): ");
        String operator = scanner.next();

        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        double result;

        switch (operator) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "/" -> {
                if (num2 == 0) {
                    System.out.println("Error: Cannot divide by zero.\n");
                    return;
                }
                result = num1 / num2;
            }
            default -> {
                System.out.println("Error: Invalid operator.\n");
                return;
            }
        }

        System.out.println(num1 + " " + operator + " " + num2 + " = " + result + "\n");
    }

    // ---------------------------------------------------------------
    // 2. Scientific functions
    // ---------------------------------------------------------------
    private static void scientificFunctions(Scanner scanner) {
        System.out.println("\nAvailable functions: sin, cos, tan, ln, log10, sqrt, pow, exp");
        System.out.print("Choose a function: ");
        String func = scanner.next().toLowerCase();

        double result;

        switch (func) {
            case "sin", "cos", "tan" -> {
                System.out.print("Enter angle in degrees: ");
                double degrees = scanner.nextDouble();
                double radians = Math.toRadians(degrees);
                result = switch (func) {
                    case "sin" -> Math.sin(radians);
                    case "cos" -> Math.cos(radians);
                    default -> Math.tan(radians);
                };
            }
            case "ln" -> {
                System.out.print("Enter a number: ");
                double x = scanner.nextDouble();
                if (x <= 0) {
                    System.out.println("Error: ln is undefined for x <= 0.\n");
                    return;
                }
                result = Math.log(x);
            }
            case "log10" -> {
                System.out.print("Enter a number: ");
                double x = scanner.nextDouble();
                if (x <= 0) {
                    System.out.println("Error: log10 is undefined for x <= 0.\n");
                    return;
                }
                result = Math.log10(x);
            }
            case "sqrt" -> {
                System.out.print("Enter a number: ");
                double x = scanner.nextDouble();
                if (x < 0) {
                    System.out.println("Error: sqrt is undefined for negative numbers.\n");
                    return;
                }
                result = Math.sqrt(x);
            }
            case "pow" -> {
                System.out.print("Enter base: ");
                double base = scanner.nextDouble();
                System.out.print("Enter exponent: ");
                double exponent = scanner.nextDouble();
                result = Math.pow(base, exponent);
            }
            case "exp" -> {
                System.out.print("Enter exponent (e^x): ");
                double x = scanner.nextDouble();
                result = Math.exp(x);
            }
            default -> {
                System.out.println("Error: Unknown function.\n");
                return;
            }
        }

        System.out.println("Result: " + result + "\n");
    }

    // ---------------------------------------------------------------
    // 3. Definite integral via Simpson's Rule
    // ---------------------------------------------------------------
    private static void definiteIntegral(Scanner scanner) {
        System.out.println("\nAvailable functions: sin, cos, x^2, sqrt, 1/x, ln");
        System.out.print("Choose a function to integrate: ");
        String func = scanner.next().toLowerCase();

        System.out.print("Enter lower bound (a): ");
        double a = scanner.nextDouble();
        System.out.print("Enter upper bound (b): ");
        double b = scanner.nextDouble();

        if (a >= b) {
            System.out.println("Error: lower bound must be less than upper bound.\n");
            return;
        }

        // 1/x and ln need to avoid zero in the interval
        if ((func.equals("1/x") || func.equals("ln")) && a <= 0) {
            System.out.println("Error: this function is undefined for x <= 0 in the interval.\n");
            return;
        }

        int n = 1000; // number of subintervals (must be even for Simpson's Rule)
        double integral = simpsonsRule(func, a, b, n);

        System.out.printf("Integral of %s from %.4f to %.4f ≈ %.6f%n%n", func, a, b, integral);
    }

    /**
     * Approximates the definite integral of f from a to b using Simpson's Rule.
     * n must be even; larger n gives a more accurate approximation.
     */
    private static double simpsonsRule(String func, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = evaluate(func, a) + evaluate(func, b);

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += (i % 2 == 0 ? 2 : 4) * evaluate(func, x);
        }

        return (h / 3) * sum;
    }

    /**
     * Evaluates a named function at x. Shared by the integrator and the grapher
     * so both features stay consistent.
     */
    private static double evaluate(String func, double x) {
        return switch (func) {
            case "sin" -> Math.sin(x);
            case "cos" -> Math.cos(x);
            case "tan" -> Math.tan(x);
            case "x^2" -> x * x;
            case "x^3" -> x * x * x;
            case "sqrt" -> x < 0 ? Double.NaN : Math.sqrt(x);
            case "1/x" -> x == 0 ? Double.NaN : 1 / x;
            case "ln" -> x <= 0 ? Double.NaN : Math.log(x);
            case "exp" -> Math.exp(x);
            default -> Double.NaN;
        };
    }

    // ---------------------------------------------------------------
    // 4. ASCII graph of a function
    // ---------------------------------------------------------------
    private static void graphFunction(Scanner scanner) {
        System.out.println("\nAvailable functions: sin, cos, tan, x^2, x^3, sqrt, 1/x, ln, exp");
        System.out.print("Choose a function to graph: ");
        String func = scanner.next().toLowerCase();

        System.out.print("Enter minimum x: ");
        double xMin = scanner.nextDouble();
        System.out.print("Enter maximum x: ");
        double xMax = scanner.nextDouble();

        if (xMin >= xMax) {
            System.out.println("Error: minimum x must be less than maximum x.\n");
            return;
        }

        int width = 61;   // number of x samples (columns)
        int height = 21;  // number of y rows

        double[] xs = new double[width];
        double[] ys = new double[width];
        double yMin = Double.POSITIVE_INFINITY;
        double yMax = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < width; i++) {
            double x = xMin + i * (xMax - xMin) / (width - 1);
            double y = evaluate(func, x);
            xs[i] = x;
            ys[i] = y;
            if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                yMin = Math.min(yMin, y);
                yMax = Math.max(yMax, y);
            }
        }

        if (yMin == Double.POSITIVE_INFINITY) {
            System.out.println("Error: function has no valid values in this range.\n");
            return;
        }

        // Add a little padding so the curve doesn't hug the edges
        double padding = (yMax - yMin) * 0.1;
        if (padding == 0) padding = 1;
        yMin -= padding;
        yMax += padding;

        char[][] grid = new char[height][width];
        for (char[] row : grid) {
            java.util.Arrays.fill(row, ' ');
        }

        // Plot the curve
        for (int i = 0; i < width; i++) {
            if (Double.isNaN(ys[i]) || Double.isInfinite(ys[i])) continue;
            int row = (int) Math.round((yMax - ys[i]) / (yMax - yMin) * (height - 1));
            row = Math.max(0, Math.min(height - 1, row));
            grid[row][i] = '*';
        }

        // Draw the x-axis (y = 0) if it's within range, using '-'
        if (yMin <= 0 && yMax >= 0) {
            int axisRow = (int) Math.round((yMax - 0) / (yMax - yMin) * (height - 1));
            for (int i = 0; i < width; i++) {
                if (grid[axisRow][i] == ' ') grid[axisRow][i] = '-';
            }
        }

        System.out.println();
        System.out.printf("f(x) = %s   [%.2f, %.2f]%n", func, xMin, xMax);
        System.out.printf("y-range: [%.2f, %.2f]%n", yMin, yMax);
        System.out.println();

        for (int r = 0; r < height; r++) {
            System.out.print(String.format("%7.2f | ", yMax - r * (yMax - yMin) / (height - 1)));
            System.out.println(new String(grid[r]));
        }

        System.out.print("        +-");
        System.out.println("-".repeat(width));
        System.out.printf("          %.2f%s%.2f%n", xMin,
                " ".repeat(Math.max(1, width - 10)), xMax);
        System.out.println();
    }
}
