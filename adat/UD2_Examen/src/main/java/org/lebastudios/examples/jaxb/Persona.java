package org.lebastudios.examples.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Estudiante.class, Trabajador.class})
public abstract class Persona
{
    @XmlElement(name = "Nombre")
    private String nombre;
    @XmlElement(name = "Edad")
    private int edad;

    @XmlElement(name = "InformacionContacto")
    private InfoContacto infoContacto = new InfoContacto();
}
