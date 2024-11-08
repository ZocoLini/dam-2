package org.lebastudios.EJ4_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Hotel
{
    @XmlAttribute(name = "id")
    @XmlID
    private String codigoHotel;
    @XmlElement(name = "Nombre")
    private String nombre;
    @XmlElement(name = "FechaInauguracion")
    private String fechaInauguracion;
    @XmlList
    @XmlElement(name = "Telefonos")
    private List<String> telefonos;
    @XmlElement(name = "Direccion")
    private Direccion direccion;
}
