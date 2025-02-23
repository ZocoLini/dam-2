/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lebastudios.aplicacioncompleja.database.entities;

import org.lebastudios.aplicacioncompleja.database.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DAM2
 */
public class ConsultasSQLPerrucaria
{

    // Recuperar citas dunha data
    public static List<CitaPerrucaria> recuperarTodaAsCitasDunhaData(LocalDate data)
    {
        List<CitaPerrucaria> resultado = new ArrayList<>();

        Database.getInstance().connect(session ->
        {
            try
            {
                PreparedStatement stmt = session.prepareStatement("select hora from citasperrucaria where data = ?");
                stmt.setDate(1, Date.valueOf(data));

                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    CitaPerrucaria c = new CitaPerrucaria(rs.getInt(1));
                    resultado.add(c);
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException("Error ao recuperar as citas dunha data", e);
            }
        });

        return resultado;
    }

    // Graba unha cita
    public static boolean inserirCitaPerrucaria(CitaPerrucaria c)
    {
        boolean[] resultado = {true};
        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta = "insert into citasperrucaria (chip, data, hora) VALUES ('" + c.getCodCan() + "','" +
                        c.getData() + "'," + c.getHora() + ")";
                stmt.executeUpdate(consulta);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                resultado[0] = false;
            }
        });

        return resultado[0];
    }

    // Recuperar todas as citas
    public static List<ListadoPerrucaria> recuperarTodaAsCitasDePerrucaria()
    {
        List<ListadoPerrucaria> resultado = new ArrayList<>();

        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta =
                        "SELECT citasperrucaria.codCita, propietarios.nome, propietarios.ap1, propietarios.ap2, cans.nome, citasperrucaria.data, " +
                                "citasperrucaria.hora, citasperrucaria.chip FROM citasperrucaria, cans, propietarios " +
                                "where citasperrucaria.chip=cans.chip and cans.dnipropietario=propietarios.dni order by citasperrucaria.data";
                ResultSet rs = stmt.executeQuery(consulta);
                while (rs.next())
                {
                    ListadoPerrucaria c =
                            new ListadoPerrucaria(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                    rs.getString(5), rs.getDate(6).toLocalDate(), rs.getInt(7), rs.getString(8));
                    resultado.add(c);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                resultado.clear();
            }
        });

        if (resultado.isEmpty()) return null;

        return resultado;
    }


    // Recuperar todas as citas filtrando por dni propietario
    public static List<ListadoPerrucaria> recuperarTodaAsCitasDePerrucariaDunPropietario(String dni)
    {
        List<ListadoPerrucaria> resultado = new ArrayList<>();

        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta =
                        "SELECT citasperrucaria.codCita, propietarios.nome, propietarios.ap1, propietarios.ap2, cans.nome, citasperrucaria.data, " +
                                "citasperrucaria.hora, citasperrucaria.chip FROM citasperrucaria, cans, propietarios " +
                                "where citasperrucaria.chip=cans.chip and cans.dnipropietario=propietarios.dni and " +
                                "propietarios.dni='" + dni + "' order by citasperrucaria.data";
                ResultSet rs = stmt.executeQuery(consulta);
                while (rs.next())
                {
                    ListadoPerrucaria c =
                            new ListadoPerrucaria(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                    rs.getString(5), rs.getDate(6).toLocalDate(), rs.getInt(7), rs.getString(8));
                    resultado.add(c);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                resultado.clear();
            }
        });

        if (resultado.isEmpty()) return null;

        return resultado;
    }


    // Recuperar todas as citas con filtro por datas
    public static List<ListadoPerrucaria> recuperarTodaAsCitasDePerrucariaEntreDuasDatas(String dataInicial,
            String dataFinal)
    {
        List<ListadoPerrucaria> resultado = new ArrayList<>();

        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta =
                        "SELECT citasperrucaria.codCita, propietarios.nome, propietarios.ap1, propietarios.ap2, cans.nome, citasperrucaria.data, " +
                                "citasperrucaria.hora, citasperrucaria.chip FROM citasperrucaria, cans, propietarios " +
                                "where citasperrucaria.chip=cans.chip and cans.dnipropietario=propietarios.dni " +
                                "and citasperrucaria.data>='" + dataInicial + "' and citasperrucaria.data<='" +
                                dataFinal + "'" + " order by citasperrucaria.data";
                ResultSet rs = stmt.executeQuery(consulta);
                while (rs.next())
                {
                    ListadoPerrucaria c =
                            new ListadoPerrucaria(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                    rs.getString(5), rs.getDate(6).toLocalDate(), rs.getInt(7), rs.getString(8));
                    resultado.add(c);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                resultado.clear();
            }
        });

        if (resultado.isEmpty()) return null;

        return resultado;
    }

    public static List<ListadoPerrucaria> recuperarTodaAsCitasDePerrucariaDunPropietarioEntreDuasDatas(String dni,
            String dataInicial, String dataFinal)
    {
        List<ListadoPerrucaria> resultado = new ArrayList<>();

        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta =
                        "SELECT citasperrucaria.codCita, propietarios.nome, propietarios.ap1, propietarios.ap2, cans.nome, citasperrucaria.data, " +
                                "citasperrucaria.hora, citasperrucaria.chip FROM citasperrucaria, cans, propietarios " +
                                "where citasperrucaria.chip=cans.chip and cans.dnipropietario=propietarios.dni " +
                                " and propietarios.dni='" + dni + "'" + " and citasperrucaria.data>='" + dataInicial +
                                "' and citasperrucaria.data<='" + dataFinal + "'" + " order by citasperrucaria.data";
                ResultSet rs = stmt.executeQuery(consulta);
                while (rs.next())
                {
                    ListadoPerrucaria c =
                            new ListadoPerrucaria(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                    rs.getString(5), rs.getDate(6).toLocalDate(), rs.getInt(7), rs.getString(8));
                    resultado.add(c);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                resultado.clear();
            }
        });

        if (resultado.isEmpty()) return null;

        return resultado;
    }

    public static boolean eliminarUnhaCitaDadoSeuCodigo(int codCita)
    {
        boolean[] resultado = {true};
        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta = "delete from citasperrucaria where codCita=" + codCita;
                stmt.executeUpdate(consulta);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                resultado[0] = false;
            }
        });

        return resultado[0];
    }
}
