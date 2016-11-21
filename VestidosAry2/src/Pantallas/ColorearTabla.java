
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class ColorearTabla extends DefaultTableCellRenderer {

    PreparedStatement ps;
    ResultSet rs;
    Connection con = null;
    private Component componente;
 int cont1=1;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
     
        componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        componente.setForeground(Color.black);
       
      
        Object v = table.getValueAt(row, 0);
     
        boolean ban = ver(Integer.parseInt(v.toString()));
   ;
        if (ban) {
           
            componente.setBackground(Color.YELLOW);
        } else {
            componente.setBackground(Color.white);
            
        }
        
     
        return componente;
        
    }
    
    public boolean ver(int fila) {
     
        boolean ban = false;
        
        
        con = conexion.getConnection();
        
        String sql = "select  productosapartados.idproductosapartados from productosapartados where idproductosapartados in (select medidas.productosapartados_id from medidas) and idproductosapartados='" + fila + "' and productosapartados.status='Apartado' ";
    
        try {
      
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
               
                ban = true;
            }
            
        } catch (Exception e) {
            
        }
        return ban;
    }
}
