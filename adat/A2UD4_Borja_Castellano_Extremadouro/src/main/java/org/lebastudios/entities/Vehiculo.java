package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public class Vehiculo implements java.io.Serializable
{
    private String nss;
    private String matricula;
    private String marca;
    private String modelo;
    private Date dataCompra;
    private Empregado empregado;

}