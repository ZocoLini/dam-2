package es.ies.chandomonte.juegodados.observer

import java.util.ArrayList

abstract class ObservableProperty<T>(private var value: T)
{
    private val listeners = ArrayList<PropertyChangeListener<T>>();

    fun addOnPropertyChangeListener(listener: PropertyChangeListener<T>)
    {
        listeners.add(listener)
    }

    private fun invokeListeners(oldValue: T, newValue: T)
    {
        listeners.forEach { listener -> listener.onPropertyChange(this, oldValue, newValue) }
    }

    fun set(newValue: T)
    {
        val oldValue = this.value;

        this.value = newValue;

        invokeListeners(oldValue, newValue);
    }

    interface PropertyChangeListener<T>
    {
        fun onPropertyChange(property: ObservableProperty<T>, oldValue: T, newValue: T);
    }
}