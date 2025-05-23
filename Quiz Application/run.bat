@echo off
set WORKSPACE_PATH=%~dp0
set WORKSPACE_PATH=%WORKSPACE_PATH:~0,-1%

java --module-path "C:\Program Files\Java\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml -cp "%WORKSPACE_PATH%;%WORKSPACE_PATH%/lib/mysql-connector-j-9.3.0.jar" QuizApplication

if %ERRORLEVEL% EQU 0 (
    echo Application closed successfully!
) else (
    echo Application encountered an error!
) 