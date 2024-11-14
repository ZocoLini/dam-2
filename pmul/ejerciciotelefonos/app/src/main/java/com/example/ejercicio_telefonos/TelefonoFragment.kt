package com.example.ejercicio_telefonos

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 * Use the [TelefonoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TelefonoFragment : Fragment()
{
    private lateinit var action: TelefonoFragmentActions;
    private lateinit var editText: EditText;
    private lateinit var displayInfo: TextView;
    private var callingPhone: Int? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_telefono, container, false)

        view.findViewById<Button>(R.id.ib_telefono)!!
            .setOnClickListener { _ ->
                onAction();
            }

        editText = view.findViewById(R.id.et_telefono)!!;
        displayInfo = view.findViewById(R.id.tv_info)!!;

        return view;
    }

    fun setActions(actions: TelefonoFragmentActions)
    {
        action = actions;

        displayInfo.text = action.getTelefonoId().toString();
    }

    fun isAvailable(): Boolean
    {
        return callingPhone == null;
    }

    private fun onAction()
    {
        if (callingPhone != null)
        {
            action.onTelefonoColgar(callingPhone!!, this);
            callingPhone = null;

            displayInfo.text = action.getTelefonoId().toString();
        }
        else if (action.onTelefonoSendCall(editText.text.toString().toInt(), this))
        {
            callingPhone = editText.text.toString().toInt();

            displayInfo.text = buildString {
                append(
                    action
                        .getTelefonoId()
                        .toString()
                )
                append(" > ")
                append(callingPhone)
            };
        }
    }

    public fun onReceiveCall(telefonoId: Int)
    {
        action.onTelefonoReceiveCall(telefonoId, this)
        action.onTelefonoContestar(telefonoId, this)

        callingPhone = telefonoId;

        displayInfo.text = buildString {
            append(
                action
                    .getTelefonoId()
                    .toString()
            )
            append(" < ")
            append(telefonoId)
        };
    }

    companion object
    {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TelefonoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            TelefonoFragment().apply {}
    }

    public interface TelefonoFragmentActions
    {
        fun onTelefonoReceiveCall(telefonoId: Int, telefonoFragment: TelefonoFragment);
        fun onTelefonoSendCall(telefonoId: Int, telefonoFragment: TelefonoFragment): Boolean;
        fun onTelefonoContestar(telefonoId: Int, telefonoFragment: TelefonoFragment);
        fun onTelefonoColgar(telefonoId: Int, telefonoFragment: TelefonoFragment);
        fun getTelefonoId(): Int;
    }
}