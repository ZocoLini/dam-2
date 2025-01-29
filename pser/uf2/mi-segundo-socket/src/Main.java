import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args)
     {
        String input = "get pc rtx5090 i9 34";
        String regex = "([a-zñ]+)\\s+([a-zñ\\d\\s]+)\\s+(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        if (matcher.matches()) 
        {
            System.out.println("Group 1: " + matcher.group(1));
            System.out.println("Group 2: " + matcher.group(2));
            System.out.println("Group 3: " + matcher.group(3));
        }
    }
}