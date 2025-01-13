package org.lebastudios.fxcomponents.apparience;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.SneakyThrows;
import org.lebastudios.fxcomponents.MainStageController;
import org.lebastudios.fxcomponents.language.LangFileLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class ImageLoader
{
    private static final Map<String, WeakReference<Image>> loadedIcons = new WeakHashMap<>();
    private static final Map<String, Image> loadedTextures = new HashMap<>();
    private static final Map<String, WeakReference<Image>> loadedSavedImages = new WeakHashMap<>();

    public static Image getIcon(String iconName)
    {
        Image image;
        synchronized (loadedIcons)
        {
            image = getImage(iconName, loadedIcons);
        }
        if (image != null) return image;

        image = loadImage(iconName, ImageType.ICON);

        if (image == null) return getIcon("icon-not-found.png");

        synchronized (loadedIcons)
        {
            loadedIcons.put(iconName, new WeakReference<>(image));
        }
        
        return image;
    }

    public synchronized static Image getTexture(String textureName)
    {
        Image image = loadedTextures.get(textureName);

        if (image != null) return image;

        image = loadImage(textureName, ImageType.TEXTURE);

        if (image == null) return getTexture("texture-not-found.png");

        loadedTextures.put(textureName, image);
        return image;
    }

    public static Image getSavedImage(String filePath)
    {
        Image image;
        synchronized (loadedSavedImages)
        {
            image = getImage(filePath, loadedSavedImages);
        }
        if (image != null) return image;

        try (FileInputStream resource = new FileInputStream(filePath))
        {
            image = new Image(resource, 100, 100, true, true);
        }
        catch (IOException _) {}
        
        if (image == null) return getIcon("blank-image.png");

        synchronized (loadedSavedImages)
        {
            loadedSavedImages.put(filePath, new WeakReference<>(image));
        }
        return image;
    }

    private static Image getImage(String filePath, Map<String, WeakReference<Image>> loadedSavedImages)
    {
        final WeakReference<Image> reference = loadedSavedImages.get(filePath);

        if (reference != null) return reference.get();
        return null;
    }

    @SneakyThrows
    private synchronized static Image loadImage(String iconName, ImageType imageType)
    {
        var folder = imageType == ImageType.ICON ? "icons/" : "textures/";

        try (var resource = MainStageController.class.getResourceAsStream(folder + iconName))
        {
            if (resource == null) return null;

            return new Image(resource);
        }
    }

    private enum ImageType
    {
        ICON,
        TEXTURE
    }
    
    private static boolean imageChooserIsOpen = false;
    
    public synchronized static ImageChooserResult showImageChooser(Window owner)
    {
        if (imageChooserIsOpen) return null;
        
        var fileChooser = new FileChooser();

        fileChooser.setTitle(LangFileLoader.getTranslation("title.imagechooser"));
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg")
        );

        imageChooserIsOpen = true;
        var imageFile = fileChooser.showOpenDialog(owner);
        imageChooserIsOpen = false;
        
        if (imageFile == null) return null;
        
        try (var inputStream = new FileInputStream(imageFile))
        {
            var image = new Image(inputStream);
            if (image.isError()) return null;
            
            return new ImageChooserResult(imageFile, image);
        }
        catch (IOException e)
        {
            return null;
        }
    }    
    
    public record ImageChooserResult(File imageFile, Image image) { }
}
