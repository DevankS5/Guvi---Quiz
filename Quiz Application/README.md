# Java Quiz Application

A JavaFX-based General Knowledge Quiz Application built in Java with MySQL database integration.

## Features
- Fetches quiz questions from a MySQL database
- Presents questions through a modern JavaFX GUI interface
- Accepts user answers
- Calculates and displays the final score

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server
- MySQL Connector/J (JDBC driver)
- JavaFX SDK 25 (or compatible version)

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone [your-repository-url]
   cd quiz-application
   ```

2. **Database Setup**
   - Create a MySQL database named `quiz_app`
   - Run the SQL script to create the questions table and insert questions:
     ```sql
     CREATE DATABASE quiz_app;
     USE quiz_app;
     source insert_questions.sql;
     ```

3. **Configuration**
   - Copy `config.properties.example` to `config.properties`
   - Update the database credentials in `config.properties`:
     ```properties
     db.url=jdbc:mysql://localhost:3306/quiz_app
     db.user=your_username
     db.password=your_password
     ```

4. **Compile and Run**
   
   The project includes two batch files for easy compilation and execution:

   a. **Using the provided batch files (Recommended)**
   - Double-click `compile.bat` to compile the application
   - Double-click `run.bat` to run the application
   
   These batch files can be run from any directory and will automatically find the correct paths.

   b. **Manual compilation and execution**
   ```bash
   # Compile
   javac -cp ".;lib/mysql-connector-j-9.3.0.jar" --module-path "C:\Program Files\Java\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml *.java ui/*.java dao/*.java model/*.java

   # Run
   java --module-path "C:\Program Files\Java\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml -cp ".;lib/mysql-connector-j-9.3.0.jar" QuizApplication
   ```

## Security Notes
- Never commit the `config.properties` file with real credentials
- The `config.properties.example` file is provided as a template
- Each developer should create their own `config.properties` file locally

## Project Structure
```
quiz-application/
├── lib/
│   └── mysql-connector-j-9.3.0.jar
├── model/
│   └── Question.java
├── dao/
│   ├── DatabaseConnection.java
│   └── QuestionDAO.java
├── ui/
│   └── QuizView.fxml
├── QuizApplication.java
├── compile.bat
├── run.bat
├── insert_questions.sql
├── config.properties.example
├── .gitignore
└── README.md
```

## Contributing
1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License
This project is licensed under the MIT License - see the LICENSE file for details.
