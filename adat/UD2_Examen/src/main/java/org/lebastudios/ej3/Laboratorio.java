package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Laboratorio extends Entidad
{
    @XmlAttribute(name = "numEmpleados")
    private int numEmpleados;
    @XmlAttribute(name = "especialidad")
    private String especialidad;
}
