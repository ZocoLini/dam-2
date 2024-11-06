package org.lebastudios.EJ2_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
@ToString
@XmlRootElement(name = "Modulo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Modulo
{
    @XmlElement(name = "Nome")
    private String nome;
    
    @XmlElementWrapper(name = "Contidos")
    @XmlElement(name = "Bloque")
    private ArrayList<Bloque> bloques = new ArrayList<>();
}
