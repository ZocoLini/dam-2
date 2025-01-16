package org.lebastudios.ej2;

import java.util.Scanner;

public class ConfirmationRequest
{
    public static boolean askConfirmation(String message)
    {
        System.out.println(message + " (S/n)");
        final var nextLine = new Scanner(System.in).nextLine();
        
        if (nextLine.isBlank()) 
        {
            return false;
        }
        
        return nextLine.toCharArray()[0] == 'S';
    }
}
