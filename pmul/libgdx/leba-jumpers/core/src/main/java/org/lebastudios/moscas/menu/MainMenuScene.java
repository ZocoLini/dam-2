package org.lebastudios.moscas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import org.lebastudios.engine.Animation;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.Animator;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.TextRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.engine.events.IEventMethod;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.moscas.InsectosGameAdapter;
import org.lebastudios.moscas.config.WorldConfig;
import org.lebastudios.moscas.game.GameScene;
import org.lebastudios.moscas.game.GameState;
import org.lebastudios.moscas.persistence.GameData;

public class MainMenuScene extends Scene
{
    // TODO: Make this only get called once and the show method gets called every time the scene is set as the main one
    //  also create factories for the game objects making the code more readable
    @Override
    protected void setup()
    {
        GameObject title = new GameObject(new Transform(0, getCameraHeight() / 2 - 100, 0));
        TextRenderer titleRenderer = new TextRenderer();
        titleRenderer.setText("Moscas");

        title.addComponent(titleRenderer);

        this.addGameObject(title);

        GameObject imagenInsectos = new GameObject(new Transform(0, 0, 0));
        imagenInsectos.getTransform().setScale(new Vector3(1, 1, 1));
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

        this.addGameObject(imagenInsectos);

        InputManager.getInstance().addKeyDownListener(Input.Keys.F, exitGameListener);
        InputManager.getInstance().addKeyDownListener(Input.Keys.R, resetGameListener);
        InputManager.getInstance().addKeyDownListener(Input.Keys.NUM_9, startGameListener);
    }

    private final IEventMethod startGameListener = new IEventMethod() {
        @Override
        public void invoke()
        {
            GameState.getInstance().setNumInsectos(9);

            InputManager.getInstance().removeKeyDownListener(Input.Keys.NUM_9, this);
            InputManager.getInstance().removeKeyDownListener(Input.Keys.F, exitGameListener);
            InputManager.getInstance().removeKeyDownListener(Input.Keys.R, resetGameListener);

            InsectosGameAdapter.getInstance().setScene(GameScene.getInstance());
        }
    };

    private final IEventMethod exitGameListener = () -> Gdx.app.exit();

    private final IEventMethod resetGameListener = () -> GameData.getInstance().reset();

    @Override
    public void hide()
    {
        dispose();
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
