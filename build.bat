@echo off

rem Dependencies: Java (1.7 or newer), Maven, Visual Studio
rem Must be added to path: Java, Maven
rem MSBuild location must be added to file "msbuild_location.config" from command "reg.exe query "HKLM\SOFTWARE\Microsoft\MSBuild\ToolsVersions\4.0" /v MSBuildToolsPath".

rem Read MSBuild location from config
set /p msbuild_location=< msbuild_location.config

echo.
echo ==================================================
echo  Building JNI headers
echo ==================================================
@echo on
call make_headers.bat
@echo off
if %errorlevel% neq 0 goto error

echo.
echo ==================================================
echo  Building back-end
echo ==================================================
cd "JLAW back"
%msbuild_location%\msbuild
cd ..

echo.
echo Extracting DLLs:
@echo on
copy /Y "JLAW back\Release\JLAW back.dll" "bin\jlaw.dll"
@if %errorlevel% neq 0 goto error
copy /Y "JLAW back\x64\Release\JLAW back.dll" "bin\jlaw64.dll"
@if %errorlevel% neq 0 goto error
@echo off

echo.
echo ==================================================
echo  Building front-end
echo ==================================================
echo TODO

echo.
echo ==================================================
echo  Building test
echo ==================================================
echo TODO

echo.
echo ==================================================
echo  BUILD SUCCESS!
echo ==================================================

goto eof

:error
@echo off
echo.
echo ==================================================
echo  BUILD FAILED! See last error ...
echo ==================================================
exit /b

:eof
