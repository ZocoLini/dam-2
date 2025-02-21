package org.lebastudios.examen.game;

import com.badlogic.gdx.graphics.Color;
import org.lebastudios.engine.components.LineShape;

public class CrossEnemyController extends EnemyController
{
    public static final String GO_NAME = "enemy-cross";

    @Override
    public void pintarRojo()
    {
        for (var shape : this.getGameObject().getComponents(LineShape.class))
        {
            shape.setColor(Color.RED);
        }
    }

    @Override
    public boolean rebota()
    {
        return false;
    }
}
