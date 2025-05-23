@echo off
set WORKSPACE_PATH=%~dp0
set WORKSPACE_PATH=%WORKSPACE_PATH:~0,-1%

javac -cp "%WORKSPACE_PATH%;%WORKSPACE_PATH%/lib/mysql-connector-j-9.3.0.jar" --module-path "C:\Program Files\Java\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml "%WORKSPACE_PATH%/*.java" "%WORKSPACE_PATH%/ui/*.java" "%WORKSPACE_PATH%/dao/*.java" "%WORKSPACE_PATH%/model/*.java"

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
) else (
    echo Compilation failed!
) 