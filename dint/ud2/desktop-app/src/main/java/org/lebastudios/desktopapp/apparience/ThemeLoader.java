package org.lebastudios.desktopapp.apparience;

import javafx.scene.Scene;
import lombok.SneakyThrows;
import org.lebastudios.desktopapp.DesktopApplication;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ThemeLoader
{
    private static final Set<Scene> scenesInstantiated = new HashSet<>();

    public static void reloadThemes()
    {
        for (var scene : scenesInstantiated)
        {
            scene.getStylesheets().removeLast();
            addActualTheme(scene);
        }
    }

    @SneakyThrows
    public static Scene addActualTheme(Scene scene)
    {
        removeRemovedScenes();

        scenesInstantiated.add(scene);

        String themeCss = new File(
                getThemesDir() + "/" + "cupertino-light" + "/theme.css")
                .toURI().toURL().toExternalForm();
        scene.getStylesheets().add(themeCss);
        return scene;
    }

    private static void removeRemovedScenes()
    {
        scenesInstantiated.removeIf(scene -> !scene.getWindow().isShowing());
    }

    public static File getThemesDir()
    {
        return new File("styles");
    }
}
