package org.lebastudios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ListIndexBase;

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
    
    @ElementCollection
    @CollectionTable(name = "familiar", joinColumns = @JoinColumn(name = "NSS_Empregado"))
    @OrderColumn(name = "Numero")
    @ListIndexBase(value = 1)
    private List<Familiar> familiares = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "aficion", joinColumns = @JoinColumn(name = "NSS_Empregado"))
    @Column(name = "Aficion", nullable = false)
    private Collection<String> aficiones = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "lugar", joinColumns = @JoinColumn(name = "Num_Departamento"))
    @Column(name = "Lugar", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Collection<String> lugares = new ArrayList<>();
}