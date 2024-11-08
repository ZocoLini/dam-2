package org.lebastudios.EJ2_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Bloque
{
    @XmlAttribute(name = "sesións")
    private int sesions;
    @XmlAttribute(name = "num")
    private int num;
    @XmlElement(name = "Titulo")
    private String titulo;
    @XmlElement(name = "Descripcion")
    private String descripcion;
}
