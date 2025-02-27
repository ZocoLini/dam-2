package org.lebastudios.examen.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Fotografia implements java.io.Serializable {

    private int idFotografia;
    private String titulo;
    private Date fechaCaptura;
    private Character color;
    private Fotografo fotografo;

    public Fotografia() {}

    public Fotografia(int idFotografia, String titulo, Date fechaCaptura) {
        this.idFotografia = idFotografia;

        this.titulo = titulo;
        this.fechaCaptura = fechaCaptura;
    }
}
