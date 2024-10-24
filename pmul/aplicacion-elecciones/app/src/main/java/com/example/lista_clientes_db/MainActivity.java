package com.example.lista_clientes_db;

import android.content.Intent;
import android.os.Bundle;

import com.example.lista_clientes_db.database.Database;
import com.example.lista_clientes_db.database.entities.Client;
import com.example.lista_clientes_db.database.entities.ClientDAO;
import com.example.lista_clientes_db.database.entities.Provincia;
import com.example.lista_clientes_db.database.entities.ProvinciaDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lista_clientes_db.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Comparator;

public class MainActivity extends AppCompatActivity
{
    private ActivityResultLauncher<Intent> resultLauncher;

    private ActivityMainBinding binding;

    private ListView listView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->
        {
            if (result.getResultCode() != RESULT_OK) return;

            loadClientsList();
        });

        listView = findViewById(R.id.client_list);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> resultLauncher.launch(new Intent(this, AddClientActivity.class)));

        loadClientsList();

        listView.setOnItemClickListener((parent, view, position, id) ->
        {
            Client client = (Client) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, AddClientActivity.class);
            intent.putExtra(AddClientActivity.ACCOUNT_ID_EXTRA, client.getId());
            resultLauncher.launch(intent);
        });
    }

    private void loadClientsList()
    {
        new Thread(() ->
        {
            Client[] clients = ClientDAO.selectAll().stream().sorted(Comparator.comparingInt(Client::getId)).toArray(Client[]::new);
            ListAdapter adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    clients
            );

            runOnUiThread(() -> listView.setAdapter(adapter));
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}