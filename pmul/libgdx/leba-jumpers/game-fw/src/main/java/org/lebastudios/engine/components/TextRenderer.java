package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;

public class TextRenderer extends Component
{
    @Getter private String text;
    private BitmapFont font = new BitmapFont();
    private GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void onStart()
    {
        font.setColor(Color.BLACK);
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        font.draw(batch, glyphLayout,
            getGameObject().getTransform().getPosition().x - glyphLayout.width / 2,
            getGameObject().getTransform().getPosition().y - glyphLayout.height / 2
        );
    }

    public void setText(String text)
    {
        this.text = text;
        glyphLayout.setText(font, text);
    }

    @Override
    public void onDispose()
    {
        font.dispose();
    }
}
