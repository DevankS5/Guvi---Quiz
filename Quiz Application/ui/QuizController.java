package ui;

import dao.QuestionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ProgressBar; // Import ProgressBar
import javafx.scene.layout.VBox;
import model.Question;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

public class QuizController {

    @FXML
    private Label questionNumberLabel;
    @FXML
    private Label questionTextLabel;
    @FXML
    private RadioButton optionA_rb;
    @FXML
    private RadioButton optionB_rb;
    @FXML
    private RadioButton optionC_rb;
    @FXML
    private RadioButton optionD_rb;
    @FXML
    private ToggleGroup optionsGroup;
    @FXML
    private Button submitButton;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private ProgressBar quizProgressBar; // Added ProgressBar field

    @FXML
    private VBox resultsVBox;
    @FXML
    private Label finalScoreLabel;
    @FXML
    private Button restartButton;
    
    @FXML
    private VBox optionsVBox; // To hide/show options area

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private static final int SCORE_PER_QUESTION = 10; 

    public void initialize() {
        resultsVBox.setVisible(false); 
        resultsVBox.setManaged(false); // Also set managed to false when not visible
        optionsVBox.setManaged(true); 
        questionTextLabel.setManaged(true);
        questionNumberLabel.setManaged(true);
        submitButton.setManaged(true);
        quizProgressBar.setProgress(0.0); // Initialize progress bar
        quizProgressBar.setVisible(true);
        quizProgressBar.setManaged(true);

        loadQuestions();
        if (questions != null && !questions.isEmpty()) {
            displayQuestion();
        } else {
            questionTextLabel.setText("Failed to load questions or no questions available.");
            submitButton.setDisable(true);
            optionA_rb.setDisable(true);
            optionB_rb.setDisable(true);
            optionC_rb.setDisable(true);
            optionD_rb.setDisable(true);
            quizProgressBar.setVisible(false);
        }
    }

    private void loadQuestions() {
        QuestionDAO questionDAO = new QuestionDAO();
        try {
            questions = questionDAO.getAllQuestions();
            if (questions != null && !questions.isEmpty()) {
                 Collections.shuffle(questions); 
            } else {
                // Handle case where DAO returns null or empty list, even without SQLException
                feedbackLabel.setText("No questions found in the database.");
                feedbackLabel.setStyle("-fx-text-fill: orange;");
                this.questions = Collections.emptyList(); // Ensure it's an empty list, not null
            }
        } catch (SQLException e) {
            e.printStackTrace();
            feedbackLabel.setText("Error loading questions: " + e.getMessage());
            feedbackLabel.setStyle("-fx-text-fill: red;");
            questions = Collections.emptyList(); // Ensure questions list is empty on failure
        }
    }

    private void displayQuestion() {
        if (questions == null || currentQuestionIndex >= questions.size()) {
            showResults();
            return;
        }

        // Update progress bar
        if (questions != null && !questions.isEmpty()) {
            double progress = (double) (currentQuestionIndex + 1) / questions.size();
            quizProgressBar.setProgress(progress);
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + "/" + questions.size());
        questionTextLabel.setText(currentQuestion.getQuestionText());
        optionA_rb.setText(currentQuestion.getOptionA());
        optionB_rb.setText(currentQuestion.getOptionB());
        optionC_rb.setText(currentQuestion.getOptionC());
        optionD_rb.setText(currentQuestion.getOptionD());

        if (optionsGroup.getSelectedToggle() != null) {
            optionsGroup.getSelectedToggle().setSelected(false);
        }
        feedbackLabel.setText("");
        submitButton.setText("Submit Answer");
        submitButton.setDisable(false); 
        
        submitButton.setOnAction(this::handleSubmitButtonAction);
        
        optionsVBox.setVisible(true);
        optionsVBox.setManaged(true);
        questionTextLabel.setVisible(true);
        questionTextLabel.setManaged(true);
        questionNumberLabel.setVisible(true);
        questionNumberLabel.setManaged(true);
        submitButton.setVisible(true);
        submitButton.setManaged(true);
        feedbackLabel.setVisible(true);
        feedbackLabel.setManaged(true);
        quizProgressBar.setVisible(true);
        quizProgressBar.setManaged(true);

        resultsVBox.setVisible(false);
        resultsVBox.setManaged(false);
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) optionsGroup.getSelectedToggle();

        if (selectedRadioButton == null) {
            feedbackLabel.setText("Please select an answer.");
            feedbackLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            return;
        }

        String userAnswer = "";
        if (selectedRadioButton == optionA_rb) userAnswer = "A";
        else if (selectedRadioButton == optionB_rb) userAnswer = "B";
        else if (selectedRadioButton == optionC_rb) userAnswer = "C";
        else if (selectedRadioButton == optionD_rb) userAnswer = "D";

        Question currentQuestion = questions.get(currentQuestionIndex);
        if (currentQuestion.isCorrectAnswer(userAnswer)) {
            score += SCORE_PER_QUESTION;
            feedbackLabel.setText("Correct!");
            feedbackLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
        } else {
            feedbackLabel.setText("Incorrect. The correct answer was " + getCorrectOptionText(currentQuestion) + ".");
            feedbackLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
        scoreLabel.setText("Score: " + score);
        scoreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #27ae60;");
        
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            submitButton.setText("Next Question");
            submitButton.setOnAction(e -> displayQuestion());
        } else {
            submitButton.setText("Show Results");
            submitButton.setOnAction(e -> showResults());
        }
    }
    
    private String getCorrectOptionText(Question question) {
        String correctAnswerLetter = question.getCorrectAnswer(); 
        if (correctAnswerLetter == null) {
             System.err.println("Warning: Correct answer letter is null for question: " + question.getQuestionText());
             return "N/A (Error: Missing correct answer data)";
        }

        switch (correctAnswerLetter.toUpperCase()) {
            case "A": return "A (" + question.getOptionA() + ")";
            case "B": return "B (" + question.getOptionB() + ")";
            case "C": return "C (" + question.getOptionC() + ")";
            case "D": return "D (" + question.getOptionD() + ")";
            default:
                System.err.println("Warning: Unknown correct answer letter '" + correctAnswerLetter + "' for question: " + question.getQuestionText());
                return "N/A (Error: Invalid correct answer letter)";
        }
    }

    private void showResults() {
        optionsVBox.setVisible(false); 
        optionsVBox.setManaged(false); 
        questionTextLabel.setVisible(false);
        questionTextLabel.setManaged(false);
        questionNumberLabel.setVisible(false);
        questionNumberLabel.setManaged(false);
        submitButton.setVisible(false);
        submitButton.setManaged(false);
        feedbackLabel.setVisible(false);
        feedbackLabel.setManaged(false);
        quizProgressBar.setVisible(false); 
        quizProgressBar.setManaged(false);

        resultsVBox.setVisible(true);
        resultsVBox.setManaged(true);
        finalScoreLabel.setText("Your final score is: " + score + "/" + (questions.size() * SCORE_PER_QUESTION));
        finalScoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #34495e;");
        restartButton.setOnAction(this::handleRestartButtonAction); // Ensure restart action is set
    }

    @FXML
    private void handleRestartButtonAction(ActionEvent event) {
        currentQuestionIndex = 0;
        score = 0;
        feedbackLabel.setText("");
        scoreLabel.setText("Score: 0");
        quizProgressBar.setProgress(0.0); 

        // Reload and shuffle questions for a new quiz session
        loadQuestions(); 
        if (questions != null && !questions.isEmpty()) {
            displayQuestion(); // This will make the quiz elements visible and managed
        } else {
            questionTextLabel.setText("Failed to restart quiz. Could not load questions.");
            questionTextLabel.setVisible(true);
            questionTextLabel.setManaged(true);
            submitButton.setDisable(true);
            optionA_rb.setDisable(true);
            optionB_rb.setDisable(true);
            optionC_rb.setDisable(true);
            optionD_rb.setDisable(true);
            quizProgressBar.setVisible(false);
            quizProgressBar.setManaged(false);
            resultsVBox.setVisible(false); // Hide results if restart fails to load questions
            resultsVBox.setManaged(false);
            optionsVBox.setVisible(false); // Hide options if restart fails
            optionsVBox.setManaged(false);
        }
    }
}