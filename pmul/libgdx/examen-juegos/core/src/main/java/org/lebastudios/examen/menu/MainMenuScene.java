package org.lebastudios.examen.menu;

import com.badlogic.gdx.Input;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.*;
import org.lebastudios.engine.events.IEventMethod;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.examen.ExamenGameAdapter;
import org.lebastudios.examen.game.GameScene;
import org.lebastudios.examen.world.WorldConfig;

public class MainMenuScene extends Scene
{
    private final IEventMethod enterGame = () -> {
        ExamenGameAdapter.getInstance().setScene(new GameScene());
        InputManager.getInstance().removeKeyDownListener(Input.Keys.SPACE, MainMenuScene.this.enterGame);
    };

    @Override
    protected void setup()
    {
        InputManager.getInstance().addKeyDownListener(Input.Keys.SPACE, enterGame);

        GameObject title = new GameObject(new Transform(0, 0, 0));
        TextRenderer textRenderer = new TextRenderer();
        textRenderer.setText("Main Menu");
        title.addComponent(textRenderer);

        BoxShape boxShape = new BoxShape();
        boxShape.setWidth(200);
        boxShape.setHeight(50);
        title.addComponent(boxShape);

        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setRadius(30);
        title.addComponent(circleCollider2D);

        title.addComponent(new Component() {
            @Override
            public void onClicked()
            {
                System.out.println("Clicked");
            }
        });

        this.addGameObject(title);
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
