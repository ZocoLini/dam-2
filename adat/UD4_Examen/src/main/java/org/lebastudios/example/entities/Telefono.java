package org.lebastudios.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Telefono
{
    @Column(name = "Numero")
    private String numero;
    @Column(name = "Info") 
    private String info;
}
