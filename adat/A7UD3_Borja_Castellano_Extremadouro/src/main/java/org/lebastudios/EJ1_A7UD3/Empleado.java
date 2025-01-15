package org.lebastudios.EJ1_A7UD3;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado
{
    @SerializedName("NSS_empregado")
    private String nssEmpregado;
    
    private List<Familiar> familiares;
}
