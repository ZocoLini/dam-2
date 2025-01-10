package org.lebastudios.desktopapp.locale;

import org.lebastudios.desktopapp.DesktopApplication;
import org.lebastudios.desktopapp.Launcher;

import java.util.Locale;

public class LangLoader
{
    private static void reloadLangs()
    {
        LangLoader.loadLang(Launcher.class, AppLocale.getActualLocale());

        LangLoader.loadLang(DesktopApplication.class, AppLocale.getActualLocale());
    }
    
    public static void loadLang(Class<?> langClass, Locale locale)
    {
        Thread langFileThread = new Thread(() -> LangFileLoader.loadLang(locale, langClass));
        Thread langBundleThread = new Thread(() -> LangBundleLoader.loadLang(langClass, locale));
        
        langFileThread.start();
        langBundleThread.start();

        try
        {
            langFileThread.join();
            langBundleThread.join();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
