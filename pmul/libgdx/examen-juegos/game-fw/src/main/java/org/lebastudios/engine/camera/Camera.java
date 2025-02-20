package org.lebastudios.engine.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Setter;
import org.lebastudios.engine.Scene;

public class Camera extends OrthographicCamera
{
    @Setter private Vector3 backgroundColor = new Vector3(0, 0, 1);
    @Setter private float alpha = 1;

    private final Viewport viewport;
    @Setter private boolean usesViewport = true;

    public Camera(Scene scene)
    {
        viewport = new FitViewport(scene.getCameraWidth(), scene.getCameraHeight(), this);
    }

    public void onRender(Scene scene)
    {
        ScreenUtils.clear(backgroundColor.x, backgroundColor.y, backgroundColor.z, alpha);
    }

    public void onResize(int width, int height, Scene scene)
    {
        if (usesViewport)
        {
            viewport.update(width, height);
        }
        else
        {
            float aspectRatio = (float) height / width;
            float desiredAspectRatio = scene.getCameraHeight() / scene.getCameraWidth();

            if (aspectRatio < desiredAspectRatio)
            {
                viewportWidth = scene.getCameraWidth() / aspectRatio * desiredAspectRatio;
                viewportHeight = scene.getCameraHeight();
            }
            else
            {
                viewportWidth = scene.getCameraWidth();
                viewportHeight = scene.getCameraHeight() * aspectRatio / desiredAspectRatio;
            }

            this.update();
        }
    }

    @Override
    public Vector3 unproject(Vector3 touchCoords)
    {
        if (usesViewport)
        {
            return viewport.unproject(touchCoords);
        }
        else
        {
            return super.unproject(touchCoords);
        }
    }

    @Override
    public Vector3 project(Vector3 worldCoords)
    {
        if (usesViewport)
        {
            return viewport.project(worldCoords);
        }
        else
        {
            return super.project(worldCoords);
        }
    }
}
