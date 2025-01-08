package com.example.aplicacion_elecciones;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplicacion_elecciones.database.entities.CandidatoElecciones;
import com.example.aplicacion_elecciones.database.entities.CandidatoEleccionesDAO;

public class MainActivity extends AppCompatActivity
{
    private static final int VOTOS_MAXIMOS = 3;

    private Spinner candidatosSpinner;
    private Button votarButton;

    private int votosRealizados = 0;

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

        candidatosSpinner = findViewById(R.id.candidatosSpinner);
        votarButton = findViewById(R.id.votarButton);

        initialize();
    }

    public void initialize()
    {
        votarButton.setActivated(!AccountManager.getInstance().getCurrentUser().isVotacionesRealizadas());

        votarButton.setOnClickListener(this::votar);

        poblarSpinner();
    }

    private void poblarSpinner()
    {
        ArrayAdapter<CandidatoElecciones> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                CandidatoEleccionesDAO.selectAll()
        );

        candidatosSpinner.setAdapter(adapter);
    }

    private void votar(View view)
    {
        votosRealizados++;
        votarButton.setActivated(votosRealizados < VOTOS_MAXIMOS);

        CandidatoElecciones candidato = (CandidatoElecciones) candidatosSpinner.getSelectedItem();
        candidato.votar();
        CandidatoEleccionesDAO.updateNumVotos(candidato);

        votarButton.setText("Votar " + (VOTOS_MAXIMOS - votosRealizados) + " veces más");
    }
}