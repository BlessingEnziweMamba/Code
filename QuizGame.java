package Blessing;

import java.util.Scanner;

/*
 * ============================================
 *           JAVA QUIZ GAME 🎯
 * Topics: methods, if/else, loops, arrays, objects
 * Converted from the Python version.
 * ============================================
 */

public class QuizGame {

    // --- A simple class to represent one question ---
    // (this is Java's version of the Python dictionary)
    static class Question {
        String question;
        String[] options;
        String answer;

        Question(String question, String[] options, String answer) {
            this.question = question;
            this.options = options;
            this.answer = answer;
        }
    }

    // --- Question Bank ---
    static Question[] questions = {
        new Question(
            "What is the output of System.out.println(Math.pow(2, 3))?",
            new String[]{"A) 6", "B) 8.0", "C) 9", "D) 23"},
            "B"
        ),
        new Question(
            "Which symbol is used for comments in Java?",
            new String[]{"A) #", "B) --", "C) //", "D) **"},
            "C"
        ),
        new Question(
            "What does array.length return for {1, 2, 3}?",
            new String[]{"A) 2", "B) 4", "C) 0", "D) 3"},
            "D"
        ),
        new Question(
            "Which loop runs at least once no matter what?",
            new String[]{"A) for loop", "B) while loop", "C) if loop", "D) do-while loop"},
            "D"
        ),
        new Question(
            "What is 10 % 3 in Java?",
            new String[]{"A) 3", "B) 1", "C) 0", "D) 2"},
            "B"
        ),
        new Question(
            "who love girls too much in your group?",
            new String[]{"A) Prince", "B) Dini", "C) Blessing", "D) mzala"},
            "D"
        ),
        new Question(
            "what is the better programming language?",
            new String[]{"A) Python", "B) Java", "C) C++", "D) JavaScript"},
            "B"
        )
    };

    // Shared Scanner for all input in the program
    static Scanner scanner = new Scanner(System.in);

    // --- Method to display a question ---
    static void displayQuestion(int number, Question q) {
        System.out.println("Question: " + number);
        System.out.println(q.question);
        for (String option : q.options) {
            System.out.println(option);
        }
    }

    // --- Method to check the answer ---
    static boolean checkAnswer(String userAnswer, String correctAnswer) {
        return userAnswer.equals(correctAnswer);
    }

    // --- Method to show final score ---
    static void showResult(int score, int total) {
        System.out.println("============================");
        System.out.println("    QUIZ COMPLETE!");
        System.out.println("============================");
        System.out.println("Your score: " + score + " / " + total);

        double percentage = ((double) score / total) * 100;

        if (percentage == 100) {
            System.out.println("Perfect score! Outstanding!");
        } else if (percentage >= 80) {
            System.out.println("Excellent work!");
        } else if (percentage >= 60) {
            System.out.println("Good job, keep practising!");
        } else if (percentage >= 40) {
            System.out.println("Not bad, but review the material.");
        } else {
            System.out.println("Keep studying, you will improve!");
        }
    }

    // --- Main Game Loop ---
    static void playQuiz() {
        System.out.println("============================");
        System.out.println("   Welcome to Java Quiz!");
        System.out.println("============================");
        System.out.println("Answer with A, B, C or D\n");

        int score = 0;
        int questionNumber = 1;

        for (Question q : questions) {
            displayQuestion(questionNumber, q);

            System.out.print("Your answer: ");
            String userAnswer = scanner.nextLine().toUpperCase();

            while (!userAnswer.equals("A") && !userAnswer.equals("B")
                    && !userAnswer.equals("C") && !userAnswer.equals("D")) {
                System.out.println("Invalid! Please enter A, B, C or D.");
                System.out.print("Your answer: ");
                userAnswer = scanner.nextLine().toUpperCase();
            }

            if (checkAnswer(userAnswer, q.answer)) {
                System.out.println("Correct! 👌");
                score = score + 1;
            } else {
                System.out.println("Wrong! The correct answer was: " + q.answer);
            }

            questionNumber = questionNumber + 1;
            System.out.println("----------------------------");
        }

        showResult(score, questions.length);
    }

    // --- Start the game ---
    public static void main(String[] args) {
        playQuiz();
        scanner.close();
    }
}
