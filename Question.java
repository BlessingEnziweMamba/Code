package Blessing;

/**
 * Represents a single multiple-choice quiz question.
 * Each question has 4 options (A-D), a correct answer index,
 * a category, and a difficulty level.
 */
public class Question {

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

    /**
     * Checks if a given answer index (0-3) matches the correct answer.
     */
    public boolean isCorrect(int chosenIndex) {
        return chosenIndex == correctIndex;
    }

    /**
     * Prints the question and its options in A-D format.
     */
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
