package org.lebastudios.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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

    @ElementCollection
    @CollectionTable(name = "lugar", joinColumns = @JoinColumn(name = "Num_Departamento"))
    @Column(name = "Lugar", nullable = false)
    @GenericGenerator(name = "increment", strategy = "increment")
    @CollectionId(columns = @Column(name = "id"), generator = "increment", type = @Type(type = "int"))
    private Collection<String> lugares = new ArrayList<>();
}