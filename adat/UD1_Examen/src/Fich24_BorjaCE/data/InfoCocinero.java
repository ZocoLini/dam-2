package Fich24_BorjaCE.data;

public class InfoCocinero
{
    private final int codigo;
    private final String tipo;

    public InfoCocinero(int codigo, String tipo)
    {
        this.codigo = codigo;
        this.tipo = tipo;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public String getTipo()
    {
        return tipo;
    }

    // Obtiene el numero de bytes ocupados por este objeto en disco
    public int getLongitudRegistro()
    {
        return 4 + tipo.getBytes().length;
    }
    
    // Devuelve un string con un formato de impresion de datos estetico visualmente
    public String preattyString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        Cocinero cocinero = CocineroDAO.leer(codigo);
        
        if (cocinero == null) 
        {
            stringBuilder.append("[Cocinero desconocido]");
            return stringBuilder.toString();
        }
        
        stringBuilder.append("[").append(tipo).append(":").append(codigo)
                .append("-").append(cocinero.getApodo())
                .append(" (").append(cocinero.getNombreCompleto()).append(")]");
        
        return stringBuilder.toString();
    }
}
