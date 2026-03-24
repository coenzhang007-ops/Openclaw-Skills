@echo off
setlocal

set DIR=%~dp0
set JAVA_CMD=%DIR%runtime\jre\bin\java.exe
set JAR_PATH=%DIR%app\crm-auth.jar

if not exist "%JAVA_CMD%" (
    echo {"success":false,"message":"未找到内置JRE，请检查 runtime\\jre 是否完整"}
    exit /b 1
)

if not exist "%JAR_PATH%" (
    echo {"success":false,"message":"未找到 app\\crm-auth.jar"}
    exit /b 1
)

if "%1"=="" (
    set ACTION=login
) else (
    set ACTION=%1
)

"%JAVA_CMD%" -jar "%JAR_PATH%" %ACTION%

endlocal
