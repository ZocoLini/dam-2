package com.example.aplicacion_elecciones;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplicacion_elecciones.database.Database;
import com.example.aplicacion_elecciones.database.entities.UsuarioDAO;

public class LoginActivity extends AppCompatActivity
{
    private EditText etNif;
    private EditText etPassword;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database.initialize(this);

        etNif = findViewById(R.id.nif);
        etPassword = findViewById(R.id.password);
        tvError = findViewById(R.id.error);

        etNif.addTextChangedListener(nifTextWatcher);

        findViewById(R.id.login).setOnClickListener(v -> login());
    }

    private final TextWatcher nifTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable)
        {
            if (!UsuarioDAO.validateNIFFormat(editable.toString()))
            {
                etNif.setError("Invalid NIF format");
            }
        }
    };

    private void login()
    {
        String name = etNif.getText().toString();
        String password = etPassword.getText().toString();

        if (name.isBlank() || password.isBlank())
        {
            tvError.setText("Name and password are required");
            return;
        }

        if (!UsuarioDAO.validateUser(name, password))
        {
            tvError.setText("Invalid name or password");
            return;
        }

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}