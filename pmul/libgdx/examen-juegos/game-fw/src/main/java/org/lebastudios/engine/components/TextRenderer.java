package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;

public class TextRenderer extends Component
{
    @Getter private String text = "";
    private final BitmapFont font = new BitmapFont();
    private final GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void onRender(SpriteBatch batch)
    {
        font.draw(batch, glyphLayout,
            getGameObject().getTransform().getPosition().x - glyphLayout.width / 2,
            getGameObject().getTransform().getPosition().y - glyphLayout.height / 2 + glyphLayout.height
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
