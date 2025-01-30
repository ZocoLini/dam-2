package org.lebastudios.moscas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import org.lebastudios.engine.Animation;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.Animator;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.TextRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.moscas.InsectosGameAdapter;
import org.lebastudios.moscas.config.WorldConfig;
import org.lebastudios.moscas.game.GameScene;
import org.lebastudios.moscas.game.GameState;
import org.lebastudios.moscas.persistence.GameData;

public class MainMenuScene extends Scene
{
    @Override
    protected void setup()
    {
        GameObject title = new GameObject(new Transform(0, 10, 0));
        TextRenderer titleRenderer = new TextRenderer();
        titleRenderer.setText("Moscas");

        title.addComponent(titleRenderer);

        this.addGameObject(title);

        GameObject imagenInsectos = new GameObject(new Transform(0, 0, 0));
        SpriteRenderer spriteRenderer = new SpriteRenderer();
        imagenInsectos.addComponent(spriteRenderer);

        Animator animator = new Animator();
        final var animation = new Animation<>(
            spriteRenderer::setSpriteTexture,
            0.1f,
            new Texture("insects/insect_1.png"),
            new Texture("insects/insect_2.png"),
            new Texture("insects/insect_3.png"),
            new Texture("insects/insect_4.png"),
            new Texture("insects/insect_5.png"),
            new Texture("insects/insect_6.png"),
            new Texture("insects/insect_7.png"),
            new Texture("insects/insect_8.png"),
            new Texture("insects/insect_9.png")
        );
        animation.setLooping(true);
        animator.addAnimation(animation);
        imagenInsectos.addComponent(animator);
        imagenInsectos.addComponent(new ImageMovementController());

        this.addGameObject(imagenInsectos);

        InputManager.getInstance().addKeyDownListener(Input.Keys.F, () -> Gdx.app.exit());
        InputManager.getInstance().addKeyDownListener(Input.Keys.R, () -> GameData.getInstance().reset());
        InputManager.getInstance().addKeyDownListener(Input.Keys.NUM_9, () -> {
            GameState.getInstance().setNumInsectos(9);
            InsectosGameAdapter.getInstance().setScene(new GameScene());
        });
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
