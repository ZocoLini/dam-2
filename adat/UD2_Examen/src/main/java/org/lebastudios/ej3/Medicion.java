package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Medicion
{
    @XmlAttribute(name = "fecha")
    private String fecha;
    @XmlAttribute(name = "calidad")
    private String calidad;
    
    @XmlElement(name = "pH")
    private PH ph;
    @XmlElement(name = "Oxigeno")
    private float Oxigeno;
    @XmlElement(name = "Temperatura")
    private float Temperatura;

    @Getter
    @Setter
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class PH 
    {
        @XmlAttribute(name = "tipo")
        private String tipo;
        @XmlValue
        private float valor;
    }
}
