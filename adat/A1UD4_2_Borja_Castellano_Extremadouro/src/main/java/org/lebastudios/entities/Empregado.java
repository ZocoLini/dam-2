package org.lebastudios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "empregado")
public class Empregado
{
    @Column(name = "Nome")
    private String nome;
    @Column(name = "Apelido1")
    private String apelido1;
    @Column(name = "Apelido2")
    private String apelido2;
    @Id
    @Column(name = "NSS")
    private String nss;
    @Column(name = "Salario")
    private Float salario;
    @Column(name = "DataNacemento")
    private Date dataNacemento;
    @Column(name = "Sexo")
    private Character sexo;
    
    // @ElementCollection
    // @CollectionTable(name = "telefonos", joinColumns = @JoinColumn(name = "NSS"))
    // private Set<Telefono> telefonos = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(name = "telefonos", joinColumns = @JoinColumn(name = "NSS"))
    @MapKeyColumn(name = "Numero")
    @Column(name = "Info")
    private Map<String, String> telefonos = new HashMap<>();
}