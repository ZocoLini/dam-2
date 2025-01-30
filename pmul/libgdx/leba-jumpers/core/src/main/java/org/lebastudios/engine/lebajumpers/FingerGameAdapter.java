package org.lebastudios.engine.lebajumpers;

import org.lebastudios.engine.GameAdapter;
import org.lebastudios.engine.Scene;

public class FingerGameAdapter extends GameAdapter
{
    @Override
    protected Scene createFirstScene()
    {
        return new GameScene();
    }
}
