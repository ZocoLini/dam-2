package com.example.lista_clientes_db;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lista_clientes_db.database.entities.Client;

public class ClientAdapter extends ArrayAdapter<Client>
{
    public ClientAdapter(@NonNull Context context, @NonNull Client[] objects)
    {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = super.getView(position, convertView, parent);

        if (getItem(position) != null && getItem(position).isVip())
        {
            TextView textLabel = view.findViewById(android.R.id.text1);

            textLabel.setBackgroundColor(Color.RED);
            textLabel.setTextColor(Color.WHITE);
        }

        return view;
    }
}
