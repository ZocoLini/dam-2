package com.example.lista_clientes_db.database.entities;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.lista_clientes_db.ObjWrapper;
import com.example.lista_clientes_db.database.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static Optional<Client> select(int id)
    {
        ObjWrapper<Client> client = new ObjWrapper<>();

        Database.getInstance().connect(db ->
        {
            try (Cursor cursor = db.rawQuery("select * from client where id = ?",
                    new String[]{String.valueOf(id)}))
            {
                if (cursor.moveToNext())
                {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String nif = cursor.getString(cursor.getColumnIndexOrThrow("nif"));
                    boolean vip = cursor.getInt(cursor.getColumnIndexOrThrow("vip")) == 1;
                    int provinciaId = cursor.getInt(cursor.getColumnIndexOrThrow("provincia"));

                    Provincia provincia = ProvinciaDAO.select(provinciaId).get();

                    client.setObj(new Client(id, name, nif, vip, provincia));
                }
            }
        });

        return client.intoOptional();
    }

    public static List<Client> selectAll()
    {
        List<Client> clients = new ArrayList<>();

        Database.getInstance().connect(db -> {
            try (Cursor cursor = db.rawQuery("select id, name, nif, vip, provincia from client", null))
            {
                while (cursor.moveToNext())
                {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String nif = cursor.getString(cursor.getColumnIndexOrThrow("nif"));
                    boolean vip = cursor.getInt(cursor.getColumnIndexOrThrow("vip")) == 1;
                    int provinciaId = cursor.getInt(cursor.getColumnIndexOrThrow("provincia"));

                    Provincia provincia = ProvinciaDAO.select(provinciaId).get();

                    clients.add(new Client(id, name, nif, vip, provincia));
                }
            }
        });

        return clients;
    }

    public static boolean update(Client newClient)
    {
        Database.getInstance().connect(db ->
        {
            db.execSQL("update client set name = ?, nif = ?, vip = ?, provincia = ? where id = ?",
                    new Object[]{newClient.getName(), newClient.getNif(), newClient.isVip(),
                            newClient.getProvincia().getId(), newClient.getId()});
        });

        return true;
    }
}
