package com.example.ejercicio_telefonos.telephone

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
class TelephoneFragment : Fragment() {
    private lateinit var action: TelephoneFragmentActions;
    private lateinit var editText: EditText;
    private lateinit var displayInfo: TextView;
    private var callingPhoneNumber: Int? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_telefono, container, false)

        view.findViewById<Button>(R.id.ib_telefono)!!
            .setOnClickListener { _ ->
                onAction();
            }

        editText = view.findViewById(R.id.et_telefono)!!;
        displayInfo = view.findViewById(R.id.tv_info)!!;

        return view;
    }

    fun setActions(actions: TelephoneFragmentActions) {
        action = actions;

        displayInfo.text = action.getTelephoneNumber().toString();
    }

    fun isInACall(): Boolean {
        return callingPhoneNumber != null;
    }

    private fun onAction() {
        if (callingPhoneNumber != null) {
            hangUp();
        } else {
            if (editText.text.isEmpty()) return;
            if (!editText.text.matches(Regex("[0-9]+"))) return;

            callTo(editText.text.toString().toInt());
        }
    }

    private fun hangUp() {
        action.onHangUp(callingPhoneNumber!!);
        callingPhoneNumber = null;

        displayInfo.text = action.getTelephoneNumber().toString();
    }

    private fun callTo(telephoneNumber: Int) {
        if (!action.onSendCall(telephoneNumber)) return;

        callingPhoneNumber = telephoneNumber;

        displayInfo.text = buildString {
            append(
                action
                    .getTelephoneNumber()
                    .toString()
            )
            append(" > ")
            append(callingPhoneNumber)
        };
    }

    fun onReceiveCall(telephoneNumber: Int) {
        action.onReceiveCall(telephoneNumber)

        callingPhoneNumber = telephoneNumber;

        displayInfo.text = buildString {
            append(
                action
                    .getTelephoneNumber()
                    .toString()
            )
            append(" < ")
            append(telephoneNumber)
        };
    }

    fun onOtherHangUp(hangUpFrom: Int) {
        callingPhoneNumber = null;

        action.onOtherHangUp(hangUpFrom);
        displayInfo.text = action.getTelephoneNumber().toString();
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TelefonoFragment.
         */
        @JvmStatic
        fun newInstance() =
            TelephoneFragment().apply {}
    }

    interface TelephoneFragmentActions {
        fun onReceiveCall(receivingFrom: Int);
        fun onSendCall(callingTo: Int): Boolean;
        fun onOtherHangUp(hangUpFrom: Int);
        fun onHangUp(hangUpTo: Int);
        fun getTelephoneNumber(): Int;
    }
}