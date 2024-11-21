package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Universidad extends Entidad
{
    @XmlAttribute(name = "numEstudiantes")
    private int numEstudiantes;
    @XmlAttribute(name = "facultades")
    private String facultades;

    @Override
    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("\tUniversidad: ").append(getNombre()).append(" (").append(getSiglas()).append(") ");
        sb.append("Sede: ").append(getSede()).append(" ".repeat(8)).append("Año de creacion: ").append(getAnho()).append("\n");

        sb.append("\t\tNumero Estudiantes: ").append(getNumEstudiantes()).append("Facultades: ").append(getFacultades()).append("\n");

        return sb.toString();
    }
}
