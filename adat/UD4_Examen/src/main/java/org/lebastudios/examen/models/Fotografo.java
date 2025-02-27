package org.lebastudios.examen.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
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
    private String direccion;
    private String email;
    private String telefonoFijo;
    private String telefonoMovil;
    private Collection<Fotografia> fotografias;
    private List<Material> materiales;
    private Set<Evento> eventos;

    public Fotografo(int idFotografo, String nombre, String apellidos, String seudonimo, String email)
    {
        this.idFotografo = idFotografo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.seudonimo = seudonimo;
        this.email = email;
    }
    
    public Fotografo(int idFotografo, String nombre, String apellidos, String seudonimo, String direccion, String email,
            String telefonoFijo, String telefonoMovil, Fotografo influencer, Licencia licencia)
    {
        this.idFotografo = idFotografo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.seudonimo = seudonimo;
        this.direccion = direccion;
        this.email = email;
        this.telefonoFijo = telefonoFijo;
        this.telefonoMovil = telefonoMovil;
    }

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
