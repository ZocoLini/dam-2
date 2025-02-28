package org.lebastudios.restapiclientexamen.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lebastudios.restapiclientexamen.httpbodies.IgnoreURLEncode;

@Setter
@Getter
@NoArgsConstructor
public class Traspaso
{
    @IgnoreURLEncode
    private int id;
    private String telefono;
    @IgnoreURLEncode
    private int viejaOperadora;
    private int nuevaOperadora;
    private String motivo;

    public Traspaso(String telefono, int nuevaOperadora, String motivo)
    {
        this.telefono = telefono;
        this.nuevaOperadora = nuevaOperadora;
        this.motivo = motivo;
    }
}
