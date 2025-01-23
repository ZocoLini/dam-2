package org.lebastudios.ej3;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lebastudios.database.Alojamiento;
import org.lebastudios.database.AlojamientoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlojaminetosPrecios
{
    @SerializedName("Actualizaciones")
    List<Actualizacion> actualizaciones;
    
    public void realizarActualizaciones(Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement("update ALOJAMIENTO " +
                "set precio_habitacion = ? " +
                "where nombre = ?"
        ))
        {
            for (Actualizacion actualizacion : actualizaciones)
            {
                short codAlojamiento = AlojamientoDAO.getAlojaminetoId(actualizacion.getNombre(), connection);

                if (codAlojamiento == 0)
                {
                    System.out.println("Error: El alojamiento " + actualizacion.getNombre() + " no existe en la base de datos");
                    continue;
                }

                Alojamiento alojamiento = AlojamientoDAO.select(codAlojamiento, connection);
                
                if (alojamiento == null) 
                {
                    System.err.println("No debería fallar nunca ya que el codigo nos lo devolvio un procedimiento");
                    return;
                }
                
                System.out.printf(
                        "Codigo: %d Alojamineto: %s Precio antiguo: %.0f + Precio nuevo: %.0f\n",
                        alojamiento.getCodigo(),
                        alojamiento.getNombre(),
                        alojamiento.getPrecioHabitacion(),
                        actualizacion.getPrecioHabitacion()
                );

                preparedStatement.setFloat(1, actualizacion.getPrecioHabitacion());
                preparedStatement.setString(2, actualizacion.getNombre());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }
}
