package EJ2_ARUD2;

import java.util.ArrayList;

public class ListaLunas
{
    private ArrayList<Luna> lunas = new ArrayList<>();
    private String descripcion;
    private String servicio;

    public ArrayList<Luna> getLunas()
    {
        return lunas;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public void setServicio(String servicio)
    {
        this.servicio = servicio;
    }

    public String toXMLFormat()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<Lunas Descripcion=\"").append(descripcion).append("\" Servicio=\"").append(servicio).append("\">\n");
        
        lunas.forEach(luna -> sb.append(luna.toXMLFormat(4)));
        sb.append("</Lunas>\n");
        
        return sb.toString();
    }
}
