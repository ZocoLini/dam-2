package org.lebastudios.EJ5_A4UD2REPASOVinculacion;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "Pesca")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pesca
{
    @XmlElementWrapper(name = "Especies")
    @XmlElement(name = "Especie")
    private List<Especie> especies;
    
    @XmlElement(name = "Xornada")
    private List<Jornada> jornadas;
    
    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();
        
        if (especies != null) 
        {
            for (var especie : especies)
            {
                sb.append(especie.preattyPrinting()).append("\n");
            }
        }
        
        if (jornadas != null) 
        {
            for (var jornada : jornadas)
            {
                sb.append(jornada.preattyPrinting()).append("\n");
            }
        }
        
        return sb.toString();
    }
}
