package com.example.ejercicio_telefonos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onAttachedToWindow()
    {
        Operadora.addTelefono(supportFragmentManager.findFragmentById(R.id.fragment_telefono1)
                as TelefonoFragment)
        Operadora.addTelefono(supportFragmentManager.findFragmentById(R.id.fragment_telefono2)
                as TelefonoFragment)
        Operadora.addTelefono(supportFragmentManager.findFragmentById(R.id.fragment_telefono3)
                as TelefonoFragment)
        Operadora.addTelefono(supportFragmentManager.findFragmentById(R.id.fragment_telefono4)
                as TelefonoFragment)
    }
}