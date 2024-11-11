package com.example.aplicacion_elecciones_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion_elecciones_kotlin.database.Database

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Database.init(this)

        findViewById<Button>(R.id.login).setOnClickListener(this::login)
    }

    private fun login(view: View)
    {
        val nif = findViewById<EditText>(R.id.nif).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        if (AccountManager.login(nif, password))
        {
            startActivity(Intent(this, VoteActivity::class.java))
        }
        else
        {
            // Show error
        }
    }
}