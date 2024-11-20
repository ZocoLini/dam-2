package org.lebastudios.examples.jaxb;

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
public class Trabajador extends Persona
{

    @XmlElement(name = "Empresa")
    private String companhia;
    @XmlElement(name = "Salario")
    private float sueldo;
    @XmlElement(name = "Puesto")
    private String cargo;
}
