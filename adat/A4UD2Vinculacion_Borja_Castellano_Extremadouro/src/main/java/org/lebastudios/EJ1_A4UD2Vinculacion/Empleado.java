package org.lebastudios.EJ1_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Empleado
{
    @XmlElement
    private String dni;
    
    @XmlElement
    private String nombre;
    
    @XmlElement
    private int edad;

    public Empleado(String dni, String nombre, int edad)
    {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
    }
}
