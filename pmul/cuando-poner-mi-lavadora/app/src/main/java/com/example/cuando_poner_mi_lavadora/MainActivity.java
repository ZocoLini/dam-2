package com.example.cuando_poner_mi_lavadora;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private TextView horaActual;
    private TextView tiempoRestante;

    private boolean actualizarInterfaz = true;

    private static final Map<Integer, Consumo> mapaConsumo = new HashMap<>();

    static
    {
        mapaConsumo.put(0, Consumo.VERDE);
        mapaConsumo.put(1, Consumo.VERDE);
        mapaConsumo.put(2, Consumo.ROJO);
        mapaConsumo.put(3, Consumo.ROJO);
        mapaConsumo.put(4, Consumo.VERDE);
        mapaConsumo.put(5, Consumo.VERDE);
        mapaConsumo.put(6, Consumo.VERDE);
        mapaConsumo.put(7, Consumo.AMARILLO);
        mapaConsumo.put(8, Consumo.AMARILLO);
        mapaConsumo.put(9, Consumo.AMARILLO);
        mapaConsumo.put(10, Consumo.VERDE);
        mapaConsumo.put(11, Consumo.VERDE);
        mapaConsumo.put(12, Consumo.AMARILLO);
        mapaConsumo.put(13, Consumo.AMARILLO);
        mapaConsumo.put(14, Consumo.VERDE);
        mapaConsumo.put(15, Consumo.VERDE);
        mapaConsumo.put(16, Consumo.VERDE);
        mapaConsumo.put(17, Consumo.ROJO);
        mapaConsumo.put(18, Consumo.ROJO);
        mapaConsumo.put(19, Consumo.ROJO);
        mapaConsumo.put(20, Consumo.ROJO);
        mapaConsumo.put(21, Consumo.AMARILLO);
        mapaConsumo.put(22, Consumo.ROJO);
        mapaConsumo.put(23, Consumo.AMARILLO);
    }

    private enum Consumo
    {
        ROJO,
        AMARILLO,
        VERDE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        horaActual = findViewById(R.id.hora_actual);
        tiempoRestante = findViewById(R.id.tiempo_restante);

        Thread thread = new Thread(this::actualizarInterfaz);
        thread.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        actualizarInterfaz = false;
    }

    private void actualizarInterfaz()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;

        ConstraintLayout layout = findViewById(R.id.main);

        try
        {
            LocalTime time;
            while (actualizarInterfaz)
            {
                time = LocalTime.now();
                horaActual.setText(time.truncatedTo(ChronoUnit.SECONDS).toString());

                switch (mapaConsumo.get(time.getHour() % 24))
                {
                    case ROJO:
                        layout.setBackgroundColor(Color.RED);
                        break;
                    case VERDE:
                        layout.setBackgroundColor(Color.GREEN);
                        break;
                    case AMARILLO:
                        layout.setBackgroundColor(Color.YELLOW);
                        break;
                }

                Thread.sleep(1000);
            }
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}