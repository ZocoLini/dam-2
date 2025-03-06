package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;


@Setter
@Getter
public class Proxecto implements java.io.Serializable
{

    private int numProxecto;
    private Departamento departamento;
    private String nomeProxecto;
    private String lugar;
    private Collection<EmpregadoProxecto> empregadoProxectos = new ArrayList<>();
}
