package org.lebastudios.EJ1_A7UD3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Empleados
{
    List<Empleado> empleados = new ArrayList<>();
}
