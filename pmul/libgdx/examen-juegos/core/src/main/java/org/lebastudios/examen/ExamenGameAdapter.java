package org.lebastudios.examen;

import org.lebastudios.engine.GameAdapter;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.physics.Physics2D;
import org.lebastudios.examen.menu.MainMenu;

public class ExamenGameAdapter extends GameAdapter
{
    @Override
    protected void onCreate()
    {
        Physics2D.getCollisionMatrix().addLayer("Bullet");
        Physics2D.getCollisionMatrix().addLayer("Enemy");
        Physics2D.getCollisionMatrix().addLayer("Player");

        Physics2D.getCollisionMatrix().setCollision("Bullet", "Enemy", true);
        Physics2D.getCollisionMatrix().setCollision("Player", "Enemy", true);
    }

    @Override
    protected Scene getFirstScene()
    {
        return new MainMenu();
    }
}
