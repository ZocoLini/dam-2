package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity
{

    public static final String SETTINGS_FILE = "settings.xml";
    public static final String SPINNER_FIELD = "spinner_field";
    public static final String NAME_FIELD = "name_field";
    public static final String HELPED_RECEIVED_FIELD = "helped_received_field";

    private enum Opcion
    {
        OPCION_1,
        OPCION_2,
        OPCION_3
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.button).setOnClickListener(this::savePreferences);
        loadPreferences();
    }

    private void loadPreferences()
    {
        EditText editText = findViewById(R.id.input_text);
        Spinner spinner = findViewById(R.id.spinner);
        CheckBox checkBox = findViewById(R.id.check_box);

        SharedPreferences sp = getSharedPreferences(SETTINGS_FILE, MODE_PRIVATE);

        ArrayAdapter<Opcion> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Opcion.values()
        );

        spinner.setAdapter(adapter);

        editText.setText(sp.getString(NAME_FIELD, ""));
        spinner.setSelection(sp.getInt(SPINNER_FIELD, 0));
        checkBox.setChecked(sp.getBoolean(HELPED_RECEIVED_FIELD, false));
    }

    private void savePreferences(View view)
    {
        EditText editText = findViewById(R.id.input_text);
        Spinner spinner = findViewById(R.id.spinner);
        CheckBox checkBox = findViewById(R.id.check_box);

        SharedPreferences sp = getSharedPreferences("settings.xml", MODE_PRIVATE);

        sp.edit().putString(NAME_FIELD, editText.getText().toString())
                .putInt(SPINNER_FIELD, spinner.getSelectedItemPosition())
                .putBoolean(HELPED_RECEIVED_FIELD, checkBox.isChecked())
                .apply();

        // TODO: No habria que cargar una nueva, sino ir a la anterior
        startActivity(new Intent(this, StartActivity.class));
    }
}