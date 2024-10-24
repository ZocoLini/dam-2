package Fich24_BorjaCE.data;

import java.util.List;

public class Restaurante
{
    private final int numero;
    private final String nombre; // Unico
    private final String localidad;
    private int numcocineros;
    private final List<InfoCocinero> cocineros;
    private final boolean baja;

    public Restaurante(int numero, String nombre, String localidad, List<InfoCocinero> cocineros, boolean baja)
    {
        this.numero = numero;
        this.nombre = nombre;
        this.localidad = localidad;
        this.numcocineros = cocineros.size();
        this.cocineros = cocineros;
        this.baja = baja;
    }

    public int getNumero()
    {
        return numero;
    }
    
    public String getNombre()
    {
        return nombre;
    }

    public String getLocalidad()
    {
        return localidad;
    }

    public int getNumcocineros()
    {
        return numcocineros;
    }

    public List<InfoCocinero> getCocineros()
    {
        return cocineros;
    }

    public boolean isBaja()
    {
        return baja;
    }

    public void setNumcocineros(int numcocineros)
    {
        this.numcocineros = numcocineros;
    }

    // Devuelve un string con un formato de impresion de datos estetico visualmente
    public String preattyString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("---------------------------\n");
        sb.append("Restaurante codigo ").append(this.numero).append("\n");
        sb.append("---------------------------\n");
        sb.append("Nombre: ").append(this.nombre).append("(").append(localidad).append(")").append("\n");
        sb.append("Cocineros:");
        
        cocineros.forEach(infoCocinero -> sb.append(" ").append(infoCocinero.preattyString()));
        sb.append("\n");
        
        sb.append("----------------------------\n");
        
        return sb.toString();
    }
    
    // Devuelve el total de bytes cupados en disco por este objeto
    public int getLongitudRegistro()
    {
        int longitudRegistro = 4 + nombre.getBytes().length + localidad.getBytes().length + 4;
        
        for (var infoCocinero : cocineros)
        {
            longitudRegistro += infoCocinero.getLongitudRegistro();
        }
        
        return longitudRegistro;
    }
}
