package org.lebastudios.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Enderezo
{
    private String rua;
    private String cp;
    private String localidade;
    private String provincia;
}
