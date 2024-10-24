#!/bin/bash

origin="data/data/com.example.lista_clientes_db"
destin="."

if ! adb devices | grep -q "device$"; then
    echo "Error: No se ha detectado ningún dispositivo/emulador."
    exit 1
fi

adb pull "$origin" "$destin"

if [ $? -eq 0 ]; then
    echo "Extracción completada exitosamente."
else
    echo "Error durante la extracción."
fi
