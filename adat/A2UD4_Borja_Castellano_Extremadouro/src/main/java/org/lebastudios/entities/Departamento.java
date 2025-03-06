package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
public class Departamento implements java.io.Serializable
{
    private int numDepartamento;
    private String nomeDepartamento;

    private Empregadofixo director;
    private Set<Empregado> empregados = new HashSet<>(0);

    private Collection<String> lugares = new ArrayList<>();
    private Collection<Proxecto> proxectos = new ArrayList<>();
}