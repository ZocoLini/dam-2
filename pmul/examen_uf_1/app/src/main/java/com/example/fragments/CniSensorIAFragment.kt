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
        override fun filterAlert(fragment: CniSensorIAFragment, text: String): Boolean
        {
            return false
        }

        override fun onAlert(fragment: CniSensorIAFragment, text: String)
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
            .doOnTextChanged { text, start, before, count ->
                println(text);

                if (cniSensorListener.filterAlert(view, text.toString()))
                {
                    cniSensorListener.onAlert(view, text.toString())
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
        fun filterAlert(fragment: CniSensorIAFragment, text: String): Boolean;
        fun onAlert(fragment: CniSensorIAFragment, text: String);
    }
}