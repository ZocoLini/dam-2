package org.lebastudios.examen.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.*;
import org.lebastudios.engine.coroutine.WaitForSeconds;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.examen.ExamenGameAdapter;
import org.lebastudios.examen.GameState;
import org.lebastudios.examen.menu.MainMenuScene;
import org.lebastudios.examen.world.WorldConfig;

import java.util.function.Supplier;

public class GameScene extends Scene
{
    public static final int INFO_DISPLAY_HEIGTH = 50;
    private static GameScene instance;

    public static GameScene getInstance()
    {
        if (instance == null) throw new IllegalStateException("Not yet created");

        return instance;
    }

    public GameScene()
    {
        instance = this;
    }

    @Override
    protected void setup()
    {
        GameObject player = new GameObject(new Transform(0, 0, 0));
        player.getMetadata().setName("player");

        CircleShape circleShape = new CircleShape();
        circleShape.setColor(Color.WHITE);
        circleShape.setFilled(true);
        player.addComponent(circleShape);

        BoxShape boxShape = new BoxShape();
        boxShape.setColor(Color.WHITE);
        boxShape.setFilled(true);
        player.addComponent(boxShape);

        for (int i = 0; i < 2; i++)
        {
            LineShape lineShape = new LineShape();
            lineShape.setColor(Color.WHITE);
            player.addComponent(lineShape);
        }

        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setLayer("player");
        player.addComponent(circleCollider2D);

        final PlayerController playerController = new PlayerController();
        player.addComponent(playerController);

        this.addGameObject(player);

        GameObject enemyGenerator = new GameObject(new Transform(0, 0, 0));
        enemyGenerator.addComponent(new EnemyGeneratorController());

        this.addGameObject(enemyGenerator);

        GameObject info = new GameObject(new Transform(
            0,
            -WorldConfig.WORLD_HEIGHT / 2f + INFO_DISPLAY_HEIGTH / 2f,
            0)
        );

        boxShape = new BoxShape();
        boxShape.setWidth(WorldConfig.WORLD_WIDTH);
        boxShape.setHeight(INFO_DISPLAY_HEIGTH);
        boxShape.setFilled(true);
        boxShape.setColor(Color.BLUE);
        info.addComponent(boxShape);

        TextRenderer timePlayed = new TextRenderer();
        timePlayed.setOffset(new Vector2(-150, 0));
        info.addComponent(timePlayed);
        info.addComponent(new Component()
        {
            @Override
            public void onUpdate(float deltaTime)
            {
                timePlayed.setText(
                    String.format(
                        "TIEMPO: %ds",
                        (int) GameState.getInstance().getGameTimeElapsed()
                    )
                );
            }
        });

        TextRenderer colisiones = new TextRenderer();
        info.addComponent(colisiones);
        info.addComponent(new Component()
        {
            private final int maxColisiones = GameState.getInstance().getDifficulty();

            @Override
            public void onUpdate(float deltaTime)
            {
                colisiones.setText(
                    String.format(
                        "COLISIONES: %d de %d",
                        maxColisiones - playerController.getLife(), maxColisiones
                    )
                );
            }
        });

        TextRenderer record = new TextRenderer();
        record.setOffset(new Vector2(150, 0));
        record.setText(String.format("RECORD: %ds", (int) GameState.getInstance().getScoreActualDifficulty()));
        info.addComponent(record);

        info.addComponent(new Component()
        {
            @Override
            public void onUpdate(float deltaTime)
            {
                if (GameState.getInstance().getGameTimeElapsed() > GameState.getInstance().getScoreActualDifficulty())
                {
                    info.removeComponent(this);
                    record.setText("Nuevo Record!");

                    // Parpadeo con nuevo record
                    this.getGameObject().getScene().startCoroutine(new WaitForSeconds(0.2f)
                    {
                        int i = 0;

                        @Override
                        public Supplier<Boolean> getAction()
                        {
                            return () ->
                            {
                                if (record.isEnabled())
                                {
                                    record.disable();
                                }
                                else
                                {
                                    record.enable();
                                }

                                return ++i < 6;
                            };
                        }
                    });
                }
            }
        });

        this.addGameObject(info);
    }

    public void goToMainMenu()
    {
        InputManager.getInstance().clearKeyDownListeners();
        InputManager.getInstance().clearKeyUpListeners();

        instance = null;

        ExamenGameAdapter.getInstance().setScene(new MainMenuScene());
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
