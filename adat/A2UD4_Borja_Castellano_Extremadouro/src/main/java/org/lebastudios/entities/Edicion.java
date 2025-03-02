package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Setter
@Getter
public class Edicion implements java.io.Serializable
{

    private EdicionId id;

    private Date data;
    private String lugar;
    private Collection<Empregado> alumnos = new ArrayList<>();

    private Curso curso;

    private Empregadofixo empregadofixo;
}
