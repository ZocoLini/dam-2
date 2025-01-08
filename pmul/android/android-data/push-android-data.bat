@echo off
echo Iniciando ADB Push de la carpeta /data...

set PACKAGE_ID=%1

:: Verificar si ADB está disponible
adb devices
if errorlevel 1 (
    echo ADB no está disponible. Por favor, asegúrate de que ADB esté instalado y configurado.
    exit /b
)

adb root
if errorlevel 1 (
    echo No se pudo obtener acceso root. Por favor, asegúrate de que el dispositivo esté rooteado.
    exit /b
)

adb push data %PACKAGE_ID%