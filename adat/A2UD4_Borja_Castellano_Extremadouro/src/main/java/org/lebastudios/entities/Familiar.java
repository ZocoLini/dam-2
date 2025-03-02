package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public class Familiar implements java.io.Serializable
{

    private String nss;
    private String nome;
    private String apelido1;
    private String apelido2;
    private Date dataNacimento;
    private String parentesco;
    private char sexo;
}