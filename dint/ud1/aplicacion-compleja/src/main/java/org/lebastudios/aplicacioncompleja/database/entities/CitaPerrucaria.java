/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CitaPerrucaria
{
    private int codCita;
    private String codCan;
    private LocalDate data;
    private int hora;

    public CitaPerrucaria(LocalDate data, int hora)
    {
        this.data = data;
        this.hora = hora;
    }

    public CitaPerrucaria(String codCan, LocalDate data, int hora)
    {
        this.codCan = codCan;
        this.data = data;
        this.hora = hora;
    }

    public CitaPerrucaria(int hora)
    {
        this.hora = hora;
    }

    public String getHora00()
    {
        return hora + ".00h";
    }


}
