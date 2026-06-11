@echo off
chcp 65001 >nul

set JAVA_EXE=
for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-21*") do set JAVA_EXE=%%i\bin\java.exe
if exist "C:\Program Files\Java\jdk-21\bin\java.exe" set JAVA_EXE=C:\Program Files\Java\jdk-21\bin\java.exe
if exist "C:\Program Files\Eclipse Adoptium\jdk-21\bin\java.exe" set JAVA_EXE=C:\Program Files\Eclipse Adoptium\jdk-21\bin\java.exe

if "%JAVA_EXE%"=="" (
    echo ERROR: JDK 21 not found!
    echo Please install JDK 21
    pause
    exit /b
)

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

"%JAVA_EXE%" -version
echo.
"%JAVA_EXE%" -jar target\planner-1.0-SNAPSHOT.jar

pause