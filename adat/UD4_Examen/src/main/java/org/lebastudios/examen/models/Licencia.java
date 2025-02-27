package org.lebastudios.examen.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    public Licencia(String numeroLicencia, Date fechaExpedicion, Date fechaVencimiento)
    {
        this.numeroLicencia = numeroLicencia;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public static Licencia defaultLicencia()
    {
        LocalDateTime now = LocalDateTime.now();
        
        return new Licencia(
                String.valueOf(System.currentTimeMillis()),
                new Date(now.getYear() - 1900, now.getMonthValue() - 1, now.getDayOfMonth()),
                new Date(now.getYear() - 1897, now.getMonthValue() - 1, now.getDayOfMonth())
        );
    }
}
