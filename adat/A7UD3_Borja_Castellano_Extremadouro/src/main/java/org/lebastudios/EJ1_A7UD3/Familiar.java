package org.lebastudios.EJ1_A7UD3;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Familiar
{
    private Short numero;

    private String nssEmpregado;

    @SerializedName("NSS")
    private String nss;

    @SerializedName("Nome")
    private String nome;

    @SerializedName("Apelido1")
    private String apelido1;

    @SerializedName("Apelido2")
    private String apelido2;

    @SerializedName("DataNacemento")
    private LocalDate dataNacemento;

    @SerializedName("Parentesco")
    private String parentesco;

    @SerializedName("Sexo")
    private char sexo = 'M';
}
