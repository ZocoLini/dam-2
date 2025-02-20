package org.lebastudios.engine.coroutine;

import java.util.function.Supplier;

public abstract class WaitNextFrame implements IEnumerator
{
    @Override
    public boolean moveNext(float deltaTime)
    {
        return true;
    }
}
