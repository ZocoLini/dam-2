package com.example.ejercicio_telefonos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.ejercicio_telefonos.R

/**
 * A simple [Fragment] subclass.
 * Use the [TelephoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TelephoneFragment : Fragment()
{
    private var actions: TelephoneFragmentActions = object : TelephoneFragmentActions
    {
        override fun onPhoneAction(telephoneNumber: Int, telephoneFragment: TelephoneFragment) {}
        override fun getTelephoneNumber(telephoneFragment: TelephoneFragment): Int { return -1; }
    };
    private lateinit var editText: EditText;
    private lateinit var displayInfo: TextView;
    private lateinit var mainButton: Button;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_telefono, container, false)

        editText = view.findViewById(R.id.et_telefono)!!;
        displayInfo = view.findViewById(R.id.tv_info)!!;

        mainButton = view.findViewById(R.id.ib_telefono)!!;
        mainButton.setOnClickListener { _ ->
            actions.onPhoneAction(
                try
                {
                    editText.text
                        .toString()
                        .toInt()
                }
                catch (e: NumberFormatException)
                {
                    -1
                },
                this
            );
        }


        return view;
    }

    fun setActions(actions: TelephoneFragmentActions)
    {
        this.actions = actions;

        displayInfo.text = this.actions
            .getTelephoneNumber(this)
            .toString();
    }

    fun setReceivingCallApparecience(callingFromNumber: Int)
    {
        mainButton.setBackgroundResource(android.R.drawable.sym_call_incoming);

        setInACallApparience(callingFromNumber);

        val telephoneNumber = actions.getTelephoneNumber(this);

        displayInfo.text = buildString {
            append(telephoneNumber)
            append(" < ")
            append(callingFromNumber)
        };
    }

    fun setCallingApparience(callingToNumber: Int)
    {
        mainButton.setBackgroundResource(android.R.drawable.sym_call_outgoing);

        setInACallApparience(callingToNumber);

        val telephoneNumber = actions.getTelephoneNumber(this);

        displayInfo.text = buildString {
            append(telephoneNumber)
            append(" > ")
            append(callingToNumber)
        };

    }

    fun setIdleApparience()
    {
        mainButton.setBackgroundResource(android.R.drawable.stat_sys_phone_call);

        editText.text.clear();
        editText.isEnabled = true;

        displayInfo.text = actions
            .getTelephoneNumber(this)
            .toString();
    }

    private fun setInACallApparience(otherTelephoneNumber: Int)
    {
        editText.setText(otherTelephoneNumber.toString());
        editText.isEnabled = false;

    }

    companion object
    {
        @JvmStatic
        fun newInstance() =
            TelephoneFragment().apply {}
    }

    interface TelephoneFragmentActions
    {
        fun onPhoneAction(telephoneNumber: Int, telephoneFragment: TelephoneFragment);
        fun getTelephoneNumber(telephoneFragment: TelephoneFragment): Int;
    }
}