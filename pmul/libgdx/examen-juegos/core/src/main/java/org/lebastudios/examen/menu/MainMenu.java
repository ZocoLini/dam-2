package org.lebastudios.examen.menu;

import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.CircleShape;
import org.lebastudios.engine.components.TextRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.examen.world.WorldConfig;

public class MainMenu extends Scene
{
    @Override
    protected void setup()
    {
        GameObject title = new GameObject(new Transform(0, 0, 0));
        TextRenderer textRenderer = new TextRenderer();
        textRenderer.setText("Main Menu");
        title.addComponent(textRenderer);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(50);
        title.addComponent(circleShape);

        this.addGameObject(title);
    }

    @Override
    protected float getCameraWidth()
    {
        return WorldConfig.WORLD_WIDTH;
    }

    @Override
    protected float getCameraHeight()
    {
        return WorldConfig.WORLD_HEIGHT;
    }
}
