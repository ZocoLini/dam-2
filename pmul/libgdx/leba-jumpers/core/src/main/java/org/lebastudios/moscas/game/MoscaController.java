package org.lebastudios.moscas.game;

import com.badlogic.gdx.graphics.Texture;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.moscas.InsectosGameAdapter;
import org.lebastudios.moscas.config.WorldConfig;
import org.lebastudios.moscas.menu.MainMenuScene;

public class MoscaController extends Component
{
    private int direccion;
    private int velocidad = 60;
    private Transform transform;
    private int timesClicked = 0;
    private SpriteRenderer spriteRenderer;

    @Override
    public void onStart()
    {
        direccion = Math.random() > 0.5f ? 1 : -1;
        transform = this.getGameObject().getTransform();
        spriteRenderer = this.getGameObject().getComponent(SpriteRenderer.class);
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        if (transform.getPosition().x > WorldConfig.WIDTH / 2f) direccion = -1;
        if (transform.getPosition().x < - WorldConfig.WIDTH / 2f) direccion = 1;


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
            System.out.println("Terminada run");
            spriteRenderer.setSpriteTexture(new Texture("insects/dead.png"));
            this.getGameObject().removeComponent(this);
            InsectosGameAdapter.getInstance().setScene(new MainMenuScene());
            return;
        }

        spriteRenderer.setSpriteTexture(new Texture("insects/insect_" + (timesClicked + 1) + ".png"));
    }
}
