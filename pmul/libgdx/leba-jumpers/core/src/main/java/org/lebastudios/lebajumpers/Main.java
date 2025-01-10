package org.lebastudios.lebajumpers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter
{
    private SpriteBatch batch;

    private float x = 20;

    private Animation<Texture> runAnimation;
    private Animation<Texture> idleAnimation;
    private int direccion = 1;
    private int celerity = 0;

    private float deltaTime = 0;
    private float elapsedTime = 0;
    private long startTime = 0;

    @Override
    public void create()
    {
        startTime = System.currentTimeMillis();

        Texture[] runTextures = new Texture[8];
        for (int i = 0; i < 8; i++)
        {
            runTextures[i] = new Texture("warrior/Individual Sprite/Run/Warrior_Run_" + (i + 1) + ".png");
        }

        Texture[] idleTextures = new Texture[6];
        for (int i = 0; i < 6; i++)
        {
            idleTextures[i] = new Texture("warrior/Individual Sprite/idle/Warrior_Idle_" + (i + 1) + ".png");
        }

        runAnimation = new Animation<>(0.1f, runTextures);
        idleAnimation = new Animation<>(0.2f, idleTextures);

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(InputManager.getInstance());

        InputManager.getInstance().onAPress = () ->
        {
            direccion += -2;
            celerity = 100;
        };
        InputManager.getInstance().onDPress = () ->
        {
            direccion += 2;
            celerity = 100;
        };
        InputManager.getInstance().onAReleased = () ->
        {
            celerity = 0;
        };
        InputManager.getInstance().onDReleased = () ->
        {
            celerity = 0;
        };
    }

    @Override
    public void render()
    {
        boolean flipped;

        if (direccion > 0)
        {
            flipped = false;
            direccion = 1;
        }
        else
        {
            flipped = true;
            direccion = -1;
        }

        float newElapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        deltaTime = newElapsedTime - elapsedTime;
        elapsedTime = newElapsedTime;

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();

        final float xIncrement = celerity * direccion * deltaTime;
        final Texture keyFrame = (xIncrement == 0 ? idleAnimation : runAnimation).getKeyFrame(elapsedTime, true);

        if (keyFrame != null)
        {
            x = x > 640 - keyFrame.getWidth() && direccion > 0 || x < 0 && direccion < 0 ? x : x + xIncrement;

            batch.draw(keyFrame, x, 210, keyFrame.getWidth() * (flipped ? -1 : 1), keyFrame.getHeight());
        }

        batch.end();
    }

    @Override
    public void dispose()
    {
        batch.dispose();

        for (var variable : idleAnimation.getKeyFrames())
        {
            variable.dispose();
        }

        for (var variable : runAnimation.getKeyFrames())
        {
            variable.dispose();
        }
    }
}
