package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.database.Database
import com.example.entities.Alert
import com.example.fragments.CniSensorIAFragment


class MainActivity : AppCompatActivity()
{
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>;
    private lateinit var pastAlertsListView: ListView;

    private lateinit var emailReceiverSensor: CniSensorIAFragment;
    private lateinit var emailSubjectSensor: CniSensorIAFragment;
    private lateinit var emailBodySensor: CniSensorIAFragment;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Database.init(this);

        resultLauncher = this.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult;

            populatePastAlertsListView();
        }

        val fragments = supportFragmentManager;

        emailReceiverSensor = fragments.findFragmentById(R.id.email_receiver) as CniSensorIAFragment;
        emailSubjectSensor = fragments.findFragmentById(R.id.email_subject) as CniSensorIAFragment;
        emailBodySensor = fragments.findFragmentById(R.id.email_body) as CniSensorIAFragment;

        findViewById<Button>(R.id.restart_db_button).setOnClickListener { resetDatabase() };

        pastAlertsListView = findViewById(R.id.past_alerts);
        populatePastAlertsListView();
    }

    override fun onStart()
    {
        super.onStart()

        emailReceiverSensor.setCniSensorListener(emailReceiverListener);
        emailSubjectSensor.setCniSensorListener(emailSubjectListener);
        emailBodySensor.setCniSensorListener(emailBodyListener);
    }

    fun openWarningsActivity(token: CniSensorIAFragment.Token)
    {
        WarningsActivity.showActivity(resultLauncher, this, token.token, token.context);
    }

    private fun populatePastAlertsListView()
    {
        val previousAlerts = Alert.selectAll();
        val adapter = AlertListViewAdapter(this, previousAlerts)
        pastAlertsListView.adapter = adapter;
    }

    private fun resetDatabase()
    {
        Database.resetTables();
        populatePastAlertsListView();
    }

    private val emailReceiverListener = object : CniSensorIAFragment.CniSensorListener
    {
        override fun findToken(fragment: CniSensorIAFragment, text: String):
                CniSensorIAFragment.Token?
        {
            if (text.endsWith("@ot.com"))
            {
                return CniSensorIAFragment.Token("@ot.com", text);
            }

            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: CniSensorIAFragment.Token)
        {
            openWarningsActivity(token);
        }

    }

    private val emailSubjectListener = object : CniSensorIAFragment.CniSensorListener
    {
        override fun findToken(fragment: CniSensorIAFragment, text: String):
                CniSensorIAFragment.Token?
        {
            if (text.contains("ascensor"))
            {
                return CniSensorIAFragment.Token("ascensor", text);
            } else if (text.contains("fuego"))
            {
                return CniSensorIAFragment.Token("fuego", text);
            }

            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: CniSensorIAFragment.Token)
        {
            openWarningsActivity(token);
        }
    }

    private val emailBodyListener = object : CniSensorIAFragment.CniSensorListener
    {
        override fun findToken(fragment: CniSensorIAFragment, text: String):
                CniSensorIAFragment.Token?
        {
            val iban: MatchResult? = Regex("[A-Za-z]{2}[0-9]{2}(-[0-9]){5}").find(text);
            val nif: MatchResult? = Regex("[0-9]{8}[A-Za-z]").find(text);

            if (iban != null)
            {
                return CniSensorIAFragment.Token(iban.value, text);
            } else if (nif != null)
            {
                return CniSensorIAFragment.Token(nif.value, text);
            }

            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: CniSensorIAFragment.Token)
        {
            openWarningsActivity(token);
        }
    }

    class AlertListViewAdapter(context: Context, private val alerts: List<Alert>) : ArrayAdapter<Alert>(
        context,
        R.layout.alert_list_view_row,
        alerts
    )
    {
        private class ViewHolder
        {
            lateinit var textView: TextView;
            lateinit var imageView: ImageView;
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
        {
            val viewHolder: ViewHolder;

            val alert = alerts[position];

            var view: View? = convertView;

            if (view == null)
            {
                view = LayoutInflater
                    .from(context)
                    .inflate(R.layout.alert_list_view_row, null);
                viewHolder = ViewHolder();
                viewHolder.textView = view.findViewById(R.id.text_view);
                viewHolder.imageView = view.findViewById(R.id.image_view);
                view.tag = viewHolder;
            }
            else
            {
                viewHolder = view.tag as ViewHolder;
            }

            viewHolder.textView.text = alert.context;
            viewHolder.imageView.setImageResource(
                if (alert.accepted) R.drawable.dedo_abajo
                else R.drawable.dedo_arriba
            );

            return view!!;
        }
    }
}