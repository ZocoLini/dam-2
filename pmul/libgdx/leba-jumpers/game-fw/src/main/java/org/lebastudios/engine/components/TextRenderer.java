package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

public class TextRenderer extends Component
{
    @Setter @Getter private String text;
    private BitmapFont font;

    @Override
    public void onStart()
    {
        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        font.draw(batch, text, getGameObject().getTransform().getPosition().x, getGameObject().getTransform().getPosition().y);
    }

    @Override
    public void onDispose()
    {
        font.dispose();
    }
}
