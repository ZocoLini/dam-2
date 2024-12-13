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
    private var cniSensorListener: CniSensorListener = object : CniSensorListener
    {
        override fun findToken(fragment: CniSensorIAFragment, text: String): Token?
        {
            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: Token)
        {
        }
    }

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
                val foundToken = cniSensorListener.findToken(this, text.toString())

                if (foundToken != null)
                {
                    cniSensorListener.onAlert(this, foundToken)
                }
            }

        return view
    }

    fun setCniSensorListener(cniSensorListener: CniSensorListener)
    {
        this.cniSensorListener = cniSensorListener
    }

    interface CniSensorListener
    {
        fun findToken(fragment: CniSensorIAFragment, text: String): Token?;
        fun onAlert(fragment: CniSensorIAFragment, token: Token);
    }

    class Token(val token: String, val context: String)
}