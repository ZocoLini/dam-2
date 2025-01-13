package org.lebastudios.desktopapp.locale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LangFileLoader
{
    private static final Map<String, String> translations = new HashMap<>();

    public static void loadLang(Locale locale, Class<?> pluginClass)
    {
        var resource = pluginClass.getResourceAsStream("languagesData.csv");

        if (resource == null)
        {
            System.err.println("No locale file found in " + pluginClass.getName());
            return;
        }

        computeTranslations(locale, resource);
    }

    private static void computeTranslations(Locale locale, InputStream fileToCompute)
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileToCompute)))
        {
            int index = searchColumn(reader.readLine(), locale);

            String line;
            while ((line = reader.readLine()) != null)
            {
                var columns = line.split(",");

                translations.put(
                        columns[0],
                        index == -1 ? columns[1] : columns[index]
                );
            }
        }
        catch (IOException exception)
        {
            System.err.println("Error reading file languagesData.csv");
        }
    }

    private static int searchColumn(String header, Locale locale)
    {
        String[] columns = header.split(",");
        var langCode = locale.getLanguage() + "_" + locale.getCountry();
        int alternativeIndex = -1;

        for (int i = 1; i < columns.length; i++)
        {
            if (columns[i].equals(langCode)) return i;
            if (columns[i].startsWith(locale.getLanguage())) alternativeIndex = i;
        }

        return alternativeIndex;
    }

    public static String getTranslation(String key)
    {
        return translations.getOrDefault(key, key);
    }
}
