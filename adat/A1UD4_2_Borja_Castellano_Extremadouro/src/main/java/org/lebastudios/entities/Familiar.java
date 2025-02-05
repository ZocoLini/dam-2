package org.lebastudios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Familiar
{
    @Column(name = "NSS", nullable = false)
    private String nss;
    @Column(name = "Nome", nullable = false)
    private String nome;
    @Column(name = "Parentesco", nullable = false)
    private String parentesco;
    @Column(name = "Apelido1", nullable = false)
    private String apelido1;
    @Column(name = "Apelido2")
    private String apelido2;
    @Column(name = "DataNacemento")
    @Temporal(TemporalType.DATE)
    private Date dataNacemento;
    @Column(name = "Sexo")
    private Character sexo;
}
