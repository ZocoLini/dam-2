package org.lebastudios.examen.game;

import org.lebastudios.engine.Scene;
import org.lebastudios.examen.world.WorldConfig;

public class PauseScene extends Scene
{
    @Override
    protected void setup()
    {

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
