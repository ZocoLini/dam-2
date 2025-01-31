package org.lebastudios.engine.components;

import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Transform extends Component
{
    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale;

    public Transform(float x, float y, float z)
    {
        position = new Vector3(x, y, z);
        rotation = new Vector3();
        scale = new Vector3(1, 1, 1);
    }

    public void translate(float x, float y, float z)
    {
        position.x += x;
        position.y += y;
        position.z += z;
    }
}
