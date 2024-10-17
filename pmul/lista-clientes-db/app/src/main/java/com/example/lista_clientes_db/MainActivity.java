package com.example.lista_clientes_db;

import android.os.Bundle;

import com.example.lista_clientes_db.database.Database;
import com.example.lista_clientes_db.database.entities.Client;
import com.example.lista_clientes_db.database.entities.ClientDAO;
import com.google.android.material.snackbar.Snackbar;

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

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding binding;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Database.initialize(this);

        Client clientePrueba = new Client(-1, "Cliente Prueba", "12345678Z", true, null);

        ClientDAO.insert(clientePrueba);

        listView = findViewById(R.id.client_list);

        Client[] clients = ClientDAO.selectAll().toArray(new Client[0]);

        ListAdapter adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                clients
        );

        listView.setAdapter(adapter);
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