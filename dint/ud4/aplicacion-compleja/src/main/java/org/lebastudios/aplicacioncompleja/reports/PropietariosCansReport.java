package org.lebastudios.aplicacioncompleja.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class PropietariosCansReport
{
    public void showReport(String orderBy, Connection conn) throws JRException
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("order-by", orderBy);

        JasperReport report = JasperCompileManager.compileReport(PropietariosCansReport.class.getResourceAsStream(
                "examen-dint.jrxml"));
        
        JasperPrint print = JasperFillManager.fillReport(
                report,
                params,
                conn
        );

        JasperViewer.viewReport(print, false);
    }
}
