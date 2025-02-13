package org.lebastudios.finger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.*;
import org.lebastudios.engine.coroutine.WaitForSeconds;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.finger.config.WorldConfig;

import java.util.function.Supplier;

public class FingerController extends Component
{
    private int life = 3;
    private int superSpaces = 3;
    private int direccion = 0;
    private float velocidad = 60;
    private int numDisparos = 3;

    private Transform transform;

    @Override
    public void onStart()
    {
        transform = this.getTransform();

        InputManager.getInstance().addKeyDownListener(() ->
        {
            direccion = 1;
        }, Input.Keys.W, Input.Keys.UP);
        InputManager.getInstance().addKeyUpListener(() ->
        {
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                direccion = -1;
            }
            else
            {
                direccion = 0;
            }

        }, Input.Keys.W, Input.Keys.UP);
        InputManager.getInstance().addKeyDownListener(() ->
        {
            direccion = -1;
        }, Input.Keys.S, Input.Keys.DOWN);
        InputManager.getInstance().addKeyUpListener(() ->
        {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                direccion = 1;
            }
            else
            {
                direccion = 0;
            }
        }, Input.Keys.S, Input.Keys.DOWN);

        InputManager.getInstance().addKeyDownListener(Input.Keys.SPACE, this::shoot);
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        if (transform.getPosition().y >= WorldConfig.HEIGHT / 2f && direccion == 1) return;
        if (transform.getPosition().y <= -WorldConfig.HEIGHT / 2f && direccion == -1) return;

        transform.translate(
            0,
            velocidad * direccion * deltaTime,
            0
        );
    }

    private void shoot()
    {
        this.startCoroutine(new WaitForSeconds(0.25f) {
            int i = 0;

            @Override
            public Supplier<Boolean> getAction()
            {
                return () ->
                {
                    i++;

                    GameObject bullet = BulletFactory.createBullet(FingerController.this.transform);
                    FingerController.this.getGameObject().getScene().addGameObject(bullet);

                    return i < FingerController.this.numDisparos;
                };
            }
        });
    }

    @Override
    public void onTrigger2DEnter(Collider2D other)
    {
        if (other.getGameObject().getMetadata().getTag().equals("Enemy"))
        {
            life--;
            other.getGameObject().destroy();

            if (life == 0)
            {
                this.getGameObject().destroy();
            }
        }
    }

    private static class BulletFactory
    {
        public static GameObject createBullet(Transform transform)
        {
            GameObject bullet = new GameObject(new Transform(transform.getPosition().x, transform.getPosition().y, 0));
            bullet.getMetadata().setTag("Bullet");

            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(4);
            bullet.addComponent(circleShape);

            CircleCollider2D circleCollider2D = new CircleCollider2D();
            circleCollider2D.setRadius(4.5f);
            bullet.addComponent(circleCollider2D);

            circleCollider2D.trackCollider(EnemyGeneratorController.getColliders());

            bullet.addComponent(new BulletController());
            return bullet;
        }
    }
}
