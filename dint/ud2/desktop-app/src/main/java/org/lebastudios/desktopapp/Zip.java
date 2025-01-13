package org.lebastudios.desktopapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip
{
    public static void createZip(File source, File destination)
    {
        try (var zos = new ZipOutputStream(new FileOutputStream(destination)))
        {
            var buffer = new byte[1024];
            var files = source.listFiles();
            assert files != null;
            for (var file : files)
            {
                try (var fis = new FileInputStream(file))
                {
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    int length;
                    while ((length = fis.read(buffer)) > 0)
                    {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
                catch (Exception _) {}
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to create zip file.");
        }
    }
}
