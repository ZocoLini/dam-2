package com.example.ejercicio_telefonos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercicio_telefonos.telephone.Telephone
import com.example.ejercicio_telefonos.fragments.TelephoneFragment
import com.example.ejercicio_telefonos.telephone.Operadora

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

    override fun onStart()
    {
        super.onStart();

        initializeTelephones();
    }

    private fun initializeTelephones()
    {
        val telephone1 = Telephone(1);
        val telephone2 = Telephone(2);
        val telephone3 = Telephone(3);
        val telephone4 = Telephone(4);

        registerEventsTelephone(telephone1, supportFragmentManager.findFragmentById(R.id.fragment_telefono1) as TelephoneFragment);
        registerEventsTelephone(telephone2, supportFragmentManager.findFragmentById(R.id.fragment_telefono2) as TelephoneFragment);
        registerEventsTelephone(telephone3, supportFragmentManager.findFragmentById(R.id.fragment_telefono3) as TelephoneFragment);
        registerEventsTelephone(telephone4, supportFragmentManager.findFragmentById(R.id.fragment_telefono4) as TelephoneFragment);

        Operadora.addTelephone(telephone1);
        Operadora.addTelephone(telephone2);
        Operadora.addTelephone(telephone3);
        Operadora.addTelephone(telephone4);
    }

    private fun registerEventsTelephone(telephone: Telephone, telephoneFragment: TelephoneFragment)
    {
        telephoneFragment.setActions(object : TelephoneFragment.TelephoneFragmentActions
        {
            override fun onPhoneAction(telephoneNumber: Int)
            {
                if (telephone.isInACall())
                {
                    telephone.hangUp();
                    telephoneFragment.setIdleApparience();
                }
                else if (telephone.call(telephoneNumber))
                {
                    telephoneFragment.setCallingApparience(telephoneNumber);
                }
            }

            override fun getTelephoneNumber(): Int
            {
                return telephone.getTelephoneNumber();
            }
        });

        telephone.setTelephoneActions(object : Telephone.TelephoneActions
        {
            override fun onReceivingCall(callingFromNumber: Int)
            {
                telephoneFragment.setReceivingCallApparecience(callingFromNumber)
            }

            override fun onOtherHangUp()
            {
                telephoneFragment.setIdleApparience();
            }

        })
    }
}