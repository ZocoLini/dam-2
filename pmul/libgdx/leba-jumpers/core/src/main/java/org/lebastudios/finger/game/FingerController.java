package org.lebastudios.finger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.*;
import org.lebastudios.engine.coroutine.WaitForSeconds;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.engine.util.Pool;
import org.lebastudios.finger.config.WorldConfig;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Supplier;

public class FingerController extends Component
{
    private int life = 3;
    private int superSpaces = 3;
    private int direccion = 0;
    private float velocidad = 60;
    private int limiteBalas = 3;

    private Transform transform;
    private final Pool<GameObject> bulletPool = new Pool<>(this::createBullet);
    private final HashSet<GameObject> spwnedBullets = new HashSet<>();

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
        Iterator<GameObject> bulletsIter = spwnedBullets.iterator();

        while (bulletsIter.hasNext())
        {
            GameObject bullet = bulletsIter.next();

            if (!bullet.isEnabled() || Math.abs(bullet.getTransform().getPosition().x) > WorldConfig.WIDTH / 2f)
            {
                bullet.setEnabled(false);
                bulletsIter.remove();
                bulletPool.release(bullet);
            }
        }

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
                    if (spwnedBullets.size() >= limiteBalas) return false;
                    i++;

                    System.out.println(123);

                    GameObject bullet = bulletPool.request();
                    spwnedBullets.add(bullet);
                    bullet.setEnabled(true);
                    bullet.getTransform().setPosition(new Vector3(
                        FingerController.this.getTransform().getPosition().x,
                        FingerController.this.getTransform().getPosition().y,
                        0)
                    );

                    return i < FingerController.this.limiteBalas;
                };
            }
        });
    }

    @Override
    public void onTrigger2DEnter(Collider2D other)
    {
        if (other.getGameObject().getMetadata().getTag().equals("Enemy"))
        {
            other.getGameObject().setEnabled(false);

            if (life == 0)
            {
                this.getGameObject().destroy();
            }
        }
    }

    public GameObject createBullet()
    {
        GameObject bullet = new GameObject(new Transform(0, 0, 0));
        bullet.getMetadata().setTag("Bullet");

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(4);
        bullet.addComponent(circleShape);

        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setRadius(4.5f);
        circleCollider2D.setLayer("Bullet");
        bullet.addComponent(circleCollider2D);

        bullet.addComponent(new BulletController());

        FingerController.this.getGameObject().getScene().addGameObject(bullet);

        return bullet;
    }
}
