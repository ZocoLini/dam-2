package EJ2_ARUD2;

public class Luna
{
    private String estado;
    private String date;
    private String hora;

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    public void setHora(String hora)
    {
        this.hora = hora;
    }
    
    public String toXMLFormat(int indentation)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(indentation)).append("<Luna Estado=\"").append(estado).append("\">\n");
        sb.append(" ".repeat(indentation * 2)).append("<Fecha>").append(date).append("</Fecha>\n");
        sb.append(" ".repeat(indentation * 2)).append("<Hora>").append(hora).append("</Hora>\n");
        sb.append(" ".repeat(indentation)).append("</Luna>\n");
        return sb.toString();
    }
}
