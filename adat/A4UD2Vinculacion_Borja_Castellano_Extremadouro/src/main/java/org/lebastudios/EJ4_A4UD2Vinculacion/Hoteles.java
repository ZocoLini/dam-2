package org.lebastudios.EJ4_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
    @XmlElement(name = "Hotel")
    private List<Hotel> hoteles = new ArrayList<>();
}
