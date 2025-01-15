package org.lebastudios.examples;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lebastudios.sqlx.Column;
import org.lebastudios.sqlx.IgnoreField;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Lugar
{
    @IgnoreField
    private String nomeLugar;
    
    @Column(name = "Num_departamento")
    private int numDepartamento;
}
