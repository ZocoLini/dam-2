package EJ2_A3P2UD1;

import java.io.File;

public class Alumno
{
    private final String curso;
    private final String numero;
    private final String nombre;
    
    public Alumno(String s)
    {
        String[] args = s.split("/");
        
        curso = args[0];
        numero = args[1];
        nombre = args[2];
    }

    public boolean generateDir(File root)
    {
        File cursoFile = new File(root, curso);
        File alumnFile = new File(cursoFile, numero + "-" + nombre);
        
        return alumnFile.mkdirs();
    }

    public String getNombre()
    {
        return nombre;
    }
}
