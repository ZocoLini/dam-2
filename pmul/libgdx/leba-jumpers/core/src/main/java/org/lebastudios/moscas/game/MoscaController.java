package org.lebastudios.moscas.game;

import com.badlogic.gdx.graphics.Texture;
import lombok.Setter;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.TextRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.moscas.InsectosGameAdapter;
import org.lebastudios.moscas.config.WorldConfig;
import org.lebastudios.moscas.menu.MainMenuScene;
import org.lebastudios.moscas.persistence.GameData;

public class MoscaController extends Component
{
    private int direccion;
    private int velocidad = 60;
    private Transform transform;
    private int timesClicked = 0;
    private SpriteRenderer spriteRenderer;
    private float elapsedTime = 0;
    @Setter private TextRenderer insectsKilledText;

    @Override
    public void onStart()
    {
        transform = this.getGameObject().getTransform();
        spriteRenderer = this.getGameObject().getComponent(SpriteRenderer.class);

        changeDirection();
    }

    private void changeDirection()
    {
        direccion = Math.random() > 0.5f ? 1 : -1;
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        elapsedTime += deltaTime;

        if (transform.getPosition().x > WorldConfig.WIDTH / 2f) direccion = -1;
        if (transform.getPosition().x < - WorldConfig.WIDTH / 2f) direccion = 1;

        spriteRenderer.flipX(direccion == -1);

        this.getGameObject().getTransform().translate(
            velocidad * direccion * deltaTime,
            0,
            0
        );
    }

    @Override
    public void onClicked()
    {
        timesClicked++;

        if (GameState.getInstance().getNumInsectos() <= timesClicked)
        {
            spriteRenderer.setSpriteTexture(new Texture("insects/dead.png"));
            this.getGameObject().removeComponent(this);
            InsectosGameAdapter.getInstance().setScene(new MainMenuScene());
            GameData.getInstance().registerNewScore((int) elapsedTime);
            return;
        }

        changeDirection();

        insectsKilledText.setText(insectsKilledText.getText() + "  I");
        spriteRenderer.setSpriteTexture(new Texture("insects/insect_" + (timesClicked + 1) + ".png"));
    }
}
