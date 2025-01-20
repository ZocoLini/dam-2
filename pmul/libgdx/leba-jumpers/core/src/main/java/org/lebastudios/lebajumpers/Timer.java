package org.lebastudios.lebajumpers;

public class Timer
{
    private final Runnable onTime;
    private final float timeToUpdate;

    private float timeElapsed;

    public Timer(Runnable onTime, float timeToUpdate)
    {
        this.onTime = onTime;
        this.timeToUpdate = timeToUpdate;
    }

    public void increse(float delta)
    {
        timeElapsed += delta;

        if (timeElapsed >= timeToUpdate)
        {
            timeElapsed = timeToUpdate - timeElapsed;
            onTime.run();
        }
    }

    public void call()
    {
        timeElapsed = 0;
        onTime.run();
    }
}
