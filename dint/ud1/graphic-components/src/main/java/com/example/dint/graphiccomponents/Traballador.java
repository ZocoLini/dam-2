package com.example.dint.graphiccomponents;

public record Traballador(String dni, String nome, String apelido1,
                          String apelido2, String provincia, String profesion)
{
    public String generateDetails()
    {
        return "DNI: " + dni + "\n" +
               "Nome: " + nome + "\n" +
               "Apelidos: " + apelido1 + " " + apelido2 + "\n" +
               "Provincia: " + provincia + "\n" +
               "Profesión: " + profesion;
    }
}
