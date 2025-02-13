package org.lebastudios.engine.util;

import java.util.Objects;

// TODO: Make record
public class Tuple2<F, S>
{
    public final F first;
    public final S second;

    public Tuple2(F first, S second)
    {
        this.first = first;
        this.second = second;
    }

    public boolean contains(Object o)
    {
        return Objects.equals(first, o) || Objects.equals(second, o);
    }

    public static <T, U> Tuple2<T, U> of(T first, U second)
    {
        return new Tuple2<>(first, second);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;

        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
        return Objects.equals(first, tuple2.first) && Objects.equals(second, tuple2.second);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hashCode(first);
        result = 31 * result + Objects.hashCode(second);
        return result;
    }
}
