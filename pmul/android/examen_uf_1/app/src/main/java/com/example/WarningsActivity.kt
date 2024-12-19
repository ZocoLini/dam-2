package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WarningsActivity : AppCompatActivity()
{
    private lateinit var disableWarningCheckbox: CheckBox;

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
        val org = intent.getStringExtra("org")!!;
        val control = intent.getStringExtra("control")!!;

        this.findViewById<TextView>(R.id.detected_token_text_view).text =
            "Token: $detectedToken (Control: $control)";
        this.findViewById<TextView>(R.id.token_context_text_view).text = detectedTokenContext;
        this.findViewById<TextView>(R.id.org_text_view).text = org;

        this
            .findViewById<Button>(R.id.yes_button)
            .setOnClickListener {
                WarningsManager.saveWaningAsAlert(true);
                terminateActivity();
            }

        this
            .findViewById<Button>(R.id.no_button)
            .setOnClickListener {
                WarningsManager.saveWaningAsAlert(false);
                terminateActivity();
            }

        disableWarningCheckbox = findViewById(R.id.dont_repeat_warning_checkbox);
    }

    private fun terminateActivity()
    {
        if (disableWarningCheckbox.isChecked)
        {
            WarningsManager.disableWarning();
        }

        setResult(RESULT_OK);
        finish();
    }

    companion object
    {
        @JvmStatic
        fun showActivity(
            resultLauncher: ActivityResultLauncher<Intent>, context: Context
        )
        {
            val warning = WarningsManager.getActualWarning()!!;

            resultLauncher.launch(Intent(context, WarningsActivity::class.java).apply {
                putExtra("detectedToken", warning.token);
                putExtra("detectedTokenContext", warning.context);
                putExtra("org", warning.orgName);
                putExtra("control", warning.controlName);
            });
        }
    }

}