package com.example.lista_clientes_db.database.entities;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.lista_clientes_db.database.Database;

import java.util.ArrayList;
import java.util.List;

public class ClientDAO
{
    public static final String TABLE_DEFINITION = "create table Client " +
            "(id integer primary key autoincrement, name text, nif text, vip boolean, provincia integer," +
            "foreign key (provincia) references Provincia(id))";

    public static boolean insert(Client client)
    {
        Database.getInstance().connect(db ->
        {
            db.execSQL("insert into client (name, nif, vip, provincia) values (?, ?, ?, ?)",
                    new Object[]{client.getName(), client.getNif(), client.isVip(), client.getProvincia().getId()});
        });

        return true;
    }

    public static Client select(int id)
    {
        return null;
    }

    public static List<Client> selectAll()
    {
        List<Client> clients = new ArrayList<>();

        Database.getInstance().connect(db -> {
            Cursor cursor = db.rawQuery("select * from client", null);

            while (cursor.moveToNext())
            {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String nif = cursor.getString(cursor.getColumnIndex("nif"));
                @SuppressLint("Range") boolean vip = cursor.getInt(cursor.getColumnIndex("vip")) == 1;
                @SuppressLint("Range") int provinciaId = cursor.getInt(cursor.getColumnIndex("provincia"));

                Provincia provincia = ProvinciaDAO.select(provinciaId);

                clients.add(new Client(id, name, nif, vip, provincia));
            }

            cursor.close();
        });

        return clients;
    }
}
