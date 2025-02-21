package org.lebastudios.examen.menu;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.BoxShape;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.TextRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.examen.ExamenGameAdapter;
import org.lebastudios.examen.GameState;
import org.lebastudios.examen.game.GameScene;
import org.lebastudios.examen.world.WorldConfig;

import java.util.function.Consumer;

public class MainMenuScene extends Scene
{
    private final Consumer<Integer> enterGame = (difficulty) ->
    {
        GameState.getInstance().setDifficulty(difficulty);
        ExamenGameAdapter.getInstance().setScene(new GameScene());

        InputManager.getInstance().clearKeyDownListeners();
    };

    @Override
    protected void setup()
    {
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(1), Input.Keys.NUM_1, Input.Keys.NUMPAD_1);
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(2), Input.Keys.NUM_2, Input.Keys.NUMPAD_2);
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(3), Input.Keys.NUM_3, Input.Keys.NUMPAD_3);
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(4), Input.Keys.NUM_4, Input.Keys.NUMPAD_4);
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(5), Input.Keys.NUM_5, Input.Keys.NUMPAD_5);
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(6), Input.Keys.NUM_6, Input.Keys.NUMPAD_6);
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(7), Input.Keys.NUM_7, Input.Keys.NUMPAD_7);
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(8), Input.Keys.NUM_8, Input.Keys.NUMPAD_8);
        InputManager.getInstance().addKeyDownListener(() -> enterGame.accept(9), Input.Keys.NUM_9, Input.Keys.NUMPAD_9);

        GameObject title = new GameObject(new Transform(0, WorldConfig.WORLD_HEIGHT / 4f, 0));

        BoxShape boxShape = new BoxShape();
        boxShape.setWidth(WorldConfig.WORLD_WIDTH);
        boxShape.setHeight(WorldConfig.WORLD_HEIGHT / 2f);
        boxShape.setFilled(true);
        boxShape.setColor(Color.YELLOW);
        title.addComponent(boxShape);

        TextRenderer textRenderer = new TextRenderer();
        textRenderer.setText("Jugar (1...9)");
        title.addComponent(textRenderer);

        this.addGameObject(title);

        GameObject record = new GameObject(new Transform(0, -WorldConfig.WORLD_HEIGHT / 4f, 0));

        boxShape = new BoxShape();
        boxShape.setWidth(WorldConfig.WORLD_WIDTH);
        boxShape.setHeight(WorldConfig.WORLD_HEIGHT / 2f);
        boxShape.setFilled(true);
        boxShape.setColor(Color.GREEN);
        record.addComponent(boxShape);

        textRenderer = new TextRenderer();
        textRenderer.setText(GameState.getInstance().getScoreActualDifficulty() + "");
        title.addComponent(textRenderer);

        this.addGameObject(record);
    }

    @Override
    public void hide()
    {
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
