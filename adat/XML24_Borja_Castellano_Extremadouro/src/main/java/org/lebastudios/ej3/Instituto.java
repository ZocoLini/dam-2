package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Instituto extends Entidad
{
    @XmlAttribute(name = "numProyectos")
    private int numProyectos;
    
    @Override
    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("\tInstituto: ").append(getNombre()).append(" (").append(getSiglas()).append(") ");
        sb.append("Sede: ").append(getSede()).append(" ".repeat(8)).append("Año de creacion: ").append(getAnho()).append("\n");

        sb.append("\t\tNum Proyectos: ").append(getNumProyectos()).append("\n");

        return sb.toString();
    }
}
