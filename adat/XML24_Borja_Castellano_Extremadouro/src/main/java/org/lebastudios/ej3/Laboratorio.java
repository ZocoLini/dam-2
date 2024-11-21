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
public class Laboratorio extends Entidad
{
    @XmlAttribute(name = "numEmpleados")
    private int numEmpleados;
    @XmlAttribute(name = "especialidad")
    private String especialidad;

    @Override
    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\tLaboratorio: ").append(getNombre()).append(" (").append(getSiglas()).append(") ");
        sb.append("Sede: ").append(getSede()).append(" ".repeat(8)).append("Año de creacion: ").append(getAnho()).append("\n");
        
        sb.append("\t\tEmpleados: ").append(getNumEmpleados()).append("Expecialidad: ").append(especialidad).append("\n");
        
        return sb.toString();
    }
}
