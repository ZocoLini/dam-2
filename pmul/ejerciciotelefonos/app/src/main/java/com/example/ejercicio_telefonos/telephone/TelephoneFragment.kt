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
    private var callingPhone: Int? = null;

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

        displayInfo.text = action.getTelephoneId().toString();
    }

    fun isInACall(): Boolean {
        return callingPhone != null;
    }

    private fun onAction() {
        if (callingPhone != null) {
            hangUp();
        } else {
            if (editText.text.isEmpty()) return;
            if (!editText.text.matches(Regex("[0-9]+"))) return;

            callTo(editText.text.toString().toInt());
        }
    }

    private fun hangUp() {
        action.onHangUp(callingPhone!!);
        callingPhone = null;

        displayInfo.text = action.getTelephoneId().toString();
    }

    private fun callTo(telephoneNumber: Int) {
        if (!action.onSendCall(telephoneNumber)) return;

        callingPhone = telephoneNumber;

        displayInfo.text = buildString {
            append(
                action
                    .getTelephoneId()
                    .toString()
            )
            append(" > ")
            append(callingPhone)
        };
    }

    fun onReceiveCall(telephoneId: Int) {
        action.onReceiveCall(telephoneId)

        callingPhone = telephoneId;

        displayInfo.text = buildString {
            append(
                action
                    .getTelephoneId()
                    .toString()
            )
            append(" < ")
            append(telephoneId)
        };
    }

    fun onOtherHangUp(hangUpFrom: Int) {
        callingPhone = null;

        action.onOtherHangUp(hangUpFrom);
        displayInfo.text = action.getTelephoneId().toString();
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
        fun getTelephoneId(): Int;
    }
}