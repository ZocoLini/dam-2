package org.lebastudios.examen.game;

import com.badlogic.gdx.graphics.Color;
import org.lebastudios.engine.components.BoxShape;

public class BoxEnemyController extends EnemyController
{
    public static final String GO_NAME = "enemy-box";

    @Override
    public void pintarRojo()
    {
        this.getGameObject().getComponent(BoxShape.class).setColor(Color.RED);
    }
}
