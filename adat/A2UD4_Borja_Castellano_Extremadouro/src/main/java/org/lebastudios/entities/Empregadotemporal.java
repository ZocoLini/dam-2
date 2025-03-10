package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Empregadotemporal generated by hbm2java
 */
@Setter
@Getter
public class Empregadotemporal extends Empregado implements java.io.Serializable
{

    private Date dataInicio;
    private Date dataFin;
    private Double costeHora;
    private Double numHoras;
}