package org.lebastudios.EJ5_A5UD2.Modo1;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Marea
{
    private double altura;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime hora;
    private int idPorto;
    private int idTipoMarea;
    private String tipoMarea;

    @Override
    public String toString()
    {
        return "Marea{" +
                "altura=" + altura +
                ", data=" + data +
                ", hora=" + hora +
                ", idPorto=" + idPorto +
                ", idTipoMarea=" + idTipoMarea +
                ", tipoMarea='" + tipoMarea + '\'' +
                '}';
    }
}
