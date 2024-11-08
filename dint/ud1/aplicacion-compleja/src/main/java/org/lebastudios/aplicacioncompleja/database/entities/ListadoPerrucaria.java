/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;

import java.time.LocalDate;

/**
 * @author DAM2
 */
public class ListadoPerrucaria
{
    @Getter int codCita;
    String propietario;
    @Getter String can;
    @Getter LocalDate data;
    @Getter String hora;

    public ListadoPerrucaria(int codCita, String nombrePropietario, String ap1Propietario, String ap2Propietario,
            String can, LocalDate data, int hora)
    {
        this.codCita = codCita;
        this.propietario = nombrePropietario + " " + ap1Propietario + " " + ap2Propietario;
        this.can = can;
        this.data = data;
        this.hora = hora + ".00 h";
    }

    @Override
    public String toString()
    {
        return propietario;
    }


}

