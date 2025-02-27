package org.lebastudios.examen.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fotografo implements java.io.Serializable
{
    private int idFotografo;
    private String nombre;
    private String apellidos;
    private String seudonimo;
    
    private InformacionContacto infoContacto;
    
    private Collection<Fotografia> fotografias;
    private List<Material> materiales;
    private Set<Evento> eventos;

    private Licencia licencia;
    
    private Map<String, String> idiomas;
    
    private Fotografo influenciadoPor;
    
    private Set<Fotografo> influenciaA; 
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;

        Fotografo fotografo = (Fotografo) o;
        return idFotografo == fotografo.idFotografo;
    }

    @Override
    public int hashCode()
    {
        return idFotografo;
    }

}
