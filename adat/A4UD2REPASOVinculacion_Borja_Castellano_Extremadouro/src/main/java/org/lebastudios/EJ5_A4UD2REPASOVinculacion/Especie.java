package org.lebastudios.EJ5_A4UD2REPASOVinculacion;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Especie
{
    @XmlAttribute(name = "nome")
    private String nombre;
    @XmlAttribute(name = "valor")
    private String valor;
    @XmlElement(name = "Habitat")
    private String habitat;
    @XmlElement(name = "NomeCientífico")
    private String nombreCientifico;
    @XmlElement(name = "OutrosNomes")
    @XmlList
    private List<String> otrosNombres;
    @XmlElement(name = "CapturaMinima")
    private CapturaMinima capturaMinima;
    @XmlElement(name = "Nota")
    private List<String> notas;

    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("La especie ").append(nombre).append(" (").append(nombreCientifico).append("), ").append("tiene un " +
                "valor ").append(valor).append(".").append("\n");
        sb.append(" ".repeat(4)).append(capturaMinima.preattyPrinting()).append("\n");
        sb.append(" ".repeat(4)).append("Otros Nombre:\n");

        if (otrosNombres != null)
        {
            otrosNombres.forEach(otroNombre -> sb.append(" ".repeat(8)).append("- ").append(otroNombre).append("\n"));
        }
        else
        {
            sb.append(" ".repeat(8)).append("- Ninguno\n");
        }

        sb.append(" ".repeat(4)).append("Notas:\n");

        if (notas != null) 
        {
            notas.forEach(nota -> sb.append(" ".repeat(8)).append("- ").append(nota).append("\n"));
        }
        else
        {
            sb.append(" ".repeat(8)).append("- Ninguna\n");
        }
       
        return sb.toString();
    }
}
