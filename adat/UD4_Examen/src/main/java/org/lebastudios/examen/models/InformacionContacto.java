package org.lebastudios.examen.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InformacionContacto
{
    private String direccion;
    private String email;
    private String telFijo;
    private String telMovil;
}
