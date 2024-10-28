package com.example.aplicacion_elecciones;

import com.example.aplicacion_elecciones.database.entities.Usuario;
import com.example.aplicacion_elecciones.database.entities.UsuarioDAO;

public class AccountManager
{
    private static AccountManager instance;

    private Usuario currentUser;

    private AccountManager()
    {
    }

    public static AccountManager getInstance()
    {
        if (instance == null)
        {
            instance = new AccountManager();
        }

        return instance;
    }

    public boolean login(String nif, String password)
    {
        currentUser = UsuarioDAO.validateUser(nif, password);

        return currentUser != null;
    }

    public boolean logout()
    {
        currentUser = null;
        return true;
    }

    public boolean isLogged()
    {
        return currentUser != null;
    }

    public Usuario getCurrentUser()
    {
        return currentUser;
    }
}
