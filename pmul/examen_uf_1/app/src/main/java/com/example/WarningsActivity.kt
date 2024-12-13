package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WarningsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_warnings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val detectedToken = intent.getStringExtra("detectedToken");
        val detectedTokenContext = intent.getStringExtra("detectedTokenContext");

        this.findViewById<TextView>(R.id.detected_token_text_view).text = detectedToken;
        this.findViewById<TextView>(R.id.token_context_text_view).text = detectedTokenContext;

        this.findViewById<Button>(R.id.yes_button).setOnClickListener {
            closeActivity();
        }

        this.findViewById<Button>(R.id.no_button).setOnClickListener {
            closeActivity();
        }
    }

    private fun closeActivity()
    {
        finish();
    }

    companion object
    {
        @JvmStatic
        fun showActivity(context: Context, detectedToken: String, detectedTokenContext: String)
        {
            val intent = Intent(context, WarningsActivity::class.java)

            intent.putExtra("detectedToken", detectedToken)
            intent.putExtra("detectedTokenContext", detectedTokenContext)

            context.startActivity(intent)
        }
    }
}