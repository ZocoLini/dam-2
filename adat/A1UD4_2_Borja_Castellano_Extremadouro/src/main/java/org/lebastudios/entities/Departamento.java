package org.lebastudios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Departamento")
public class Departamento
{
    @Id
    @Column(name = "NumDepartamento")
    private Integer numDepartamento;
    
    @Column(name = "NomeDepartamento")
    private String nomeDepartamento;

}