#!/bin/bash

origin="/data/data"
destin="com.example.lista_clientes_db"

if ! adb devices | grep -q "device$"; then
    echo "Error: No se ha detectado ningún dispositivo/emulador."
    exit 1
fi

adb push "$destin" "$origin"

if [ $? -eq 0 ]; then
    echo "Push completado exitosamente."
else
    echo "Error durante el push."
fi

adb shell chmod -R 660 "$origin/$destin"
if [ $? -ne 0 ]; then
    echo "Error cambiando permisos."
    fi 
    
adb shell chown -R u0_a208:u0_a208 "$origin"/"$destin" 
if [ $? -ne 0 ]; then
    echo "Error cambiando propietario."
    fi
