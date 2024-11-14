package org.lebastudios.EJ5_A5UD2.Modo1;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Puerto
{
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data;
    private int idPorto;
    private int idPortoRef;
    private double latitude;
    private double lonxitude;
    private String nomePorto;
    private List<Marea> listaMareas;

    @Override
    public String  toString()
    {
        return "Puerto{" +
                "data=" + data +
                ", idPorto=" + idPorto +
                ", idPortoRef=" + idPortoRef +
                ", latitude=" + latitude +
                ", lonxitude=" + lonxitude +
                ", nomePorto='" + nomePorto + '\'' +
                ", listaMareas=" + listaMareas +
                '}';
    }
}
