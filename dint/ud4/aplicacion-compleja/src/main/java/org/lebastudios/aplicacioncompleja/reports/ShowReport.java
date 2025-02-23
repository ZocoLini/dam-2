package org.lebastudios.aplicacioncompleja.reports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class ShowReport
{
    public void showReport() throws SQLException, JRException
    {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinica", "root", "abc123.");

        HashMap<String, Object> params = new HashMap<>();
        params.put("WHERE", "where f.id_factura = 13");

        JasperPrint print = JasperFillManager.fillReport(
                ShowReport.class.getResourceAsStream("first-report.jasper"),
                params,
                conn
        );

        JasperViewer.viewReport(print, false);
        
        conn.close();
    }
}
