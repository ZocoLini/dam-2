package org.lebastudios.engine.util;

import java.util.Objects;

public record Tuple2<F, S>(F first, S second)
{
    public boolean contains(Object o)
    {
        return Objects.equals(first, o) || Objects.equals(second, o);
    }

    public static <T, U> Tuple2<T, U> of(T first, U second)
    {
        return new Tuple2<>(first, second);
    }

}
