package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Universidad extends Entidad
{
    @XmlAttribute(name = "numEstudiantes")
    private int numEstudiantes;
    @XmlAttribute(name = "facultades")
    private String facultades;
}
