package org.lebastudios.EJ4_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "CadenaHotelera")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hoteles
{   
    @XmlAttribute(name = "nombre")
    private String nombre;
    @XmlAttribute(name = "CIF")
    private String cif;
    @XmlElement(name = "Hotel")
    private List<Hotel> hoteles = new ArrayList<>();
}
