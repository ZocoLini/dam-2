package rand;

public class RandomResult
{
    public static boolean getBoolean(float probability)
    {
        return Math.random() < probability;
    }

    public static int getInt(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
