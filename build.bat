@echo off
setlocal

set DIR=%~dp0
set TARGET_JAR=%DIR%target\crm-auth.jar
set APP_DIR=%DIR%app
set APP_JAR=%APP_DIR%\crm-auth.jar

where mvn >nul 2>nul
if errorlevel 1 (
    echo [ERROR] 未检测到 Maven，请先安装 Maven 并确保 mvn 在 PATH 中。
    exit /b 1
)

if not exist "%APP_DIR%" (
    mkdir "%APP_DIR%"
)

cd /d "%DIR%"
echo [INFO] 开始执行 Maven 打包...
call mvn clean package
if errorlevel 1 (
    echo [ERROR] Maven 打包失败。
    exit /b 1
)

if not exist "%TARGET_JAR%" (
    echo [ERROR] 未找到打包产物: %TARGET_JAR%
    exit /b 1
)

echo [INFO] 复制打包产物到 app 目录...
copy /Y "%TARGET_JAR%" "%APP_JAR%" >nul
if errorlevel 1 (
    echo [ERROR] 复制 jar 失败。
    exit /b 1
)

echo [INFO] 构建完成：%APP_JAR%
exit /b 0
