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
public class Captura
{
    @XmlAttribute(name = "numUnidades")
    private int numUnidades;
    @XmlAttribute(name = "peso")
    private float peso;
    @XmlValue
    private String nomeCaptura;

    public String preattyPrinting()
    {
        return "Captura de: " + nomeCaptura + ", Unidades: " + numUnidades 
                + ", Peso: " + peso;
    }
}
