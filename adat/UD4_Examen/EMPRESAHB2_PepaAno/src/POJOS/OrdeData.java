/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package POJOS;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author usuario
 */
public class OrdeData implements Comparator <Date> {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public int compare(Date d1, Date d2) {
       return formato.format(d2).compareTo(formato.format(d1));
    }
    
}
