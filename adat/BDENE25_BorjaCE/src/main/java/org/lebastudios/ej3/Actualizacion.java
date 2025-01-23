package org.lebastudios.ej3;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Actualizacion
{
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("precio_habitacion")
    private float precioHabitacion;
}
