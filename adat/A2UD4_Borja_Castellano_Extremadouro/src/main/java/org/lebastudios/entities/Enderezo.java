/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Enderezo
{
    private String rua;
    private Integer numeroCalle;
    private String piso;
    private String cp;
    private String localidade;
    private String provincia;
}
