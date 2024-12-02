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
@Getter
public class ListadoPerrucaria
{
    int codCita;
    String propietario;
    String codCan;
    String can;
    LocalDate data;
    String hora;

    public ListadoPerrucaria(int codCita, String nombrePropietario, String ap1Propietario, String ap2Propietario,
            String can, LocalDate data, int hora, String codCan)
    {
        this.codCita = codCita;
        this.propietario = nombrePropietario + " " + ap1Propietario + " " + ap2Propietario;
        this.can = can;
        this.data = data;
        this.hora = hora + ".00 h";
        this.codCan = codCan;
    }

    @Override
    public String toString()
    {
        return propietario;
    }


}

