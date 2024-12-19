package es.ies.chandomonte.juegodados.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import es.ies.chandomonte.juegodados.R

class DadoFragment : Fragment()
{
    private val DEVELOPMENT = true;

    private lateinit var buttonDado: Button;
    private lateinit var nextNumberField: EditText;
    private lateinit var root: ConstraintLayout;

    private var minValue = 1;
    private var maxValue = 6;
    private var resultListener: ResultListener = object : ResultListener
    {
        override fun onResultListener(fragment: DadoFragment, result: Int)
        {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dado, container, false)

        root = view.findViewById(R.id.root);
        buttonDado = view.findViewById(R.id.dado_button);
        nextNumberField = view.findViewById(R.id.next_number);

        if (!DEVELOPMENT)
        {
            root.removeView(nextNumberField);
        }

        buttonDado.setOnClickListener { onButtonClick() }

        return view;
    }

    fun setRange(minValue: Int, maxValue: Int)
    {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    fun setOnResultListener(resultListener: ResultListener)
    {
        this.resultListener = resultListener;
    }

    private fun onButtonClick()
    {
        val result: Int = if (DEVELOPMENT)
        {
            try
            {
                nextNumberField.text
                    .toString()
                    .toInt()
            }
            catch (e: NumberFormatException)
            {
                nextNumberField.setText("");
                generateRandomNumber()
            }
        }
        else
        {
            generateRandomNumber()
        }

        buttonDado.text = result.toString()

        resultListener.onResultListener(this, result)
    }

    private fun generateRandomNumber(): Int
    {
        return (Math.random() * maxValue).toInt() + minValue
    }

    companion object
    {
        @JvmStatic
        fun newInstance() = DadoFragment()
    }

    interface ResultListener
    {
        public fun onResultListener(fragment: DadoFragment, result: Int);
    }
}