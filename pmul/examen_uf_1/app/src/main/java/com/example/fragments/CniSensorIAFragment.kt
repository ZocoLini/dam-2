package com.example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.R

class CniSensorIAFragment : Fragment()
{
    private var cniSensorListeners = ArrayList<CniSensorListener>();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cni_sensor_i_a, container, false)

        view
            .findViewById<EditText>(R.id.edit_text)
            .doOnTextChanged { text, _, _, _ ->
                if (text == null) return@doOnTextChanged;

                for (listener in cniSensorListeners)
                {
                    if (listener.isDeactivated()) continue;

                    val token = listener.findToken(this, text.toString())

                    if (token != null)
                    {
                        listener.onAlert(this, token);
                    }
                }
            }

        return view
    }

    fun addCniSensorListener(cniSensorListener: CniSensorListener)
    {
        cniSensorListeners.add(cniSensorListener);
    }

    interface CniSensorListener
    {
        fun findToken(fragment: CniSensorIAFragment, text: String): Token?;
        fun onAlert(fragment: CniSensorIAFragment, token: Token);
        fun isDeactivated(): Boolean;
    }

    class Token(val token: String, val context: String, val controlFound: String)
}