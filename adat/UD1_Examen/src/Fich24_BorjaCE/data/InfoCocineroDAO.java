package Fich24_BorjaCE.data;

import java.io.IOException;
import java.io.RandomAccessFile;

public class InfoCocineroDAO
{
    public static void insertar(InfoCocinero info, RandomAccessFile raf) throws IOException
    {
        raf.writeInt(info.getCodigo());
        raf.writeUTF(info.getTipo());
    }
    
    public static InfoCocinero leer(RandomAccessFile raf) throws IOException
    {
        int codigo = raf.readInt();
        String tipo = raf.readUTF();
        
        return new InfoCocinero(codigo, tipo);
    }
}
