package org.lebastudios.EJ3_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @XmlElementWrapper(name = "Informacion Contacto")
    @XmlList
    private List<String> telefonos = new ArrayList<>();
}
