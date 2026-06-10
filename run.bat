@echo off
chcp 65001 >nul


echo.
echo    ██╗   ██╗██╗██╗  ██╗██╗███╗   ██╗ ██████╗ ███████╗
echo    ██║   ██║██║██║ ██╔╝██║████╗  ██║██╔════╝ ██╔════╝
echo    ██║   ██║██║█████╔╝ ██║██╔██╗ ██║██║  ███╗███████╗
echo    ╚██╗ ██╔╝██║██╔═██╗ ██║██║╚██╗██║██║   ██║╚════██║
echo     ╚████╔╝ ██║██║  ██╗██║██║ ╚████║╚██████╔╝███████║
echo      ╚═══╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝
echo.
echo    =================================================
echo    =              RAID PLANNER                     =
echo    =================================================
echo.
echo    Checking for the existence of the JAR file...
if not exist target\planner-1.0-SNAPSHOT.jar (
    echo JAR file not found!
    echo Run this first: mvn clean package
    pause
    exit /b
)
echo.
echo    Starting Vikings Planner...
echo    Open your browser and go to http://localhost:8080/index.html
echo.
echo    Press Ctrl+C to stop
echo    ========================================
echo.
java -jar target\planner-1.0-SNAPSHOT.jar
pause