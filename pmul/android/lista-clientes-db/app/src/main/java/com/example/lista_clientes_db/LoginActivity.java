package com.example.lista_clientes_db;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lista_clientes_db.database.Database;
import com.example.lista_clientes_db.database.entities.UsuarioDAO;

public class LoginActivity extends AppCompatActivity
{
    private EditText etName;
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

        etName = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        tvError = findViewById(R.id.error);

        findViewById(R.id.login).setOnClickListener(v -> login());
    }

    private void login()
    {
        String name = etName.getText().toString();
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