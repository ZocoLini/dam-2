package com.example.examen_tips.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import com.example.R


class DynamicFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dynamic_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun createDynamicFragment() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
//        for (i in 1..getNumFrgs()) {
//            val frg: Frg = Frg()
//            val fcv = FragmentContainerView(this)
//            fcv.id = View.generateViewId()
//            llContenedorFrgs.addView((fcv))
//            ft.add(fcv.id, frg)
//        }

        val frg = DynamicFragment()
        val fcv = FragmentContainerView(this)
        fcv.id = View.generateViewId()
        findViewById<LinearLayout>(R.id.main).addView((fcv))
        ft.add(fcv.id, frg)

        ft.commit()
    }
}