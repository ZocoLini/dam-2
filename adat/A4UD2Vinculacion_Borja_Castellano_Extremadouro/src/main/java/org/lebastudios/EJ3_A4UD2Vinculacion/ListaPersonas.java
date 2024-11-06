package org.lebastudios.EJ3_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
    @XmlElement
    private List<Persona> personas = new ArrayList<>();
}
