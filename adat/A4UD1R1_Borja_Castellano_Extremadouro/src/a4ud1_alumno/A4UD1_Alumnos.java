/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a4ud1_alumno;

import java.text.SimpleDateFormat;
import java.util.Scanner;


public class A4UD1_Alumnos {

    static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    private static void appMenu()
    {
        boolean exit = false;
        
        while (!exit)
        {
            switch (showMenu())
            {
                default -> exit = true;
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
