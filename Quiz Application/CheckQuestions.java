import dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckQuestions {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM questions");
            
            if (rs.next()) {
                System.out.println("Total number of questions in database: " + rs.getInt("count"));
            }
            
            // Also show a few sample questions
            System.out.println("\nSample questions from database:");
            rs = stmt.executeQuery("SELECT * FROM questions LIMIT 5");
            while (rs.next()) {
                System.out.println("\nQuestion: " + rs.getString("question_text"));
                System.out.println("A: " + rs.getString("option_a"));
                System.out.println("B: " + rs.getString("option_b"));
                System.out.println("C: " + rs.getString("option_c"));
                System.out.println("D: " + rs.getString("option_d"));
                System.out.println("Correct Answer: " + rs.getString("correct_answer"));
            }
            
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 