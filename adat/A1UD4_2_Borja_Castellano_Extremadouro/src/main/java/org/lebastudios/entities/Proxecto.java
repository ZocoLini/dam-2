package org.lebastudios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Proxecto")
public class Proxecto
{
    @Id
    @Column(name = "NumProxecto")
    private Integer numProxecto;
    
    @Column(name = "NomeProxecto")
    private String nomeProxecto;
    
    @Column(name = "Lugar")
    private String lugar;
    
    private Departamento departamento;
    
    private Set<Empregado> empleados = new HashSet<>(); 

}