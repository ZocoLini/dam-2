package org.lebastudios.restapiclientexamen.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Telefono
{
    @SerializedName("telefono")
    private String numero;
    private int codOperador;
    private String titular;
}
