import dao.QuestionDAO;
import model.Question;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class QuizApplication extends Application {
    private static final int SCORE_PER_QUESTION = 10;
    private List<Question> questions;
    private int score;

    public void loadQuestions() throws SQLException {
        QuestionDAO questionDAO = new QuestionDAO();
        questions = questionDAO.getAllQuestions();
    }

    public void startQuiz() {
        score = 0;
        System.out.println("Welcome to the Quiz Application!\n");

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());
            System.out.println("A. " + question.getOptionA());
            System.out.println("B. " + question.getOptionB());
            System.out.println("C. " + question.getOptionC());
            System.out.println("D. " + question.getOptionD());

            String userAnswer = getUserAnswer();

            if (question.isCorrectAnswer(userAnswer)) {
                System.out.println("Correct!\n");
                score += SCORE_PER_QUESTION;
            } else {
                System.out.println("Incorrect!\n");
            }
        }

        System.out.println("Quiz Summary:");
        System.out.println("Total Questions: " + questions.size());
        System.out.println("Correct Answers: " + (score / SCORE_PER_QUESTION));
        System.out.println("Incorrect Answers: " + ((questions.size() * SCORE_PER_QUESTION - score) / SCORE_PER_QUESTION));
        System.out.println("Score: " + score + "%\n");

        System.out.println("Thank you for playing!");
    }

    private String getUserAnswer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your Answer: ");
        return scanner.nextLine().toUpperCase();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file from the ui package
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/QuizView.fxml")));

            Scene scene = new Scene(root);
            primaryStage.setTitle("Quiz Application");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file. Make sure QuizView.fxml is in the correct location (e.g., src/main/resources/ui/QuizView.fxml or directly in a 'ui' folder marked as a resources root).");
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println("Failed to find FXML file. Ensure '/ui/QuizView.fxml' path is correct relative to your resources folder.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
