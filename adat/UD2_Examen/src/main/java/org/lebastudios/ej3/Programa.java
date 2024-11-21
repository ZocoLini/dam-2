package org.lebastudios.ej3;

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
@XmlRootElement(name = "Programa")
public class Programa
{
    @XmlAttribute(name = "denominacion")
    private String denominacion;
    @XmlAttribute(name = "pais")
    private String pais;
    
    @XmlElementWrapper(name = "Entidades")
    @XmlElements({
            @XmlElement(name = "Laboratorio", type = Laboratorio.class),
            @XmlElement(name = "Universidad", type = Universidad.class),
    })
    private List<Entidad> entidades = new ArrayList<>();
    
    @XmlElementWrapper(name = "Rios")
    @XmlElement(name = "Rio")
    private List<Rio> rios = new ArrayList<>();
}
