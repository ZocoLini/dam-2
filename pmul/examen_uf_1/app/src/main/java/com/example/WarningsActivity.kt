package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.database.Database
import com.example.entities.Alert

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

        val detectedToken = intent.getStringExtra("detectedToken")!!;
        val detectedTokenContext = intent.getStringExtra("detectedTokenContext")!!;

        this.findViewById<TextView>(R.id.detected_token_text_view).text = detectedToken;
        this.findViewById<TextView>(R.id.token_context_text_view).text = detectedTokenContext;

        this
            .findViewById<Button>(R.id.yes_button)
            .setOnClickListener {
                Database.connect { conn ->
                    Alert.insert(
                        Alert(
                            detectedToken, detectedTokenContext,
                            true
                        ), conn
                    );
                }

                closeActivity();
            }

        this
            .findViewById<Button>(R.id.no_button)
            .setOnClickListener {
                Database.connect { conn ->
                    Alert.insert(
                        Alert(
                            detectedToken, detectedTokenContext,
                            false
                        ), conn
                    );
                }

                closeActivity();
            }
    }

    private fun closeActivity()
    {
        setResult(RESULT_OK);
        finish();
    }

    companion object
    {
        @JvmStatic
        fun showActivity(
            resultLauncher: ActivityResultLauncher<Intent>, context:
            Context, detectedToken: String,
            detectedTokenContext: String
        )
        {
            resultLauncher.launch(Intent(context, WarningsActivity::class.java).apply {
                putExtra("detectedToken", detectedToken);
                putExtra("detectedTokenContext", detectedTokenContext);
            });
        }
    }
}