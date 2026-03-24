@echo off
setlocal EnableDelayedExpansion

set DIR=%~dp0
set ROOT_NAME=crm-auth-skill
set APP_JAR=%DIR%app\crm-auth.jar
set JAVA_EXE=%DIR%runtime\jre\bin\java.exe
set OUTPUT_ZIP=%DIR%..\%ROOT_NAME%.zip
set TEMP_DIR=%DIR%..\%ROOT_NAME%-release-temp

if not exist "%APP_JAR%" (
    echo [ERROR] 未找到 app\crm-auth.jar，请先执行 build.bat
    exit /b 1
)

if not exist "%JAVA_EXE%" (
    echo [ERROR] 未找到内置 JRE: runtime\jre\bin\java.exe
    echo [ERROR] 请先把可分发 JRE 放到 runtime\jre 目录
    exit /b 1
)

if exist "%TEMP_DIR%" (
    rmdir /s /q "%TEMP_DIR%"
)

mkdir "%TEMP_DIR%\%ROOT_NAME%"
mkdir "%TEMP_DIR%\%ROOT_NAME%\app"
mkdir "%TEMP_DIR%\%ROOT_NAME%\config"
mkdir "%TEMP_DIR%\%ROOT_NAME%\runtime"

copy /Y "%DIR%run.bat" "%TEMP_DIR%\%ROOT_NAME%\run.bat" >nul
copy /Y "%DIR%README.md" "%TEMP_DIR%\%ROOT_NAME%\README.md" >nul
copy /Y "%DIR%SKILL.md" "%TEMP_DIR%\%ROOT_NAME%\SKILL.md" >nul
copy /Y "%DIR%app\crm-auth.jar" "%TEMP_DIR%\%ROOT_NAME%\app\crm-auth.jar" >nul
copy /Y "%DIR%config\skill-config.example.json" "%TEMP_DIR%\%ROOT_NAME%\config\skill-config.example.json" >nul

(
    echo {
    echo   "tokenUrl": "http://192.168.1.251:9999/auth/oauth/token",
    echo   "basicAuthUsername": "crm-admin",
    echo   "basicAuthPassword": "crm-admin",
    echo   "scope": "server",
    echo   "grantType": "password",
    echo   "crmUsername": "",
    echo   "crmPassword": ""
    echo }
) > "%TEMP_DIR%\%ROOT_NAME%\config\skill-config.json"

xcopy "%DIR%runtime\jre" "%TEMP_DIR%\%ROOT_NAME%\runtime\jre" /E /I /Y /Q >nul

if exist "%OUTPUT_ZIP%" (
    del /f /q "%OUTPUT_ZIP%"
)

powershell -NoProfile -ExecutionPolicy Bypass -Command "Compress-Archive -Path '%TEMP_DIR%\%ROOT_NAME%\*' -DestinationPath '%OUTPUT_ZIP%' -Force"
if errorlevel 1 (
    echo [ERROR] 打包 zip 失败。
    rmdir /s /q "%TEMP_DIR%"
    exit /b 1
)

rmdir /s /q "%TEMP_DIR%"

echo [INFO] 打包完成：%OUTPUT_ZIP%
echo [INFO] 发布包仅包含正式运行所需文件。
echo [INFO] 已自动脱敏 skill-config.json，不会带出真实账号密码。
exit /b 0
