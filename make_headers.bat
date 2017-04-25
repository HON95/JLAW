javah -cp "JLAW front/src/main/java" -o "JLAW back/JLAW back/JlawArxControl.h" ninja.hon95.jlaw.JlawArxControl
@if %errorlevel% neq 0 exit /b %errorlevel%
javah -cp "JLAW front/src/main/java" -o "JLAW back/JLAW back/JlawGkey.h" ninja.hon95.jlaw.JlawGkey
@if %errorlevel% neq 0 exit /b %errorlevel%
javah -cp "JLAW front/src/main/java" -o "JLAW back/JLAW back/JlawLcd.h" ninja.hon95.jlaw.JlawLcd
@if %errorlevel% neq 0 exit /b %errorlevel%
javah -cp "JLAW front/src/main/java" -o "JLAW back/JLAW back/JlawLed.h" ninja.hon95.jlaw.JlawLed
@if %errorlevel% neq 0 exit /b %errorlevel%
javah -cp "JLAW front/src/main/java" -o "JLAW back/JLAW back/JlawSteeringWheel.h" ninja.hon95.jlaw.JlawSteeringWheel
@if %errorlevel% neq 0 exit /b %errorlevel%
