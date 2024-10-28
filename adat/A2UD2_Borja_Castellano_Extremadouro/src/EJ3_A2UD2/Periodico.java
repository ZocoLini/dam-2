package EJ3_A2UD2;

import java.util.ArrayList;
import java.util.List;

public class Periodico
{
    private String titular;
    private String descripcion;
    private final List<Item> items = new ArrayList<>();

    public void setTitular(String titular)
    {
        this.titular = titular;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public List<Item> getItems()
    {
        return items;
    }
    
    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Noticias\n");
        sb.append("=====================================\n");
        sb.append("Titular: ").append(titular).append("\n");
        sb.append("Descripcion: ").append(descripcion).append("\n");
        
        for (int i = 0; i < items.size(); i++) 
        {
            Item item = items.get(i);
            sb.append("==============NOTICIA ").append(i).append("==============\n");
            sb.append(item.preattyPrinting());
        }
        
        return sb.toString();
    }
}
