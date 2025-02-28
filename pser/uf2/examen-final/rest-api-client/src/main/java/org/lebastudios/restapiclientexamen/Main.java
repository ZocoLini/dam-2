package org.lebastudios.restapiclientexamen;

import org.lebastudios.restapiclientexamen.httpbodies.Pepe;
import org.lebastudios.restapiclientexamen.httpbodies.URLEncoder;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println(URLEncoder.encode(new Pepe("ad", "bn")));
        
        appMenu();
    }
    
    private static void appMenu()
    {
        boolean exit = false;
    
        while (!exit)
        {
            switch (showMenu())
            {
                default:
                    exit = true;
                    break;
            }
        }
    }
    
    private static int showMenu()
    {
        System.out.println("----  Menu  ----");
    
        try
        {
            return new Scanner(System.in).nextInt();
        }
        catch (Exception ignore)
        {
            return -1;
        }
    }
}
