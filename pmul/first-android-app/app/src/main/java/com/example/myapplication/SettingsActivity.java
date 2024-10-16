package com.example.myapplication;

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
    private EditText editText;
    private Spinner spinner;
    private CheckBox checkBox;

    private enum SettingsOption
    {
        OPTION_1,
        OPTION_2,
        OPTION_3
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

        editText = findViewById(R.id.input_text);
        spinner = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.check_box);

        findViewById(R.id.button_save).setOnClickListener(this::savePreferences);
        findViewById(R.id.button_exit).setOnClickListener(this::exit);
        loadPreferences();
    }

    private void loadPreferences()
    {
        ArrayAdapter<SettingsOption> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                SettingsOption.values()
        );

        spinner.setAdapter(adapter);

        Settings settings = Settings.getInstance(this);

        editText.setText(settings.getName());
        spinner.setSelection(settings.getSpinnerSelection());
        checkBox.setChecked(settings.getHelpedReceived());
    }

    private void savePreferences(View view)
    {
        boolean out = Settings.getInstance(this).savePreferences(
                editText.getText().toString(),
                spinner.getSelectedItemPosition(),
                checkBox.isChecked()
        );

        if (out) finish();
        else System.out.println("Error saving preferences");
    }

    private void exit(View view)
    {
        finish();
    }
}