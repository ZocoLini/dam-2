package org.lebastudios.examen;

import lombok.Getter;
import org.lebastudios.engine.GameAdapter;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.physics.Physics2D;
import org.lebastudios.examen.menu.MainMenuScene;

public class ExamenGameAdapter extends GameAdapter
{
    @Getter private static ExamenGameAdapter instance;

    public ExamenGameAdapter()
    {
        instance = this;
    }

    @Override
    protected void onCreate()
    {
        // Physics2D.getCollisionMatrix().addLayer("Bullet");
        // Physics2D.getCollisionMatrix().addLayer("Enemy");
        // Physics2D.getCollisionMatrix().addLayer("Player");
        //
        // Physics2D.getCollisionMatrix().setCollision("Bullet", "Enemy", true);
        // Physics2D.getCollisionMatrix().setCollision("Player", "Enemy", true);

        GameAdapter.DEBUG = true;
    }

    @Override
    protected Scene getFirstScene()
    {
        return new MainMenuScene();
    }
}
