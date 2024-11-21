package org.lebastudios.ej3;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Programa")
public class Programa
{
    @XmlAttribute(name = "denominacion")
    private String denominacion;
    @XmlAttribute(name = "pais")
    private String pais;
    
    @XmlElementWrapper(name = "Entidades")
    @XmlElements({
            @XmlElement(name = "Laboratorio", type = Laboratorio.class),
            @XmlElement(name = "Universidad", type = Universidad.class),
    })
    private List<Entidad> entidades = new ArrayList<>();
    
    @XmlElementWrapper(name = "Rios")
    @XmlElement(name = "Rio")
    private List<Rio> rios = new ArrayList<>();

    public String getPreattyPrinting() 
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Programa: ").append(denominacion).append(" ".repeat(8)).append("Pais: ").append(pais).append("\n");
        sb.append("\n");
        
        sb.append("Entidades: ").append(entidades.size()).append("\n");
        sb.append("----------------------------------------------------------------------------\n");
        
        entidades.forEach(entidad -> sb.append(entidad.preattyPrinting()).append("\n"));
        
        sb.append("Rios: ").append(rios.size()).append("\n");
        sb.append("----------------------------------------------------------------------------\n");
        
        rios.forEach(rio -> sb.append(rio.preattyPrinting()).append("\n"));
        
        sb.append("-----------------------------------------------------------------------------\n\n");
        
        sb.append("Media del oxigeno disuelto total: ").append(getMediaOxigenoDisuelto()).append(" mg/l ");
        sb.append("Media de la temperatura total: ").append(getMediaTemperatura()).append(" º");
        
        return sb.toString();
    }

    private float getMediaTemperatura()
    {
        float total = 0;
        
        for (Rio rio : rios)
        {
            total += rio.calcularTemperaturaMedia();
        }
        
        return total / rios.size();
    }

    private float getMediaOxigenoDisuelto()
    {
        float total = 0;
        
        for (Rio rio : rios)
        {
            total += rio.calcularOxigenoMedioDisuelto();
        }
        
        return total / rios.size();
    }
}
