package org.lebastudios.EJ3_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Estudiante extends Persona
{
    @XmlElement(name = "Universidad")
    private String universidad;
    @XmlElement(name = "Carrera")
    private String carrera;
}
