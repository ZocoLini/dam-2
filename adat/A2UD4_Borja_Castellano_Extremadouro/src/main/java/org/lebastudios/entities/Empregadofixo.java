package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Setter
@Getter
public class Empregadofixo extends Empregado implements java.io.Serializable
{
    private Double salario;
    private Date dataAlta;
    private String categoria;

    private SortedMap<Date, Double> horasextras = new TreeMap<>();

    private Set<Edicion> edicionprofesor = new HashSet<>(0);

    private Set<Departamento> deptodirector = new HashSet<>(0);

}
