package com.example.aplicacion_elecciones_kotlin.database.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aplicacion_elecciones_kotlin.database.Database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsuarioDAO
{
    public static final String TABLE_DEFINITION = "create table Usuario " +
            "(id integer primary key autoincrement, " +
            "nif text not null unique, " +
            "password text not null, " +
            "votaciones_realizadas boolean not null)";

    public static Usuario validateUser(String nif, String password)
    {
        final Usuario[] result = {null};
        if (!validateNIFFormat(nif)) return null;
        if (!validatePassword(password)) return null;

        String inputPasswordHash = generateHash(password);

        Database.INSTANCE.connect(db ->
        {
            try (Cursor cursor = db.rawQuery(
                    "SELECT id, nif, password, votaciones_realizadas FROM Usuario WHERE nif = ?",
                    new String[]{nif})
            )
            {
                if (cursor.moveToNext())
                {
                    String hash = cursor.getString(2);
                    if (!hash.equals(inputPasswordHash)) return;
                    result[0] = new Usuario(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            Boolean.parseBoolean(cursor.getString(3))
                    );
                }
            }
        });

        return result[0];
    }

    public static boolean insert(Usuario usuario)
    {
        if (usuario == null) throw new IllegalArgumentException("El usuario no puede ser nulo");
        if (usuario.getId() != -1) throw new IllegalArgumentException("El usuario ya existe");

        boolean[] result = {false};

        Database.INSTANCE.connect(session -> result[0] = insert(usuario, session));

        return result[0];
    }

    public static boolean insert(Usuario usuario, SQLiteDatabase session)
    {
        session.execSQL(
                "insert into Usuario (nif, password, votaciones_realizadas) " +
                        "values (?, ?, ?)",
                new Object[]{
                        usuario.getNif(),
                        generateHash(usuario.getPassword()),
                        String.valueOf(usuario.isVotacionesRealizadas())
                }
        );

        return true;
    }

    static public boolean validateNIFFormat(String nif)
    {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        Pattern pattern = Pattern.compile("(\\d{8})([" + letras + "])", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nif);

        if (!matcher.matches()) return false;

        int numero = Integer.parseInt(matcher.group(1));
        String letra = matcher.group(2);
        char letraCorrecta = letras.charAt(numero % 23);
        return letra.toUpperCase().charAt(0) == letraCorrecta;
    }

    static public boolean validatePassword(String password)
    {
        final int MIN_LENGTH_PASSWORD = 5;
        return password.length() >= MIN_LENGTH_PASSWORD;
        //TODO: Verificar la existencia de símbolos peligrosos para la inyección SQL
    }

    // Gracias a  http://www.coderblog.de/producing-the-same-sha-512-hash-in-java-and-php/
    static public String generateHash(String texto)
    {
        MessageDigest md;
        byte[] hash;
        try
        {
            md = MessageDigest.getInstance("SHA-512");
            hash = md.digest(texto.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e)
        {
            return "";
        }

        // Convertir a hexadecimal
        StringBuffer sb = new StringBuffer();
        for (byte b : hash) sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return sb.toString();
    }
}
