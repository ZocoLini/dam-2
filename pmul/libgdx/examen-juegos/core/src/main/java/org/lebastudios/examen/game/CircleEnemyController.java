package org.lebastudios.examen.game;

import com.badlogic.gdx.graphics.Color;
import org.lebastudios.engine.components.CircleShape;

public class CircleEnemyController extends EnemyController
{
    public static final String GO_NAME = "enemy-circle";

    @Override
    public void pintarRojo()
    {
        this.getGameObject().getComponent(CircleShape.class).setColor(Color.RED);
    }
}
