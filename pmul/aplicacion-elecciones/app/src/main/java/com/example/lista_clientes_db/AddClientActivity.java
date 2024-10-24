package com.example.lista_clientes_db;

import android.accounts.Account;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lista_clientes_db.database.entities.Client;
import com.example.lista_clientes_db.database.entities.ClientDAO;
import com.example.lista_clientes_db.database.entities.Provincia;
import com.example.lista_clientes_db.database.entities.ProvinciaDAO;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

public class AddClientActivity extends AppCompatActivity
{
    public static final String ACCOUNT_ID_EXTRA = "account-id";
    private Optional<Client> client = Optional.empty();

    private TextView sectionTitle;
    private EditText clientName;
    private EditText clientNif;
    private CheckBox clientVip;
    private Spinner clientProvincia;
    private Button mainButton;

    // TODO: Mapear las provincias private HashMap<>

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        sectionTitle = findViewById(R.id.section_title);
        clientName = findViewById(R.id.client_name);
        clientNif = findViewById(R.id.client_nif);
        clientVip = findViewById(R.id.client_vip);
        clientProvincia = findViewById(R.id.cliente_provincia);
        mainButton = findViewById(R.id.main_action_button);

        client = ClientDAO.select(getIntent().getIntExtra(ACCOUNT_ID_EXTRA, -1));

        mainButton.setOnClickListener(v -> saveClient());
        initialize();
    }

    private void initialize()
    {
        boolean clientPresent = client.isPresent();

        Provincia[] provincias = ProvinciaDAO.selectAll("order by id").toArray(new Provincia[0]);
        SpinnerAdapter adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                provincias
        );
        clientProvincia.setAdapter(adapter);

        if (clientPresent)
        {
            sectionTitle.setText("Edit client");
            mainButton.setText("Save");

            Client client = this.client.get();

            clientName.setText(client.getName());
            clientNif.setText(client.getNif());
            clientVip.setChecked(client.isVip());

            int i = 0;

            for (i = 0; i < provincias.length; i++)
            {
                if (provincias[i].getId() == client.getProvincia().getId()) break;
            }

            clientProvincia.setSelection(i);
        }
        else
        {
            sectionTitle.setText("Add client");
            mainButton.setText("Add");

            clientVip.setChecked(false);
            clientProvincia.setSelection(0);
        }
    }

    private void saveClient()
    {
        if (clientName.getText().toString().isBlank())
        {
            clientName.setError("Name is required");
            return;
        }
        
        if (clientNif.getText().toString().isBlank())
        {
            clientNif.setError("NIF is required");
            return;
        }

        int id = client.map(Client::getId).orElse(-1);

        Client newClient = new Client(
                id,
                clientName.getText().toString(),
                clientNif.getText().toString(),
                clientVip.isChecked(),
                (Provincia) clientProvincia.getSelectedItem()
        );

        if (id == -1)
        {
            ClientDAO.insert(newClient);
        }
        else
        {
            ClientDAO.update(newClient);
        }

        setResult(RESULT_OK);
        finish();
    }
}
