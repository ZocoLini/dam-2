package org.lebastudios.examen.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Material 
{
    private String numeroSerie;
    private String material;
    private String marca;
    private String modelo;
}
