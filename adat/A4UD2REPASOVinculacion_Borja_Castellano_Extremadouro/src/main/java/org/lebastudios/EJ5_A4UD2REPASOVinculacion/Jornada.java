package org.lebastudios.EJ5_A4UD2REPASOVinculacion;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Jornada
{
    @XmlAttribute(name = "lugar")
    private String lugar;
    @XmlAttribute(name = "data")
    private String date;
    
    @XmlElement(name = "Captura")
    private List<Captura> capturas;
    
    @XmlElement(name = "Descricion")
    private String descripcion;

    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Xornada día: ").append(date).append(" (").append(lugar).append(")\n");
        sb.append("Descripción: ").append(descripcion == null ? "Ninguna" : descripcion).append("\n");
        
        sb.append(" ".repeat(4)).append("Capturas:\n");
        
        if (capturas == null || capturas.isEmpty()) 
        {
            sb.append(" ".repeat(8)).append("- Ninguna.");
        }
        else
        {
            capturas.forEach(captura -> sb.append(" ".repeat(8)).append("- ").append(captura.preattyPrinting()).append("\n"));
        }

        return sb.toString();
    }
}
