@echo off
cd /d "%~dp0"
if not exist out mkdir out
javac -encoding UTF-8 -d out src\main\java\com\ecommerce\model\*.java src\main\java\com\ecommerce\payment\*.java src\main\java\com\ecommerce\service\*.java src\main\java\com\ecommerce\exception\*.java src\main\java\com\ecommerce\main\*.java
if errorlevel 1 pause & exit /b 1
java -Dfile.encoding=UTF-8 -cp out com.ecommerce.main.ECommerceApplication
pause
