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
    private val telephones: HashMap<TelephoneFragment, Telephone> = HashMap();
    private val telephonesFragments: HashMap<Telephone, TelephoneFragment> = HashMap();

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

        val telephoneFragment1 = supportFragmentManager.findFragmentById(R.id.fragment_telefono1) as TelephoneFragment
        val telephoneFragment2 = supportFragmentManager.findFragmentById(R.id.fragment_telefono2) as TelephoneFragment
        val telephoneFragment3 = supportFragmentManager.findFragmentById(R.id.fragment_telefono3) as TelephoneFragment
        val telephoneFragment4 = supportFragmentManager.findFragmentById(R.id.fragment_telefono4) as TelephoneFragment

        telephones[telephoneFragment1] = telephone1;
        telephones[telephoneFragment2] = telephone2;
        telephones[telephoneFragment3] = telephone3;
        telephones[telephoneFragment4] = telephone4;

        telephonesFragments[telephone1] = telephoneFragment1;
        telephonesFragments[telephone2] = telephoneFragment2;
        telephonesFragments[telephone3] = telephoneFragment3;
        telephonesFragments[telephone4] = telephoneFragment4;

        registerEventsTelephone(telephone1, telephoneFragment1);
        registerEventsTelephone(telephone2, telephoneFragment2);
        registerEventsTelephone(telephone3, telephoneFragment3);
        registerEventsTelephone(telephone4, telephoneFragment4);

        Operadora.addTelephone(telephone1);
        Operadora.addTelephone(telephone2);
        Operadora.addTelephone(telephone3);
        Operadora.addTelephone(telephone4);
    }

    private fun registerEventsTelephone(telephone: Telephone, telephoneFragment: TelephoneFragment)
    {
        telephoneFragment.setActions(telephoneFragmentActions);
        telephone.setTelephoneActions(telephoneActions)
    }

    private val telephoneFragmentActions = object : TelephoneFragment.TelephoneFragmentActions
    {
        override fun onPhoneAction(telephoneNumber: Int, telephoneFragment: TelephoneFragment)
        {
            val telephone: Telephone = telephones[telephoneFragment]!!;

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

        override fun getTelephoneNumber(telephoneFragment: TelephoneFragment): Int
        {
            return telephones[telephoneFragment]!!.getTelephoneNumber();
        }
    }

    private val telephoneActions = object : Telephone.TelephoneActions
    {
        override fun onReceivingCall(callingFromNumber: Int, telephone: Telephone)
        {
            telephonesFragments[telephone]!!.setReceivingCallApparecience(callingFromNumber)
        }

        override fun onOtherHangUp(telephone: Telephone)
        {
            telephonesFragments[telephone]!!.setIdleApparience();
        }
    }
}