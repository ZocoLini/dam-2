package org.lebastudios.moscas;

import lombok.Getter;
import org.lebastudios.engine.GameAdapter;
import org.lebastudios.engine.Scene;
import org.lebastudios.moscas.menu.MainMenuScene;

public class InsectosGameAdapter extends GameAdapter
{
    @Getter private static InsectosGameAdapter instance;

    public InsectosGameAdapter()
    {
        super();

        if (instance != null) throw new IllegalStateException("GameAdapter instance already exists");

        instance = this;
    }

    @Override
    protected Scene getFirstScene()
    {
        return new MainMenuScene();
    }
}
