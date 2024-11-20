package org.lebastudios.examples.jaxb;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@XmlRootElement(name = "Personas")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaPersonas
{
    @XmlElements({
            @XmlElement(name = "Estudiante", type = Estudiante.class),
            @XmlElement(name = "Trabajador", type = Trabajador.class)
    })
    private List<Persona> personas = new ArrayList<>();
}
