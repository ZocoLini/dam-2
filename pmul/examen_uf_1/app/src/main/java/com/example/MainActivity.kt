package com.example

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
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

        emailReceiverSensor =
            fragments.findFragmentById(R.id.email_receiver) as CniSensorIAFragment;
        emailSubjectSensor = fragments.findFragmentById(R.id.email_subject) as CniSensorIAFragment;
        emailBodySensor = fragments.findFragmentById(R.id.email_body) as CniSensorIAFragment;

        findViewById<Button>(R.id.restart_db_button).setOnClickListener { resetDatabase() };

        pastAlertsListView = findViewById(R.id.past_alerts);
        populatePastAlertsListView();
    }

    override fun onStart()
    {
        super.onStart()

        emailReceiverSensor.addCniSensorListener(emailReceiverListener);
        emailSubjectSensor.addCniSensorListener(emailSubjectListener1);
        emailSubjectSensor.addCniSensorListener(emailSubjectListener2);
        emailBodySensor.addCniSensorListener(emailBodyListener);
    }

    fun openWarningsActivity(token: CniSensorIAFragment.Token, org: String)
    {
        WarningsManager.setWarning(
            WarningsManager.Warning(
                token.token, token.context, org, token.controlFound
            ), this
        );
        WarningsActivity.showActivity(resultLauncher, this);
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

        Toast.makeText(this, getStringResource(R.string.db_reseted), Toast.LENGTH_SHORT).show();
    }

    private fun getStringResource(resId: Int): String
    {
        return this.resources.getString(resId);
    }

    private val emailReceiverListener = object : CniSensorIAFragment.CniSensorListener
    {
        override fun findToken(fragment: CniSensorIAFragment, text: String):
                CniSensorIAFragment.Token?
        {
            if (text.contains("@ot.com"))
            {
                return CniSensorIAFragment.Token("@ot.com", text, "Email Receiver");
            }

            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: CniSensorIAFragment.Token)
        {
            openWarningsActivity(token, getStringResource(R.string.police));
        }

        override fun isDeactivated(): Boolean
        {
            return false;
        }

    }

    private val emailSubjectListener1 = object : CniSensorIAFragment.CniSensorListener
    {
        override fun findToken(fragment: CniSensorIAFragment, text: String):
                CniSensorIAFragment.Token?
        {
            if (text.contains("ascensor"))
            {
                return CniSensorIAFragment.Token("ascensor", text, "Email Subject");
            }

            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: CniSensorIAFragment.Token)
        {
            openWarningsActivity(token, getStringResource(R.string.police));
        }

        override fun isDeactivated(): Boolean
        {
            return false;
        }
    }

    private val emailSubjectListener2 = object : CniSensorIAFragment.CniSensorListener
    {
        override fun findToken(fragment: CniSensorIAFragment, text: String): CniSensorIAFragment.Token?
        {
            if (text.contains("fuego"))
            {
                return CniSensorIAFragment.Token("fuego", text, "Email Subject");
            }

            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: CniSensorIAFragment.Token)
        {
            openWarningsActivity(token, getStringResource(R.string.fireman));
        }

        override fun isDeactivated(): Boolean
        {
            return false;
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
                return CniSensorIAFragment.Token(iban.value, text, "Email Body");
            } else if (nif != null)
            {
                return CniSensorIAFragment.Token(nif.value, text, "Email Body");
            }

            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: CniSensorIAFragment.Token)
        {
            openWarningsActivity(token, getStringResource(R.string.police));
        }

        override fun isDeactivated(): Boolean
        {
            return false;
        }
    }

    class AlertListViewAdapter(context: Context, private val alerts: List<Alert>) :
        ArrayAdapter<Alert>(
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

            // Crear una cadena usando elementos de string.sml
            viewHolder.textView.text = context.resources.getString(
                R.string.alert_list_view_row_text,
                alert.token, alert.orgName
            );

            viewHolder.imageView.setImageResource(
                if (alert.accepted) R.drawable.dedo_abajo
                else R.drawable.dedo_arriba
            );

            return view!!;
        }
    }
}