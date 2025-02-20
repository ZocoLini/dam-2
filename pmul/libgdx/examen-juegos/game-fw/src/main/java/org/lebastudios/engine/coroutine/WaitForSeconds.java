package org.lebastudios.engine.coroutine;

public abstract class WaitForSeconds implements IEnumerator
{
    private final float seconds;
    private float stateTime;

    public WaitForSeconds(float seconds)
    {
        this.seconds = seconds;
        stateTime = seconds;
    }

    @Override
    public final boolean moveNext(float deltaTime)
    {
        stateTime += deltaTime;

        if (stateTime >= seconds)
        {
            stateTime = 0;
            return true;
        }

        return false;
    }
}
