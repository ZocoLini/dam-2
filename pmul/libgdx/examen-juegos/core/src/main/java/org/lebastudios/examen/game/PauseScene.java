package org.lebastudios.examen.game;

import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.TextRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.examen.ExamenGameAdapter;
import org.lebastudios.examen.world.WorldConfig;

public class PauseScene extends Scene
{
    @Override
    protected void setup()
    {
        InputManager.getInstance().getOnAnyKeyDown().addLazy(() -> ExamenGameAdapter.getInstance().setScene(GameScene.getInstance()));

        GameObject pause = new GameObject(new Transform(0, 0, 0));
        TextRenderer textRenderer = new TextRenderer();
        textRenderer.setText("PAUSE");
        pause.addComponent(textRenderer);

        this.addGameObject(pause);
    }

    @Override
    public void hide()
    {
        super.hide();
        this.dispose();
    }

    @Override
    public float getCameraWidth()
    {
        return WorldConfig.WORLD_WIDTH;
    }

    @Override
    public float getCameraHeight()
    {
        return WorldConfig.WORLD_HEIGHT;
    }
}
