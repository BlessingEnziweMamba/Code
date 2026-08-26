package Blessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * QUIZ ENGINE - single-file version
 * Combines Question, QuestionBank, Quiz, and QuizApp into one file.
 * Run this file directly (it contains the main method).
 */
public class QuizApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        QuestionBank bank = new QuestionBank();

        System.out.println("=================================");
        System.out.println("     WELCOME TO THE QUIZ ENGINE   ");
        System.out.println("=================================");

        boolean playAgain = true;
        while (playAgain) {
            String category = chooseCategory(scanner, bank);
            String difficulty = chooseDifficulty(scanner);

            List<Question> filtered = filterQuestions(bank, category, difficulty);

            if (filtered.isEmpty()) {
                System.out.println("\nNo questions match that combination. Try again.\n");
                continue;
            }

            Quiz quiz = new Quiz(filtered);
            quiz.start(scanner);

            if (quiz.hasWrongAnswers()) {
                System.out.print("\nWould you like to review your wrong answers? (Y/N): ");
                String reviewChoice = scanner.nextLine().trim().toUpperCase();
                if (reviewChoice.equals("Y")) {
                    quiz.reviewWrongAnswers();
                }
            }

            System.out.print("\nPlay again? (Y/N): ");
            String again = scanner.nextLine().trim().toUpperCase();
            playAgain = again.equals("Y");
        }

        System.out.println("\nThanks for playing. Goodbye!");
        scanner.close();
    }

    private static String chooseCategory(Scanner scanner, QuestionBank bank) {
        List<String> categories = bank.getCategories();
        System.out.println("\nChoose a category:");
        System.out.println("0) All");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ") " + categories.get(i));
        }

        while (true) {
            System.out.print("Enter number: ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 0) {
                    return "All";
                } else if (choice >= 1 && choice <= categories.size()) {
                    return categories.get(choice - 1);
                }
            } catch (NumberFormatException e) {
                // fall through to error message
            }
            System.out.println("Invalid selection. Try again.");
        }
    }

    private static String chooseDifficulty(Scanner scanner) {
        System.out.println("\nChoose a difficulty:");
        System.out.println("0) All");
        System.out.println("1) Easy");
        System.out.println("2) Medium");
        System.out.println("3) Hard");

        while (true) {
            System.out.print("Enter number: ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "0": return "All";
                case "1": return "Easy";
                case "2": return "Medium";
                case "3": return "Hard";
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
    }

    private static List<Question> filterQuestions(QuestionBank bank, String category, String difficulty) {
        List<Question> byCategory = bank.getByCategory(category);
        if (difficulty.equalsIgnoreCase("All")) {
            return byCategory;
        }
        byCategory.removeIf(q -> !q.getDifficulty().equalsIgnoreCase(difficulty));
        return byCategory;
    }
}

/**
 * Represents a single multiple-choice quiz question.
 */
class Question {

    private String questionText;
    private String[] options;      // exactly 4 options
    private int correctIndex;      // 0 = A, 1 = B, 2 = C, 3 = D
    private String category;
    private String difficulty;     // "Easy", "Medium", "Hard"

    public Question(String questionText, String[] options, int correctIndex,
                    String category, String difficulty) {
        this.questionText = questionText;
        this.options = options;
        this.correctIndex = correctIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public String getCorrectAnswerText() {
        return options[correctIndex];
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isCorrect(int chosenIndex) {
        return chosenIndex == correctIndex;
    }

    public void display(int questionNumber) {
        System.out.println("\nQ" + questionNumber + " [" + category + " - " + difficulty + "]");
        System.out.println(questionText);
        char letter = 'A';
        for (String option : options) {
            System.out.println("   " + letter + ") " + option);
            letter++;
        }
    }
}

/**
 * Holds the hardcoded set of quiz questions and provides
 * methods to filter them by category or difficulty.
 */
class QuestionBank {

    private List<Question> allQuestions;

    public QuestionBank() {
        allQuestions = new ArrayList<>();
        loadQuestions();
    }

    private void loadQuestions() {

        // ----- JAVA CATEGORY -----
        allQuestions.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "super"},
                1, "Java", "Easy"));

        allQuestions.add(new Question(
                "What is the default value of a boolean instance variable?",
                new String[]{"true", "false", "0", "null"},
                1, "Java", "Easy"));

        allQuestions.add(new Question(
                "Which of these is NOT a valid access modifier in Java?",
                new String[]{"public", "private", "protected", "internal"},
                3, "Java", "Medium"));

        allQuestions.add(new Question(
                "What does the 'static' keyword mean when applied to a method?",
                new String[]{
                        "It belongs to the instance, not the class",
                        "It belongs to the class, not any instance",
                        "It can only be called once",
                        "It cannot be overridden"},
                1, "Java", "Medium"));

        allQuestions.add(new Question(
                "Which data structure does Java's ArrayDeque implement efficiently as both a stack and a queue?",
                new String[]{"Linked list only", "Double-ended queue", "Binary tree", "Hash map"},
                1, "Java", "Hard"));

        // ----- MATH CATEGORY -----
        allQuestions.add(new Question(
                "What is the value of 7 mod 3?",
                new String[]{"0", "1", "2", "3"},
                2, "Math", "Easy"));

        allQuestions.add(new Question(
                "If a number is even, what can be said about its square?",
                new String[]{"It is odd", "It is even", "It is prime", "It is negative"},
                1, "Math", "Easy"));

        allQuestions.add(new Question(
                "What is the derivative of x^3 with respect to x?",
                new String[]{"3x", "x^2", "3x^2", "3x^3"},
                2, "Math", "Medium"));

        allQuestions.add(new Question(
                "In discrete math, what is the contrapositive of 'If P then Q'?",
                new String[]{
                        "If Q then P",
                        "If not P then not Q",
                        "If not Q then not P",
                        "If P then not Q"},
                2, "Math", "Hard"));

        // ----- STATISTICS CATEGORY -----
        allQuestions.add(new Question(
                "What does PMF stand for in probability?",
                new String[]{
                        "Probability Mass Function",
                        "Probable Mean Frequency",
                        "Population Mean Function",
                        "Probability Median Formula"},
                0, "Statistics", "Easy"));

        allQuestions.add(new Question(
                "For a discrete uniform distribution over {1,...,n}, what is the expected value?",
                new String[]{"n/2", "(n+1)/2", "n", "(n-1)/2"},
                1, "Statistics", "Medium"));

        allQuestions.add(new Question(
                "What does a 95% confidence interval mean?",
                new String[]{
                        "95% of the data falls in this range",
                        "There is a 95% chance the parameter is in this specific interval",
                        "95% of such intervals, over repeated sampling, would contain the true parameter",
                        "The sample mean equals the population mean 95% of the time"},
                2, "Statistics", "Hard"));

        // ----- TRADING CATEGORY -----
        allQuestions.add(new Question(
                "In forex trading, what does 'ATR' commonly measure?",
                new String[]{"Trend direction", "Market volatility", "Trading volume", "Interest rate"},
                1, "Trading", "Easy"));

        allQuestions.add(new Question(
                "What is a 'pip' in forex trading?",
                new String[]{
                        "A type of trading strategy",
                        "The smallest standardized price movement in a currency pair",
                        "A broker fee",
                        "A candlestick pattern"},
                1, "Trading", "Medium"));
    }

    public List<Question> getAllQuestions() {
        return allQuestions;
    }

    public List<Question> getByCategory(String category) {
        if (category.equalsIgnoreCase("All")) {
            return new ArrayList<>(allQuestions);
        }
        List<Question> result = new ArrayList<>();
        for (Question q : allQuestions) {
            if (q.getCategory().equalsIgnoreCase(category)) {
                result.add(q);
            }
        }
        return result;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        for (Question q : allQuestions) {
            if (!categories.contains(q.getCategory())) {
                categories.add(q.getCategory());
            }
        }
        return categories;
    }
}

/**
 * Runs a quiz session: asks questions one by one, records the score,
 * and keeps track of questions answered incorrectly for later review.
 */
class Quiz {

    private List<Question> questions;
    private int score;
    private List<Question> wrongQuestions;
    private List<Integer> wrongUserAnswers;

    public Quiz(List<Question> questions) {
        this.questions = new ArrayList<>(questions);
        Collections.shuffle(this.questions);
        this.score = 0;
        this.wrongQuestions = new ArrayList<>();
        this.wrongUserAnswers = new ArrayList<>();
    }

    public void start(Scanner scanner) {
        score = 0;
        wrongQuestions.clear();
        wrongUserAnswers.clear();

        int questionNumber = 1;
        for (Question q : questions) {
            q.display(questionNumber);
            int choice = readValidChoice(scanner, q.getOptions().length);

            if (q.isCorrect(choice)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer was: "
                        + q.getCorrectAnswerText());
                wrongQuestions.add(q);
                wrongUserAnswers.add(choice);
            }
            questionNumber++;
        }

        showResults();
    }

    private int readValidChoice(Scanner scanner, int numOptions) {
        while (true) {
            System.out.print("Your answer (A-" + (char) ('A' + numOptions - 1) + "): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1) {
                int index = input.charAt(0) - 'A';
                if (index >= 0 && index < numOptions) {
                    return index;
                }
            }
            System.out.println("Invalid choice. Please enter a letter between A and "
                    + (char) ('A' + numOptions - 1) + ".");
        }
    }

    private void showResults() {
        System.out.println("\n===== QUIZ COMPLETE =====");
        System.out.println("Score: " + score + " / " + questions.size());
        double percentage = (questions.size() == 0) ? 0 : (100.0 * score / questions.size());
        System.out.printf("Percentage: %.1f%%%n", percentage);
        System.out.println(getGrade(percentage));
    }

    private String getGrade(double percentage) {
        if (percentage >= 90) return "Grade: A (Excellent!)";
        if (percentage >= 75) return "Grade: B (Good job)";
        if (percentage >= 60) return "Grade: C (Fair)";
        if (percentage >= 50) return "Grade: D (Needs improvement)";
        return "Grade: F (Review the material and try again)";
    }

    public void reviewWrongAnswers() {
        if (wrongQuestions.isEmpty()) {
            System.out.println("\nNo wrong answers to review — perfect score!");
            return;
        }

        System.out.println("\n===== REVIEW: QUESTIONS YOU GOT WRONG =====");
        for (int i = 0; i < wrongQuestions.size(); i++) {
            Question q = wrongQuestions.get(i);
            int userChoice = wrongUserAnswers.get(i);

            System.out.println("\n[" + q.getCategory() + " - " + q.getDifficulty() + "] "
                    + q.getQuestionText());
            System.out.println("   Your answer:    " + q.getOptions()[userChoice]);
            System.out.println("   Correct answer: " + q.getCorrectAnswerText());
        }
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public boolean hasWrongAnswers() {
        return !wrongQuestions.isEmpty();
    }
}
