package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Rio
{
    @XmlAttribute(name = "codigo")
    private String codigo;
    @XmlAttribute(name = "nombre")
    private String nombre;

    @XmlElement(name = "Medicion")
    private List<Medicion> mediciones;

    public String preattyPrinting() 
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Rio: ").append(nombre).append(" (").append(codigo).append(")\n");
        
        mediciones.forEach(medicion -> sb.append("\t").append(medicion.preattyPrinting()));
        
        sb.append("-----------------------------------------------------------------------------------------\n");
        sb.append("Media de oxigeno disuelto: ").append(calcularOxigenoMedioDisuelto()).append(" mg/l ");
        sb.append("Media de temperatura: ").append(calcularTemperaturaMedia()).append(" º\n");
        
        return sb.toString();
    }

    public float calcularTemperaturaMedia() 
    {
        float total = 0;
        
        for (Medicion medicion : mediciones)
        {
            total += medicion.getTemperatura();
        }
        
        return total / mediciones.size();
    }

    public float calcularOxigenoMedioDisuelto() 
    {
        float total = 0;
        
        for (Medicion medicion : mediciones)
        {
            total += medicion.getOxigeno();
        }
        
        return total / mediciones.size();
    }
}
