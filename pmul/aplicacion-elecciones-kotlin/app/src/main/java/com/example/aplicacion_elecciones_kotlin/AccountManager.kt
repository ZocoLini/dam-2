package com.example.aplicacion_elecciones_kotlin

import com.example.aplicacion_elecciones_kotlin.database.entities.Usuario
import com.example.aplicacion_elecciones_kotlin.database.entities.UsuarioDAO

object AccountManager
{
    private var currentUser: Usuario? = null

    fun login(nif: String, password: String): Boolean
    {
        currentUser = UsuarioDAO.validateUser(nif, password)

        return currentUser != null
    }

    fun logout(): Boolean
    {
        currentUser = null
        return true
    }

    val isLogged: Boolean
        get() = currentUser != null
}
