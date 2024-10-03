package a4ud1_alumno;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class CustomObjectOutputStream extends ObjectOutputStream
{
    public CustomObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }

    @Override
    protected void writeStreamHeader() {}
}
