package org.lebastudios.examen.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FotografoSimplificado
{
    private int cod;
    private String nombreCompleto;
    int numFotografias;
}
