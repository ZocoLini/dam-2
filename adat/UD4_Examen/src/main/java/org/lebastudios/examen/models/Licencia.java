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
public class Licencia implements java.io.Serializable {

    private int idFotografo;
    private String numeroLicencia;
    private Date fechaExpedicion;
    private Date fechaVencimiento;
    
    private Fotografo fotografo;
}
