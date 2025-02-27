package org.lebastudios.examen.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fotografia implements java.io.Serializable {

    private int idFotografia;
    private String titulo;
    private Date fechaCaptura;
    private Character color;
    private Fotografo fotografo;
}
