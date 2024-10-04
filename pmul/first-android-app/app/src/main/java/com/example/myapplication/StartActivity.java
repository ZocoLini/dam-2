package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sp = getSharedPreferences(SettingsActivity.SETTINGS_FILE, MODE_PRIVATE);

        ((TextView) findViewById(R.id.text_view)).setText(sp.getString(SettingsActivity.NAME_FIELD,
                "No se ha encontrado ningún registro, vaya a ajustes e ingreselo"));

        findViewById(R.id.settings_button).setOnClickListener(this::openSettings);
    }

    private void openSettings(View button)
    {
        startActivity( new Intent(this, SettingsActivity.class));
    }
}