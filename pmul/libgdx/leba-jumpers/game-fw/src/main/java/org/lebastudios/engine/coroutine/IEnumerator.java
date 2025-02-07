package org.lebastudios.engine.coroutine;

import java.util.function.Supplier;

public interface IEnumerator
{
    boolean moveNext(float deltaTime);
    Supplier<Boolean> getAction();
}
