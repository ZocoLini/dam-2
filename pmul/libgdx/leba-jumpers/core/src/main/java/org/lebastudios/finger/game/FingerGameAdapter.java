package org.lebastudios.finger.game;

import org.lebastudios.engine.GameAdapter;
import org.lebastudios.engine.Scene;

public class FingerGameAdapter extends GameAdapter
{
    @Override
    protected Scene getFirstScene()
    {
        return new MainScene();
    }
}
