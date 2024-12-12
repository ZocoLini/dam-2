package com.example.examen_tips.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.R

class ResultLauncherActivity : AppCompatActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result_launcher)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultLauncher = this.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult;

            println("ResultLauncherActivity: ${result.data?.extras?.getString("key")}");
        }

        println("Registrado el resultLauncher");

        val intent = Intent(this, CallableActivity::class.java)
        intent.putExtra("key", "value")
        resultLauncher.launch(intent)
    }
}