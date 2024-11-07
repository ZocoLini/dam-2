package org.lebastudios.EJ5_A4UD2REPASOVinculacion;

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
public class CapturaMinima
{
    @XmlAttribute(name = "unidade")
    private String unidad;
    @XmlValue
    private float value;

    public String preattyPrinting()
    {
        return "Captura Mínima: " + value + unidad;
    }
}
