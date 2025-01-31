package org.lebastudios.moscas.game;

import com.badlogic.gdx.graphics.Texture;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.BoxCollider2D;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.TextRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.moscas.config.WorldConfig;
import org.lebastudios.moscas.persistence.GameData;

public class GameScene extends Scene
{
    private static GameScene instance;

    public static GameScene getInstance()
    {
        if (instance == null) instance = new GameScene();

        return instance;
    }

    private final TextRenderer insectsKilledTextRenderer;
    private final TextRenderer recordTextRenderer;

    private GameScene() {
        GameObject recordText = new GameObject(new Transform(0, WorldConfig.HEIGHT / 2f - 25, 0), this);
        recordTextRenderer = new TextRenderer();
        recordText.addComponent(recordTextRenderer);

        this.addGameObject(recordText);

        GameObject insectsKilledText = new GameObject(new Transform(0, WorldConfig.HEIGHT / 2f - 50, 0), this);
        insectsKilledTextRenderer = new TextRenderer();
        insectsKilledText.addComponent(insectsKilledTextRenderer);

        this.addGameObject(insectsKilledText);
    }

    @Override
    protected void setup()
    {
        GameObject character = new GameObject(new Transform(0, 0, 0), this);

        SpriteRenderer spriteRenderer = new SpriteRenderer();
        spriteRenderer.setSpriteTexture(new Texture("insects/insect_1.png"));
        character.addComponent(spriteRenderer);
        BoxCollider2D collider2D = new BoxCollider2D();
        collider2D.setHeigth(50);
        collider2D.setWidth(50);
        character.addComponent(collider2D);
        MoscaController moscaController = new MoscaController();
        character.addComponent(moscaController);

        this.addGameObject(character);

        insectsKilledTextRenderer.setText("");
        moscaController.setInsectsKilledText(insectsKilledTextRenderer);

        final var numInsectos = GameState.getInstance().getNumInsectos();
        Integer recordSeconds = GameData.getInstance().getRecords().get(numInsectos);
        recordTextRenderer.setText(recordSeconds == null ? "Sin record" : "Record: " + recordSeconds + "s");
    }

    @Override
    protected float getCameraWidth()
    {
        return WorldConfig.WIDTH;
    }

    @Override
    protected float getCameraHeight()
    {
        return WorldConfig.HEIGHT;
    }
}
