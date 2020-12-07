/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
 
import net.sf.jasperreports.engine.JasperPrint;
 
import net.sf.jasperreports.engine.JasperReport;
 
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
 
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author AbdElFateh
 */
public class PrintReport {

    void printRapportCommande(String name,String qry ,Double s,String title) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("convertArb", ("حدد بمبلغ : " + Nombrearabic.CALCULATE.getValue(s, "دج", "سم")));

        try {
            JasperDesign jd=JRXmlLoader.load(getClass().getResourceAsStream(name));
            JRDesignQuery query=new JRDesignQuery();
            query.setText(qry);
            jd.setQuery(query);
             JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, params, Connecter.getConnection());
            JasperViewer jv = new JasperViewer(jasperPrint, false);
            jv.setTitle(title);
            jv.show();

        } catch (JRException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     void printRapportCommande(String name,String qry ,String title) throws Exception {
      
        try {
            JasperDesign jd=JRXmlLoader.load(getClass().getResourceAsStream(name));
            JRDesignQuery query=new JRDesignQuery();
            query.setText(qry);
            jd.setQuery(query);
             JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, null, Connecter.getConnection());
            JasperViewer jv = new JasperViewer(jasperPrint, false);
            jv.setTitle(title);
            jv.show();

        } catch (JRException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     void printSubRapportCommande(String name,String qry ,String title) throws Exception {
      
        try {   
            JasperReport subReportJR = JasperCompileManager.compileReport(JRXmlLoader.load(this.getClass().getResourceAsStream("/XML/articlesDetail.jrxml")));
           // JasperDesign jd=JRXmlLoader.load((System.getProperty("user.dir")+"/src"+name).replace("\\", "/")); 
             JasperDesign jd=JRXmlLoader.load(this.getClass().getResourceAsStream(name));  
            JRDesignQuery query=new JRDesignQuery();
            query.setText(qry);
            jd.setQuery(query);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("sub", subReportJR);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parameters, Connecter.getConnection());
            JasperViewer jv = new JasperViewer(jasperPrint, false);
            jv.setTitle(title);
            jv.show();

        } catch (JRException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
      void printSubRapportCommandeMonth(String name,String qry ,String title, int mois) throws Exception {
      
        try {   
            JasperReport subReportJR = JasperCompileManager.compileReport(JRXmlLoader.load(this.getClass().getResourceAsStream("/XML/articlesDetailMonth.jrxml")));
            //JasperDesign jd=JRXmlLoader.load((System.getProperty("user.dir")+"/src"+name).replace("\\", "/"));         
            JasperDesign jd=JRXmlLoader.load(this.getClass().getResourceAsStream(name));  
            JRDesignQuery query=new JRDesignQuery();
            query.setText(qry);
            jd.setQuery(query);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("sub", subReportJR);
            parameters.put("mois", mois);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parameters, Connecter.getConnection());
            JasperViewer jv = new JasperViewer(jasperPrint, false);
            jv.setTitle(title);
            jv.show();

        } catch (JRException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
