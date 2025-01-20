package org.lebastudios.lebajumpers;

public interface IGameEventHandler
{
    default void onCreate() {}

    default void onStart() {}

    default void onUpdate() {}

    default void onFixedUpdate() {}

    default void onDestroy() {}

    default void onCollision2DEnter() {}

    default void onCollision2DExit() {}

    default void onTrigger2DEnter() {}

    default void onTrigger2DExit() {}
}
