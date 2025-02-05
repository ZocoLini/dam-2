package org.lebastudios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Familiar
{
    private String nss;
    private String nome;
    private String parentesco;
    private String apelido1;
    private String apelido2;
    private Date dataNacemento;
    private Character sexo;
}
