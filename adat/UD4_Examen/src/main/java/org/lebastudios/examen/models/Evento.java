package org.lebastudios.examen.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Evento implements java.io.Serializable
{
    private int idEvento;
    private String nombreEvento;
    private String ciudad;
    private Date fecha;
    private String tipoEvento;
    private Set<Fotografo> fotografos; 
}


