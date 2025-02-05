/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.lebastudios.jasperreports;

import net.sf.jasperreports.engine.*;

import java.util.HashMap;

/**
 * @author bcastextr
 */
public class ReporteJasper
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JRException
    {
        JasperReport report = JasperCompileManager.compileReport(
                ReporteJasper.class.getResourceAsStream("first-report.jrxml")
        );
        
        HashMap<String, Object> params = new HashMap<>();
        params.put("WHERE", "where f.id_factura = 13");
        
        JasperPrint print = JasperFillManager.fillReport(report, params);
    }

}
