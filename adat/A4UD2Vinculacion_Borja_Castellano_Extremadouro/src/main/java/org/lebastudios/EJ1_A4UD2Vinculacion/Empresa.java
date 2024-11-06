package org.lebastudios.EJ1_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Empresa
{
    @XmlAttribute
    private String cif;
    
    @XmlElement
    private String nombre;
    
    @XmlElementWrapper
    @XmlElement(name = "empleado")
    private List<Empleado> empleados;

    public Empresa(String cif, String nombre, List<Empleado> empleados)
    {
        this.cif = cif;
        this.nombre = nombre;
        this.empleados = empleados;
    }
    
    public Empresa(String cif, String nombre, Empleado... empleados)
    {
        this.cif = cif;
        this.nombre = nombre;
        this.empleados = List.of(empleados);
    }
}
