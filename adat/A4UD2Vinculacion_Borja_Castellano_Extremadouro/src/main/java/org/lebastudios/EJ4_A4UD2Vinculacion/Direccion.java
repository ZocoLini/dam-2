package org.lebastudios.EJ4_A4UD2Vinculacion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Direccion
{
    @XmlElement(name = "Calle")
    private String calle;
    @XmlElement(name = "Numero")
    private int numero;
    @XmlElement(name = "CodigoPostal")
    private int codigoPostal;
}
