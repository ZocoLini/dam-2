package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class Curso implements java.io.Serializable
{

    private int codigo;
    private String nome;
    private Integer horas;
    private List<Edicion> edicions = new ArrayList();
}
