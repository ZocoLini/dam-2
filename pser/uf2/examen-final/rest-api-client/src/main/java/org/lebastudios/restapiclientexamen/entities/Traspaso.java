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
    private int viejaOperadora;
    private int nuevaOperadora;

    public Traspaso(String telefono, int viejaOperadora, int nuevaOperadora)
    {
        this.telefono = telefono;
        this.viejaOperadora = viejaOperadora;
        this.nuevaOperadora = nuevaOperadora;
    }
}
