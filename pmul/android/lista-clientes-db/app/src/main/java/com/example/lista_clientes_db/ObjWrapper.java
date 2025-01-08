package com.example.lista_clientes_db;

import java.util.Optional;

public class ObjWrapper<T>
{
    private T obj;

    public ObjWrapper() {}

    public T getObj()
    {
        return obj;
    }

    public void setObj(T obj)
    {
        this.obj = obj;
    }

    public Optional<T> intoOptional()
    {
        return Optional.ofNullable(obj);
    }
}
