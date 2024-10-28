package EJ3_A2UD2;

import java.util.ArrayList;
import java.util.List;

public class Item
{
    private String titular;
    private String pubDate;
    private String autor;
    private String descripcion;
    private final List<String> categorias = new ArrayList<>();

    public void setTitular(String titular)
    {
        this.titular = titular;
    }

    public void setPubDate(String pubDate)
    {
        this.pubDate = pubDate;
    }

    public void setAutor(String autor)
    {
        this.autor = autor;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public List<String> getCategorias()
    {
        return categorias;
    }
    
    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Titular: ").append(titular).append("\n");
        sb.append("Fecha de publicacion: ").append(pubDate).append("\n");
        sb.append("Autor: ").append(autor).append("\n");
        sb.append("Descripcion: ").append(descripcion).append("\n");
        
        sb.append("Categorias: ");
        categorias.forEach(categoria -> sb.append("/").append(categoria));
        
        sb.append("\n");
        
        return sb.toString();
    }
}
