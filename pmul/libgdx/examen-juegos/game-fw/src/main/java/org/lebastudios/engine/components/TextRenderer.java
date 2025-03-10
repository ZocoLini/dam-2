package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

public class TextRenderer extends Component
{
    @Getter private String text = "";
    @Setter private Vector2 offset = new Vector2(0, 0);

    private final BitmapFont font = new BitmapFont();
    private final GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void onStart()
    {
        font.setColor(Color.WHITE);
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        float x = getGameObject().getTransform().getPosition().x - glyphLayout.width / 2 + offset.x;
        float y = getGameObject().getTransform().getPosition().y - glyphLayout.height / 2 + glyphLayout.height + offset.y;

        font.draw(batch, glyphLayout, x, y);
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
