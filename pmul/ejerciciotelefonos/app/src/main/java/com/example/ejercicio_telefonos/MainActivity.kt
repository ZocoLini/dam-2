package com.example.ejercicio_telefonos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercicio_telefonos.telephone.Operadora
import com.example.ejercicio_telefonos.telephone.TelephoneFragment

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

        Operadora.addTelephone(supportFragmentManager.findFragmentById(R.id.fragment_telefono1)
                as TelephoneFragment
        )
        Operadora.addTelephone(supportFragmentManager.findFragmentById(R.id.fragment_telefono2)
                as TelephoneFragment
        )
        Operadora.addTelephone(supportFragmentManager.findFragmentById(R.id.fragment_telefono3)
                as TelephoneFragment
        )
        Operadora.addTelephone(supportFragmentManager.findFragmentById(R.id.fragment_telefono4)
                as TelephoneFragment
        )
    }

    override fun onStart() {
        super.onStart()

    }
}