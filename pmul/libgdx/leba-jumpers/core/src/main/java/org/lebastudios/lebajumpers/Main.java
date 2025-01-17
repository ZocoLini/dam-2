package org.lebastudios.lebajumpers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter
{
    private SpriteBatch batch;
    private BitmapFont timeFont;

    private float x = 20;

    private Animation<Texture> runAnimation;
    private Animation<Texture> idleAnimation;
    private int direccion = 0;
    private final static int celerity = 100;

    private float deltaTime = 0;
    private float stateTime = 0;
    private long startTime = 0;
    private float timeSurvived;

    private Timer timer;
    private boolean pressedAtTime = false;
    private final Color winColor = Color.RED;
    private Color actualColor = Color.BLUE;

    private boolean alive = true;

    private enum Color
    {
        RED,
        GREEN,
        BLUE;

        public static Color getRandomWithoutRepeating(Color previousColor)
        {
            return Arrays.stream(Color.values())
                .filter(color -> color != previousColor)
                .toList()
                .get((int) (Math.random() * Color.values().length - 1));
        }
    }

    @Override
    public void create()
    {
        float timeBetweenColor = Math.max(1.5f - (timeSurvived / 10), 0.40f);

        timer = new Timer(() ->
        {
            Color previousColor = actualColor;

            actualColor = alive ? Color.getRandomWithoutRepeating(previousColor) : actualColor;

            if (previousColor == Color.RED && !pressedAtTime)
            {
                endGame();
            }

            pressedAtTime = false;
        }, timeBetweenColor);
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
        timeFont = new BitmapFont();

        Gdx.input.setInputProcessor(InputManager.getInstance());

        InputManager.getInstance().onSpacePress = () ->
        {
            if (!alive) return;

            if (actualColor != winColor)
            {
                endGame();
                return;
            }

            pressedAtTime = true;
            timer.call();
        };
        InputManager.getInstance().onAPress = () -> direccion = -1;
        InputManager.getInstance().onDPress = () -> direccion = 1;
        InputManager.getInstance().onAReleased = () -> direccion = Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0;
        InputManager.getInstance().onDReleased = () -> direccion = Gdx.input.isKeyPressed(Input.Keys.A) ? -1 : 0;
    }

    private boolean flipped;

    @Override
    public void render()
    {
        if (direccion > 0)
        {
            flipped = false;
        }
        else if (direccion < 0)
        {
            flipped = true;
        }

        float newElapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        deltaTime = newElapsedTime - stateTime;
        stateTime = newElapsedTime;

        if (alive)
        {
            timer.increse(deltaTime);
        }

        switch (actualColor)
        {
            case RED ->  ScreenUtils.clear(1, 0, 0, 1f);
            case GREEN ->  ScreenUtils.clear(0, 1, 0, 1f);
            case BLUE ->  ScreenUtils.clear(1, 0, 1, 1f);
            default -> throw new IllegalStateException();
        }

        batch.begin();

        final float xIncrement = celerity * direccion * deltaTime;
        final Texture keyFrame = (xIncrement == 0 ? idleAnimation : runAnimation).getKeyFrame(stateTime, true);

        if (keyFrame != null)
        {
            x = x > 640 - keyFrame.getWidth() && direccion > 0 || x < 0 && direccion < 0 ? x : x + xIncrement;

            batch.draw(keyFrame, x, 210, keyFrame.getWidth() * (flipped ? -1 : 1), keyFrame.getHeight());
        }

        if (alive)
        {
            timeFont.draw(batch, (int) stateTime + "",
                640 / 2.0f,
                480 - timeFont.getLineHeight()
            );
        }
        else
        {
            timeFont.draw(batch, "Moriste despues de: " + (int) timeSurvived,
                (640 / 2.0f) - timeFont.getRegion().getRegionWidth() / 4.0f,
                480 - timeFont.getLineHeight()
            );
        }

        batch.end();
    }

    private void endGame()
    {
        timeSurvived = stateTime;
        alive = false;
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
