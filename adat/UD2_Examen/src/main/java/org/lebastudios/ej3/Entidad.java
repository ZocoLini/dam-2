package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Laboratorio.class, Universidad.class})
public abstract class Entidad
{
    @XmlAttribute(name = "siglas")
    private String siglas;
    @XmlAttribute(name = "año")
    private String anho;
    @XmlAttribute(name = "sede")
    private String sede;
    @XmlValue
    private String nombre;

    public abstract String preattyPrinting();
}
