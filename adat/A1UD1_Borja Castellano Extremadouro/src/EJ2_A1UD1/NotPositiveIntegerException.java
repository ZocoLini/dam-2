package EJ2_A1UD1;

public class NotPositiveIntegerException extends RuntimeException
{
    public NotPositiveIntegerException()
    {
        super("Not positive integer not allowed.");
    }
}
