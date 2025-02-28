package org.lebastudios.restapiclientexamen.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Telefono
{
    private String telefono;
    private int codOperador;
    private String titular;
}
