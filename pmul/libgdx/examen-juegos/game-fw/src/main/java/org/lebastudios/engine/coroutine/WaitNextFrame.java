package org.lebastudios.engine.coroutine;

public abstract class WaitNextFrame implements IEnumerator
{
    @Override
    public boolean moveNext(float deltaTime)
    {
        return true;
    }
}
