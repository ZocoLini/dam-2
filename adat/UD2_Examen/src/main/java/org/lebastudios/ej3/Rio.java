package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Rio
{
    @XmlAttribute(name = "codigo")
    private String codigo;
    @XmlAttribute(name = "nombre")
    private String nombre;
    
    @XmlElement(name = "Medicion")
    private List<Medicion> mediciones;
}
