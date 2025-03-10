package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Empregado generated by hbm2java
 */
@Setter
@Getter
public class Empregado implements java.io.Serializable
{

    private String nss;
    private String nome;
    private String apelido1;
    private String apelido2;
    private Date dataNacemento;
    private Character sexo;
    private Enderezo enderezo;

    private Empregado supervisor;
    private Set<Empregado> supervisados = new HashSet<>(0);
    private List<Familiar> familiares = new ArrayList<>();

    private Map<String, String> telefonos = new HashMap<>();
    private Departamento departamento;

    private Set<EmpregadoProxecto> empregadoProxectos = new HashSet<>(0);
    private Set<Edicion> ediciones = new HashSet<>();
    private Vehiculo vehiculo;

    public Object getSupervisa()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
