package org.lebastudios.examen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        boxShape.setWidth(900);
        boxShape.setHeight(50);
        title.addComponent(boxShape);

        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setRadius(30);
        title.addComponent(circleCollider2D);

        // TextureAtlas atals = new TextureAtlas(Gdx.files.internal("sprites.atlas"));
        // TextureRegion region = atals.findRegion("player");
        // SpriteRenderer spriteRenderer = new SpriteRenderer();
        // spriteRenderer.setSpriteTexture(region.getTexture());

        title.addComponent(new Component() {
            @Override
            public void onClicked()
            {
                System.out.println("Clicked");
            }
        });

        Preferences pref = Gdx.app.getPreferences("Examen");
        pref.putInteger("score", 0);
        pref.flush();

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
