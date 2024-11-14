package org.lebastudios.EJ5_A5UD2.Modo1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mareas
{
    private List<Puerto> mareas;

    @Override
    public String toString()
    {
        return "Mareas{" +
                "mareas=" + mareas +
                '}';
    }
}
