/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class NewClass {
    public static void main(String[] args) throws JRException {
       
          conexion conex = new conexion(); 
       conex.getConnection();

        
        
             

        JasperReport reporte = (JasperReport) JRLoader.loadObject("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\Sicoca\\VestidosAry\\VestidosAry\\VestidosAry2\\report1.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conex.getConnection());

        JRExporter exporter = new JRPdfExporter();
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reportePDF.pdf"));
        exporter.exportReport();
            
       
    }
    
}
