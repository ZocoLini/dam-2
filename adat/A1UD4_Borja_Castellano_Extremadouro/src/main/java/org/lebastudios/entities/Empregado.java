package org.lebastudios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Empregado
{
    private String nome;
    private String apelido1;
    private String apelido2;
    private String nss;
    private Float salario;
    private Date dataNacemento;
    private Character sexo;

}