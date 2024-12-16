package org.lebastudios.aplicacioncompleja.language;

import javafx.fxml.FXMLLoader;
import org.lebastudios.aplicacioncompleja.Launcher;

import java.util.*;

public class LangBundleLoader
{
    private static LangBundleLoader instance;
    private final Map<String, ResourceBundle> resourceBundles = new HashMap<>();

    private LangBundleLoader(String baseName, Locale locale)
    {
        addLangBundle(baseName, locale);
    }

    private void addLangBundle(String baseName, Locale locale)
    {
        ResourceBundle resourceBundle;

        try
        {
            resourceBundle = ResourceBundle.getBundle(
                    baseName,
                    locale,
                    Launcher.class.getClassLoader()
            );
        }
        catch (MissingResourceException exception)
        {
            resourceBundle = ResourceBundle.getBundle(
                    baseName,
                    Locale.of("en", "US"),
                    Launcher.class.getClassLoader()
            );
        }
        
        resourceBundles.put(baseName, resourceBundle);
    }

    public static void addLangBundle(FXMLLoader loader)
    {
        loader.setResources(getInstance().resourceBundles.get(Launcher.class.getPackageName() + ".lang"));
    }

    private static LangBundleLoader getInstance()
    {
        if (instance == null)
        {
            var locale = Locale.getDefault();

            instance = new LangBundleLoader("org.lebastudios.aplicacioncompleja.lang", locale);
        }

        return instance;
    }

    public String getString(String key)
    {
        for (var resourceBundle : resourceBundles.values())
        {
            try
            {
                return resourceBundle.getString(key);
            }
            catch (MissingResourceException _) {}
        }

        System.err.println("Key not found: " + key);

        return key;
    }
}
