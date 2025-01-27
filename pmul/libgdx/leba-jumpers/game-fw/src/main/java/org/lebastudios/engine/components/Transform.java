package org.lebastudios.engine.components;

import com.badlogic.gdx.math.Vector3;

public class Transform extends Component
{
    private final Vector3 position;
    private final Vector3 rotation;
    private final Vector3 scale;

    public Transform(float x, float y, float z)
    {
        position = new Vector3(x, y, z);
        rotation = new Vector3();
        scale = new Vector3(1, 1, 1);
    }
}
