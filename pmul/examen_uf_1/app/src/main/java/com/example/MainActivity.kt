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
import com.example.orgs.Fireman
import com.example.orgs.Org
import com.example.orgs.Police


class MainActivity : AppCompatActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var pastAlertsListView: ListView;
    private lateinit var emailReceiverSensor: CniSensorIAFragment;
    private lateinit var emailSubjectSensor: CniSensorIAFragment;
    private lateinit var emailBodySensor: CniSensorIAFragment;

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onStart() {
        super.onStart()

        emailReceiverSensor.addCniSensorListener(emailReceiverListener);
        emailSubjectSensor.addCniSensorListener(emailSubjectListener);
        emailBodySensor.addCniSensorListener(emailBodyListener);
    }

    fun openWarningsActivity(token: CniSensorIAFragment.Token, org: ArrayList<String>, warningOrigin: CniSensorListener) {
        WarningsManager.showWarning(
            WarningsManager.Warning(
                token.token, token.context, org.joinToString(), token.controlFound
            ), warningOrigin
        );
        WarningsActivity.showActivity(resultLauncher, this);
    }

    private fun populatePastAlertsListView() {
        val previousAlerts = Alert.selectAll();
        val adapter = AlertListViewAdapter(this, previousAlerts)
        pastAlertsListView.adapter = adapter;
    }

    private fun resetDatabase() {
        Database.resetTables();
        populatePastAlertsListView();

        Toast.makeText(this, getStringResource(R.string.db_reseted), Toast.LENGTH_SHORT).show();
    }

    private fun getStringResource(resId: Int): String {
        return this.resources.getString(resId);
    }

    private val emailReceiverListener = object : CniSensorListener() {
        override fun getOrgsListening(): ArrayList<Org> {
            return ArrayList<Org>().apply {
                add(Police());
            }
        }

        override fun getAlertTokensMatchers(): ArrayList<Regex> {
            return ArrayList<Regex>().apply {
                add(Regex(".*(?<token>@ot.com).*"))
            }
        }

        override fun getMainActivity(): MainActivity {
            return this@MainActivity;
        }

        override fun getUIController(): String {
            return "Email Receiver";
        }
    }

    private val emailSubjectListener = object : CniSensorListener() {
        override fun getOrgsListening(): ArrayList<Org> {
            return ArrayList<Org>().apply {
                add(Police());
                add(Fireman());
            }
        }

        override fun getAlertTokensMatchers(): ArrayList<Regex> {
            return ArrayList<Regex>().apply {
                add(Regex("^(?!seguro ).*(?<token>ascensor).*"))
                add(Regex(".*(?<token>fuego).*"))
            }
        }

        override fun getMainActivity(): MainActivity {
            return this@MainActivity;
        }

        override fun getUIController(): String {
            return "Email Subject";
        }
    }

    private val emailBodyListener = object : CniSensorListener() {
        override fun getOrgsListening(): ArrayList<Org> {
            return ArrayList<Org>().apply {
                add(Police());
            }
        }

        override fun getAlertTokensMatchers(): ArrayList<Regex> {
            return ArrayList<Regex>().apply {
                add(Regex("(?<token>[A-Za-z]{2}[0-9]{2}(-[0-9]){5})"))
                add(Regex("(?<token>[0-9]{8}[A-Za-z])"))
            }
        }

        override fun getMainActivity(): MainActivity {
            return this@MainActivity;
        }

        override fun getUIController(): String {
            return "Email Body";
        }
    }

    class AlertListViewAdapter(context: Context, private val alerts: List<Alert>) :
        ArrayAdapter<Alert>(
            context,
            R.layout.alert_list_view_row,
            alerts
        ) {
        private class ViewHolder {
            lateinit var textView: TextView;
            lateinit var imageView: ImageView;
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val viewHolder: ViewHolder;

            val alert = alerts[position];

            var view: View? = convertView;

            if (view == null) {
                view = LayoutInflater
                    .from(context)
                    .inflate(R.layout.alert_list_view_row, null);
                viewHolder = ViewHolder();
                viewHolder.textView = view.findViewById(R.id.text_view);
                viewHolder.imageView = view.findViewById(R.id.image_view);
                view.tag = viewHolder;
            } else {
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

    abstract class CniSensorListener : CniSensorIAFragment.CniSensorListener {
        private var deactivated: Boolean = false;

        abstract fun getOrgsListening(): ArrayList<Org>;
        abstract fun getAlertTokensMatchers(): ArrayList<Regex>;
        abstract fun getMainActivity(): MainActivity;
        abstract fun getUIController(): String;

        fun deactivate() {
            deactivated = true;
        }

        override fun findToken(
            fragment: CniSensorIAFragment,
            text: String
        ): CniSensorIAFragment.Token? {
            for (regex in getAlertTokensMatchers()) {
                val match: MatchResult = regex.find(text) ?: continue;

                val token = match.groups["token"]!!.value;

                return CniSensorIAFragment.Token(token, text, getUIController());
            }

            return null;
        }

        override fun onAlert(fragment: CniSensorIAFragment, token: CniSensorIAFragment.Token) {
            getMainActivity().openWarningsActivity(
                token,
                getOrgsListening().map { getMainActivity().getStringResource(it.getOrgName()) }.toCollection(ArrayList()),
                this
            );
        }

        override fun isDeactivated(): Boolean {
            return deactivated;
        }

    }
}